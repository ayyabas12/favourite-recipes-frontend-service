package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.RecipesRequest;
import com.nl.cgi.bff.model.response.RecipesResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RecipesFrontServiceTest {
    RecipesFrontService recipesFrontService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        recipesFrontService = new RecipesFrontService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("All service the Recipes Details")
    class RequestGetSaveRecipesDetails {

        @Test
        void testGetRecipesDetails() {
            when(persistenceServiceClient.getRecipesDetails(anyLong(), any())).thenReturn(MockDataProvider.getRecipesDetails());
            RecipesResponse RecipesDetails = recipesFrontService.getRecipesDetails(1);
            assertNotNull(RecipesDetails);
            assertEquals(1, RecipesDetails.getRecipes().getRecipeId());
            assertEquals("chicken breast curry", RecipesDetails.getRecipes().getRecipeName());
            verify(persistenceServiceClient, times(1)).getRecipesDetails(anyLong(), any());
        }


        @Test
        void testGetRecipesWhenDetailsISNotFound() {
            when(persistenceServiceClient.getRecipesDetails(anyLong(), any())).thenThrow(new ServiceException("Recipes Details is not Found"));
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    recipesFrontService.getRecipesDetails(2));
            verify(persistenceServiceClient, times(1)).getRecipesDetails(anyLong(), any());
            assertEquals("Recipes Details is not Found", invalidRequestException.getMessage());
        }

        @Test
        void testSaveRecipesDetails() {
            when(persistenceServiceClient.saveRecipesDetails(any(RecipesRequest.class), any())).thenReturn(MockDataProvider.getRecipesDetails());
            RecipesResponse recipesResponse = recipesFrontService.saveRecipesDetails(MockDataProvider.getDishServiceRequest());
            assertNotNull(recipesResponse);
            assertEquals(1, recipesResponse.getRecipes().getRecipeId());
            assertEquals("chicken breast curry", recipesResponse.getRecipes().getRecipeName());
            verify(persistenceServiceClient, times(1)).saveRecipesDetails(any(RecipesRequest.class), any());
        }

        @Test
        void testGetRecipesDetailsWhenRecipesDetailsNull() {
            when(persistenceServiceClient.saveRecipesDetails(any(RecipesRequest.class), any())).thenThrow(new ServiceException("No Recipes returned from persistence service"));
            var request = MockDataProvider.getInvalidRecipesServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    recipesFrontService.saveRecipesDetails(request));
            assertEquals("No Recipes returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveRecipesDetails(any(RecipesRequest.class), any());
        }

        @Test
        void testUpdateRecipesDetails() {
            when(persistenceServiceClient.updateRecipesDetails(anyLong(), any(RecipesRequest.class), any())).thenReturn(MockDataProvider.getRecipesDetails());
            RecipesResponse recipesResponse = recipesFrontService.updateRecipesDetails(1, MockDataProvider.getDishServiceRequest());
            assertNotNull(recipesResponse);
            assertEquals(1, recipesResponse.getRecipes().getRecipeId());
            assertEquals("chicken breast curry", recipesResponse.getRecipes().getRecipeName());
            verify(persistenceServiceClient, times(1)).updateRecipesDetails(anyLong(), any(RecipesRequest.class), any());
        }

        @Test
        void testUpdateRecipesDetailsWhenRecipesDetailsNull() {
            when(persistenceServiceClient.saveRecipesDetails(any(RecipesRequest.class), any())).thenThrow(new ServiceException("No Recipes returned from persistence service"));
            var request = MockDataProvider.getInvalidRecipesServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    recipesFrontService.saveRecipesDetails(request));
            assertEquals("No Recipes returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveRecipesDetails(any(RecipesRequest.class), any());
        }

        @Test
        void testDeleteRecipesDetails() {
            when(persistenceServiceClient.getRecipesDetails(anyLong(), any())).thenReturn(MockDataProvider.getRecipesDetails());
            RecipesResponse RecipesDetails = recipesFrontService.getRecipesDetails(1);
            assertNotNull(RecipesDetails);
            assertEquals(1, RecipesDetails.getRecipes().getRecipeId());
            assertEquals("chicken breast curry", RecipesDetails.getRecipes().getRecipeName());
            verify(persistenceServiceClient, times(1)).getRecipesDetails(anyLong(), any());
        }


        @Test
        void testDeleteRecipesWhenDetailsISNotFound() {
            when(persistenceServiceClient.deleteRecipeDetails(anyLong(), any())).thenThrow(new ServiceException("Recipes Details is not Found"));
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    recipesFrontService.deleteRecipesDetails(2L));
            verify(persistenceServiceClient, times(1)).deleteRecipeDetails(anyLong(), any());
            assertEquals("Recipes Details is not Found", invalidRequestException.getMessage());
        }

    }
}