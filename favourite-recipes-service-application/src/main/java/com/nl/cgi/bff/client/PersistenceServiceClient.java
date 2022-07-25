package com.nl.cgi.bff.client;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.model.request.*;
import com.nl.cgi.bff.model.response.*;
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
     * @param requestUrl
     * @return
     */
    public DishesResponse getDishDetails(String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get dishes details call")))
                .bodyToMono(DishesResponse.class)
                .flatMap(recipesResponse -> ExceptionUtil.validateGetDishesResponseResponse(recipesResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     * @param dishRequest
     * @param requestUrl
     * @return
     */
    public boolean saveOrUpdateDishDetails(DishRequest dishRequest, String requestUrl) {
        return persistenceWebClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dishRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save dish details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }


    public Boolean deleteDishDetails(Long dishId, String requestUrl) {
        return persistenceWebClient
                .delete()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("dishId", dishId).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }



    /**
     * @param requestUrl
     * @return
     */
    public IngredientsResponse getIngredientsDetails(String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(IngredientsResponse.class)
                .flatMap(recipesResponse -> ExceptionUtil.validateGetDishesResponseResponse(recipesResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     *
     * @param ingredientsRequest
     * @param requestUrl
     * @return
     */
    public boolean saveOrUpdateIngredientsDetails(IngredientsRequest ingredientsRequest, String requestUrl) {
        return persistenceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ingredientsRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save ingredients details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }





    /**
     * @param requestUrl
     * @return
     */
    public Response getFoodRecipesDetails(String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(Response.class)
                .flatMap(recipesResponse -> ExceptionUtil.validateGetFoodRecipeResponseResponse(recipesResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }




    /**
     * @param recipesRequest
     * @param requestUrl
     * @return
     */
    public boolean saveOrUpdateFoodRecipesDetails(FavouriteFoodRecipesRequest recipesRequest, String requestUrl) {
        return persistenceWebClient
                .put()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(recipesRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during save FoodRecipes details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     * @param dishRequest
     * @param requestUrl
     * @return
     */
    public Response searchFoodRecipesDetails(DishSearchRequest dishRequest, String requestUrl) {
        return persistenceWebClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).build())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dishRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during search FoodRecipesDetails call")))
                .bodyToMono(Response.class)
                .flatMap(response -> ExceptionUtil.validateGetFoodRecipeResponseResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }



    public Boolean deleteFoodRecipesDetails(Long id, String requestUrl) {
        return persistenceWebClient
                .delete()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("foodRecipeId", id).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(Boolean.class)
                .flatMap(response -> ExceptionUtil.validateResponse(response))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }

    /**
     * @param requestUrl
     * @return
     */
    public Response getFoodRecipesDetailsFilterByInstructions(String instructions, String requestUrl) {
        return persistenceWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(requestUrl).queryParam("searchString", instructions).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, e -> ExceptionUtil.handleErrorResponse(e, new ServiceException("Exception occurred during get recipes details call")))
                .bodyToMono(Response.class)
                .flatMap(recipesResponse -> ExceptionUtil.validateGetFoodRecipeResponseResponse(recipesResponse))
                .doOnError(ExceptionUtil::handleGenericWebClientException)
                .block();
    }



}
