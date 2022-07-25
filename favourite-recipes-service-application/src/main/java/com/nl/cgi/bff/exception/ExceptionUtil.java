package com.nl.cgi.bff.exception;

import com.nl.cgi.bff.model.response.*;
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

    public static Mono<Boolean> validateResponse(boolean response) {
        if (!response) {
            log.info("Details updated/Added Successfully");
            return Mono.error(new ServiceException(ErrorDetail.INTERNAL_SERVER_ERROR, "Details details is not saved"));
        }
        log.info("Details updated/Added Successfully");
        return Mono.just(response);
    }
    public static Mono<DishesResponse> validateGetDishesResponseResponse(DishesResponse dishesResponse) {
       if (dishesResponse==null || dishesResponse.getDishesNames().isEmpty()) {
           log.error("Recipes details is empty");
           return Mono.error(new ServiceException(ErrorDetail.CLIENT_DETAILS_NOT_FOUND, "No recipes returned from persistence service"));
        }
        log.info("Recipes details return from PS");
        return Mono.just(dishesResponse);
    }

    public static Mono<IngredientsResponse> validateGetDishesResponseResponse(IngredientsResponse ingredientsResponse) {
        if (ingredientsResponse==null || ingredientsResponse.getIngredients().isEmpty()) {
            log.error("Ingredients details is empty");
            return Mono.error(new ServiceException(ErrorDetail.CLIENT_DETAILS_NOT_FOUND, "No Ingredients returned from persistence service"));
        }
        log.info("Ingredients details return from PS");
        return Mono.just(ingredientsResponse);
    }

    public static Mono<Response> validateGetFoodRecipeResponseResponse(Response foodRecipeDetails) {
        if (foodRecipeDetails==null || foodRecipeDetails.getFoodRecipeDetails().isEmpty()) {
            log.error("Ingredients details is empty");
            return Mono.error(new ServiceException(ErrorDetail.CLIENT_DETAILS_NOT_FOUND, "No Ingredients returned from persistence service"));
        }
        log.info("Ingredients details return from PS");
        return Mono.just(foodRecipeDetails);
    }


}