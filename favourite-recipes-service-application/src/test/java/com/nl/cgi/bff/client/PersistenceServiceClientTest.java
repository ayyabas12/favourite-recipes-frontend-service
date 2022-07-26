package com.nl.cgi.bff.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nl.cgi.bff.exception.ExceptionResponse;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.mockdata.StubServerConfig;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.model.request.RecipesRequest;
import com.nl.cgi.bff.model.response.IngredientsResponse;
import com.nl.cgi.bff.model.response.RecipesResponse;
import com.nl.cgi.bff.model.response.SearchRecipesResponse;
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
    @DisplayName("Test All Recipes Service Details")
    class TestGetRecipesDetailsPSService {

        private static final String response = "{\n" +
                "    \"recipes\": {\n" +
                "        \"recipeId\": 1,\n" +
                "        \"recipeName\": \"Chicken Curry\",\n" +
                "        \"quantity\": 3,\n" +
                "        \"instructions\": \"New Food  checken all non vegetarian recipes\",\n" +
                "        \"category\": \"NON_VEGETARIAN\",\n" +
                "        \"ingredientsList\": [\n" +
                "            1,\n" +
                "            2\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        String searchRecipesResponse = "{\n" +
                "    \"recipes\": [\n" +
                "        {\n" +
                "            \"recipeId\": 1,\n" +
                "            \"recipeName\": \"Chicken Curry\",\n" +
                "            \"quantity\": 3,\n" +
                "            \"instructions\": \"New Food  checken all non vegetarian recipes\",\n" +
                "            \"category\": \"NON_VEGETARIAN\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        private static final RecipesRequest recipesRequest = RecipesRequest.builder().recipeId(1).recipeName("ddd").build();

        @Test
        void testGetRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(response));
            RecipesResponse recipesResponse = persistenceServiceClient.getRecipesDetails(1L, "/dishes-persistence-service/recipes/");
            assertNotNull(recipesResponse);
            assertEquals(1, recipesResponse.getRecipes().getRecipeId());
            assertEquals("Chicken Curry", recipesResponse.getRecipes().getRecipeName());
        }

        @Test
        void testSaveRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(response));
            RecipesResponse recipesResponse = persistenceServiceClient.saveRecipesDetails(recipesRequest, "/dishes-persistence-service/recipes/");
            assertNotNull(recipesResponse);
            assertEquals(1, recipesResponse.getRecipes().getRecipeId());
            assertEquals("Chicken Curry", recipesResponse.getRecipes().getRecipeName());
        }

        @Test
        void testUpdateRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(response));
            RecipesResponse recipesResponse = persistenceServiceClient.updateRecipesDetails(1L, recipesRequest, "/dishes-persistence-service/recipes/1");
            assertNotNull(recipesResponse);
            assertEquals(1, recipesResponse.getRecipes().getRecipeId());
            assertEquals("Chicken Curry", recipesResponse.getRecipes().getRecipeName());
        }

        @Test
        void testDeleteRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor("true"));
            boolean recipesResponse = persistenceServiceClient.deleteRecipeDetails(1L, "/dishes-persistence-service/recipes/1");
            assertTrue(recipesResponse);
        }

        @Test
        void testSearchRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(searchRecipesResponse));
            SearchRecipesResponse recipesResponse = persistenceServiceClient.searchFoodRecipesDetails("", 1, "recipesRequest", "dishes-persistence-service/recipes/search");
            assertNotNull(recipesResponse);
            assertEquals(1, recipesResponse.getRecipes().get(0).getRecipeId());
            assertEquals("Chicken Curry", recipesResponse.getRecipes().get(0).getRecipeName());
        }


        @Test
        void testGetBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.getRecipesDetails(1, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during get Recipes details call", exception.getMessage());

        }

    }

    @Nested
    @DisplayName("Test Save dish es Service Details")
    class TestSaveRecipesDetailsPSService {


        @Test
        void testSaveBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getInvalidRecipesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveRecipesDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save dish details call", exception.getMessage());

        }

        @Test
        void testSaveInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getInvalidRecipesServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveRecipesDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save dish details call", exception.getMessage());

        }


    }

    @Nested
    @DisplayName("Test GET Save Service for Ingredients Details")
    class TestSaveIngredientsPSService {

        String ingredientsResponse = "{\n" +
                "    \"ingredients\": {\n" +
                "        \"ingredientsId\": 2,\n" +
                "        \"ingredientName\": \"Chicken\",\n" +
                "        \"imageurl\": \"tomoto.png\"\n" +
                "    }\n" +
                "}";
        private static final IngredientsRequest ingredientsRequest = IngredientsRequest.builder().ingredientName("ddd").build();

        @Test
        void testGetRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(ingredientsResponse));
            IngredientsResponse ingredientsResponse = persistenceServiceClient.getIngredientsDetails(1L, "/dishes-persistence-service/recipes/");
            assertNotNull(ingredientsResponse);
            assertEquals(2, ingredientsResponse.getIngredients().getIngredientsId());
            assertEquals("Chicken", ingredientsResponse.getIngredients().getIngredientName());
        }

        @Test
        void testSaveRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(ingredientsResponse));
            IngredientsResponse ingredientsResponse = persistenceServiceClient.saveIngredientsDetails(ingredientsRequest, "/dishes-persistence-service/recipes/");
            assertNotNull(ingredientsResponse);
            assertEquals(2, ingredientsResponse.getIngredients().getIngredientsId());
            assertEquals("Chicken", ingredientsResponse.getIngredients().getIngredientName());
        }

        @Test
        void testUpdateRecipesDetails() throws JsonProcessingException {
            mockServerWithResponse(new ResponseBuilder<String>().mockResponseFor(ingredientsResponse));
            IngredientsResponse ingredientsResponse = persistenceServiceClient.updateIngredientsDetails(1L, ingredientsRequest, "/dishes-persistence-service/recipes/1");
            assertNotNull(ingredientsResponse);
            assertEquals(2, ingredientsResponse.getIngredients().getIngredientsId());
            assertEquals("Chicken", ingredientsResponse.getIngredients().getIngredientName());
        }


        @Test
        void testSaveIngredientBadRequestException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_BAD_REQUEST, 400));
            var request = MockDataProvider.getInvalidIngredientsServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveIngredientsDetails(request, "url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save ingredients details call", exception.getMessage());

        }

        @Test
        void testSaveIngredientsInternalServerException() throws JsonProcessingException {
            mockServerWithResponse(new ErrorResponseBuilder<ExceptionResponse>().mockErrorResponseFor(PS_SERVER_ERROR, 500));
            var request = MockDataProvider.getInvalidIngredientsServiceRequest();
            ServiceException exception = Assertions.assertThrows(ServiceException.class, () ->
                    persistenceServiceClient.saveIngredientsDetails(request, "url")
            );
            assertEquals("Internal server error please try later", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during save ingredients details call", exception.getMessage());

        }


    }


}
