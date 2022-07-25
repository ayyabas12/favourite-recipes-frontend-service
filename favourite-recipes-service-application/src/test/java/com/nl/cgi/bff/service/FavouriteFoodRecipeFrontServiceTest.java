package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.FavouriteFoodRecipesRequest;
import com.nl.cgi.bff.model.response.FavouriteFoodRecipesResponse;
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
public class FavouriteFoodRecipeFrontServiceTest {
    FavouriteFoodRecipeFrontService favouriteFoodRecipeFrontService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        favouriteFoodRecipeFrontService = new FavouriteFoodRecipeFrontService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("Get the FoodRecipe Details")
    class RequestGetSaveRecipesDetails {

        @Test
        void testGetFoodRecipeDetails() {
            when(persistenceServiceClient.getFoodRecipesDetails(any())).thenReturn(MockDataProvider.getSearchFoodRecipesDetails());
            FavouriteFoodRecipesResponse FoodRecipeDetails = favouriteFoodRecipeFrontService.getFoodRecipeDetails();
            assertNotNull(FoodRecipeDetails);
            //  assertEquals(1, FoodRecipeDetails.getFoodRecipe().get(0).getFoodRecipeId());
            //   assertEquals("chicken", FoodRecipeDetails.getFoodRecipe().get(0).getFoodRecipeName());
            //  verify(persistenceServiceClient, times(1)).getFoodRecipeDetails(any());
        }


        @Test
        void testGetFoodRecipeWhenDetailsISNotFound() {
            when(persistenceServiceClient.getFoodRecipesDetails(any())).thenThrow(new ServiceException("FoodRecipe Details is not Found"));
            ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                    favouriteFoodRecipeFrontService.getFoodRecipeDetails());
            verify(persistenceServiceClient, times(1)).getFoodRecipesDetails(any());
            assertEquals("FoodRecipe Details is not Found", invalidRequestException.getMessage());
        }

        @Test
        void testSaveFoodRecipeDetails() {
            when(persistenceServiceClient.saveOrUpdateFoodRecipesDetails(any(FavouriteFoodRecipesRequest.class), any())).thenReturn(true);
            boolean isFoodRecipeSaved = favouriteFoodRecipeFrontService.saveOrUpdateFoodRecipeDetails(MockDataProvider.getFavouriteFoodRecipesServiceRequest());
            assertTrue(isFoodRecipeSaved);
            verify(persistenceServiceClient, times(1)).saveOrUpdateFoodRecipesDetails(any(FavouriteFoodRecipesRequest.class), any());
        }

        @Test
        void testGetFoodRecipeDetailsWhenFoodRecipeDetailsNull() {
            when(persistenceServiceClient.saveOrUpdateFoodRecipesDetails(any(FavouriteFoodRecipesRequest.class), any())).thenThrow(new ServiceException("No FoodRecipe returned from persistence service"));
            var request = MockDataProvider.getInvalidFavouriteFoodRecipesServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    favouriteFoodRecipeFrontService.saveOrUpdateFoodRecipeDetails(request));
            assertEquals("No FoodRecipe returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveOrUpdateFoodRecipesDetails(any(FavouriteFoodRecipesRequest.class), any());
        }
    }
}