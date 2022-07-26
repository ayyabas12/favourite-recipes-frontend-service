package com.nl.cgi.bff.exception;

import com.nl.cgi.bff.model.response.IngredientsResponse;
import com.nl.cgi.bff.model.response.RecipesResponse;
import com.nl.cgi.bff.model.response.SearchRecipesResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@UtilityClass
@Slf4j
public class ExceptionUtil {

    private static final String GENERIC_SERVICE_MESSAGE = "Internal server error please try later";

    public static Mono<Throwable> handleGenericWebClientException(final Throwable throwable) {
        if (throwable instanceof ServiceException) {
            return Mono.error(throwable);
        }
        //This is used to handle the generic exception thrown by down stream services ex: timeoutException
        return Mono.error(new ServiceException(ErrorDetail.INTERNAL_SERVER_ERROR, GENERIC_SERVICE_MESSAGE, throwable));
    }

    public static Mono<Throwable> handleErrorResponse(ClientResponse clientResponse, ServiceException exception) {

        if (clientResponse.statusCode().equals(HttpStatus.BAD_REQUEST)) {
            exception.setErrorDetail(ErrorDetail.CLIENT_BAD_REQUEST_EXCEPTION);
        } else if (clientResponse.statusCode().equals(HttpStatus.FORBIDDEN)) {
            exception.setErrorDetail(ErrorDetail.CLIENT_FORBIDDEN_EXCEPTION);
        } else if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
            exception.setErrorDetail(ErrorDetail.CLIENT_RESOURCE_NOT_FOUND_EXCEPTION);
        } else {
            exception.setErrorDetail(ErrorDetail.INTERNAL_SERVER_ERROR);
        }
        return Mono.error(exception);
    }

    public static Mono<Boolean> validateResponse(boolean response, String message) {
        if (!response) {
            log.info(message);
            return Mono.just(false);
        }
        log.info("Details updated/Added Successfully");
        return Mono.just(true);
    }

    public static Mono<RecipesResponse> validateRecipesResponse(RecipesResponse recipesResponse, String message) {
        if (recipesResponse != null && recipesResponse.getRecipes()!=null) {
            log.info("Recipes details return from PS");
        } else {
            recipesResponse.setMessage(message);
            recipesResponse.setErrorCode(ErrorDetail.DATA_DETAILS_NOT_FOUND.getErrorCode());
            log.error(message);
        }
        return Mono.just(recipesResponse);
    }

    public static Mono<IngredientsResponse> validateIngredientsResponse(IngredientsResponse ingredientsResponse) {
        if (ingredientsResponse != null && ingredientsResponse.getIngredients() != null) {
            log.info("Ingredients details return from PS");

        } else {
            ingredientsResponse.setMessage("Ingredients details is empty");
            ingredientsResponse.setErrorCode(ErrorDetail.DATA_DETAILS_NOT_FOUND.getErrorCode());
            log.error("Ingredients details is empty");
        }
        return Mono.just(ingredientsResponse);

    }

    public static Mono<SearchRecipesResponse> validateGetFoodRecipeResponseResponse(SearchRecipesResponse foodRecipeDetails) {
        if (foodRecipeDetails == null) {
            log.error("Ingredients details is empty");
            return Mono.just(foodRecipeDetails);
        }
        log.info("Ingredients details return from PS");
        return Mono.just(foodRecipeDetails);
    }


}