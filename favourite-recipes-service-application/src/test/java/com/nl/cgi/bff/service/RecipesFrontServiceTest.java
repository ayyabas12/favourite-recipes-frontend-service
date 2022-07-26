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
    RecipesFrontService dishesFrontService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        dishesFrontService = new RecipesFrontService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("Get the Dishes Details")
    class RequestGetSaveRecipesDetails {

        @Test
        void testGetDishesDetails() {
            when(persistenceServiceClient.getRecipesDetails(anyLong(), any())).thenReturn(MockDataProvider.getDishesDetails());
            RecipesResponse dishesDetails = dishesFrontService.getRecipesDetails(1);
            assertNotNull(dishesDetails);
            assertEquals(1, dishesDetails.getRecipes().getRecipeId());
            assertEquals("chicken breast curry", dishesDetails.getRecipes().getRecipeName());
            verify(persistenceServiceClient, times(1)).getRecipesDetails(anyLong(), any());
        }


        @Test
        void testGetDishesWhenDetailsISNotFound() {
            when(persistenceServiceClient.getRecipesDetails(anyLong(), any())).thenThrow(new ServiceException("Dishes Details is not Found"));
            ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                    dishesFrontService.getRecipesDetails(2));
            verify(persistenceServiceClient, times(1)).getRecipesDetails(anyLong(), any());
            assertEquals("Dishes Details is not Found", invalidRequestException.getMessage());
        }

        @Test
        void testSaveDishesDetails() {
            when(persistenceServiceClient.saveRecipesDetails(any(RecipesRequest.class), any())).thenReturn(MockDataProvider.getDishesDetails());
            RecipesResponse dishesResponse = dishesFrontService.saveRecipesDetails(MockDataProvider.getDishServiceRequest());
            assertNotNull(dishesResponse);
            verify(persistenceServiceClient, times(1)).saveRecipesDetails(any(RecipesRequest.class), any());
        }

        @Test
        void testGetDishesDetailsWhenDishesDetailsNull() {
            when(persistenceServiceClient.saveRecipesDetails(any(RecipesRequest.class), any())).thenThrow(new ServiceException("No Dishes returned from persistence service"));
            var request = MockDataProvider.getInvalidDishesServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    dishesFrontService.saveRecipesDetails(request));
            assertEquals("No Dishes returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveRecipesDetails(any(RecipesRequest.class), any());
        }
    }
}