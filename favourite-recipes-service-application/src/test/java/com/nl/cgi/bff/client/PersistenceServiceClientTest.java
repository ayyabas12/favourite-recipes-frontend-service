package com.nl.cgi.bff.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nl.cgi.bff.exception.ExceptionResponse;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.mockdata.StubServerConfig;
import com.nl.cgi.bff.model.response.DishesResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersistenceServiceClientTest extends StubServerConfig {
    PersistenceServiceClient persistenceServiceClient;

    @BeforeEach
    void setup() {
        persistenceServiceClient = new PersistenceServiceClient(webClient);
    }

    @Nested
    @DisplayName("Test Get Service Details")
    class TestGetDishesDetailsPSService {
        @Test
        void testGetBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.getDishDetails("url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during get dishes details call", exception.getMessage());

        }

        @Test
        void testGetInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.getFoodRecipesDetails( "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during get recipes details call", exception.getMessage());

        }


    }

    @Nested
    @DisplayName("Test Save dish es Service Details")
    class TestSaveDishesDetailsPSService {
        @Test
        void testSaveDishesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean isUserSaved = persistenceServiceClient.saveOrUpdateDishDetails(MockDataProvider.getDishServiceRequest(), "url");
            assertTrue(isUserSaved);
        }


        @Test
        void testSaveBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getInvalidDishesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateDishDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save dish details call", exception.getMessage());

        }

        @Test
        void testSaveInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getInvalidDishesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateDishDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save dish details call", exception.getMessage());

        }


    }

   @Nested
    @DisplayName("Test GET Save Service for Ingredients Details")
    class TestSaveIngredientsPSService {
        @Test
        void testSaveIngredientDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean isIngredientSaved= persistenceServiceClient.saveOrUpdateIngredientsDetails(MockDataProvider.getInvalidIngredientsServiceRequest(),"/save-ingredients");
            assertTrue(isIngredientSaved);
        }

        @Test
        void testSaveIngredientDetailsDetailsWhenTheRequestISNull() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("false"));
            var request = MockDataProvider.getInvalidIngredientsServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateIngredientsDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Details details is not saved", exception.getMessage());

        }

        @Test
        void testSaveIngredientBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getInvalidIngredientsServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateIngredientsDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save ingredients details call", exception.getMessage());

        }

        @Test
        void testSaveIngredientsInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getInvalidIngredientsServiceRequest();
                    ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateIngredientsDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save ingredients details call", exception.getMessage());

        }


    }



    @Nested
    @DisplayName("Test GET Save Service for FoodRecipes Details")
    class TestSaveFoodRecipesPSService {
        @Test
        void testSaveFoodRecipeDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean isFoodRecipesSaved= persistenceServiceClient.saveOrUpdateFoodRecipesDetails(MockDataProvider.getFavouriteFoodRecipesServiceRequest(),"/save-food-recipes");
            assertTrue(isFoodRecipesSaved);
        }

        @Test
        void testSaveFoodRecipeDetailsDetailsWhenTheRequestISNull() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("false"));
            var request = MockDataProvider.getInvalidFavouriteFoodRecipesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateFoodRecipesDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Details details is not saved", exception.getMessage());

        }

        @Test
        void testSaveFoodRecipeBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getInvalidFavouriteFoodRecipesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateFoodRecipesDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save FoodRecipes details call", exception.getMessage());

        }

        @Test
        void testSaveFoodRecipesInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getInvalidFavouriteFoodRecipesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveOrUpdateFoodRecipesDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save FoodRecipes details call", exception.getMessage());

        }


    }
}
