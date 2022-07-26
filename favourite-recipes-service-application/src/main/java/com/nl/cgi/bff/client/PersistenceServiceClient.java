package com.nl.cgi.bff.client;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.model.request.RecipesRequest;
import com.nl.cgi.bff.model.response.IngredientsResponse;
import com.nl.cgi.bff.model.response.RecipesResponse;
import com.nl.cgi.bff.model.response.SearchRecipesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersistenceServiceClient {

    private final WebClient persistenceWebClient;


    /**
     *
     * @param id recipes Id
     * @param requestUrl get the recipes details
     * @return RecipesResponse
     */
    public RecipesResponse getRecipesDetails(long id, String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("id", id).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get Recipes details call")))
                .bodyToMono(RecipesResponse.class)
                .flatMap(recipesResponse -> ExceptionUtil.validateRecipesResponse(recipesResponse, "Recipes details is empty"))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     * @param recipesRequest request
     * @param requestUrl save url
     * @return recipes response
     */
    public RecipesResponse saveRecipesDetails(RecipesRequest recipesRequest, String requestUrl) {
        return persistenceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(recipesRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save dish details call")))
                .bodyToMono(RecipesResponse.class)
                .flatMap(response -> ExceptionUtil.validateRecipesResponse(response , "Invalid Ingredients Details"))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     *
     * @param id recipes Id
     * @param recipesRequest update
     * @param requestUrl of update the recipes
     * @return update of recipe
     */
    public RecipesResponse updateRecipesDetails(long id, RecipesRequest recipesRequest, String requestUrl) {
        return persistenceWebClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(requestUrl + id).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(recipesRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save dish details call")))
                .bodyToMono(RecipesResponse.class)
                .flatMap(response -> ExceptionUtil.validateRecipesResponse(response , "Invalid input request Details"))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     *
     * @param id recipes Id
     * @param requestUrl delete recipe
     * @return true if deleted
     */
    public Boolean deleteRecipeDetails(Long id, String requestUrl) {
        return persistenceWebClient
                .delete()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("id", id).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during delete recipes details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response, "dish details is not deleted"))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }


    /**
     * @param requestUrl get
     * @return details of ingredients
     */
    public IngredientsResponse getIngredientsDetails(final long id, final String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("id", id).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(IngredientsResponse.class)
                .flatMap(recipesResponse -> ExceptionUtil.validateIngredientsResponse(recipesResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     * @param ingredientsRequest save
     * @param requestUrl of save
     * @return saved ingredients
     */
    public IngredientsResponse saveIngredientsDetails(IngredientsRequest ingredientsRequest, String requestUrl) {
        return persistenceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ingredientsRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save ingredients details call")))
                .bodyToMono(IngredientsResponse.class)
                .flatMap(response -> ExceptionUtil.validateIngredientsResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     * @param ingredientsRequest request
     * @param requestUrl update
     * @return update of ingredients
     */
    public IngredientsResponse updateIngredientsDetails(final long id, IngredientsRequest ingredientsRequest, String requestUrl) {
        return persistenceWebClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(requestUrl + id).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ingredientsRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save ingredients details call")))
                .bodyToMono(IngredientsResponse.class)
                .flatMap(response -> ExceptionUtil.validateIngredientsResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }




    /**
     * @param category veg or non-veg
     * @param quantity number of person serve
     * @param instructions cook
     * @param requestUrl search
     * @return details of ingredients
     */
    public SearchRecipesResponse searchFoodRecipesDetails(String category, long quantity, String instructions, String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl)
                        .queryParam("category", category)
                        .queryParam("quantity", quantity)
                        .queryParam("instructions", instructions)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during search FoodRecipesDetails call")))
                .bodyToMono(SearchRecipesResponse.class)
                .flatMap(response -> ExceptionUtil.validateGetFoodRecipeResponseResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     *
     * @param id recipesod
     * @param requestUrl delete
     * @return boolean
     */

    public Boolean deleteFoodRecipesDetails(Long id, String requestUrl) {
        return persistenceWebClient
                .delete()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("foodRecipeId", id).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response, "recipes is not deleted"))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }
}
