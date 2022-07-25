package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.model.response.IngredientsResponse;
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
public class IngredientsFrontServiceTest {
    IngredientsFrontService ingredientsFrontService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        ingredientsFrontService = new IngredientsFrontService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("Get the Ingredients Details")
    class RequestGetSaveRecipesDetails {

        @Test
        void testGetIngredientsDetails() {
            when(persistenceServiceClient.getIngredientsDetails(anyLong(),any())).thenReturn(MockDataProvider.getIngredientsDetails());
            IngredientsResponse IngredientsDetails = ingredientsFrontService.getIngredientsDetails(1);
            assertNotNull(IngredientsDetails);
            assertEquals(1, IngredientsDetails.getIngredients().getIngredientsId());
            assertEquals("chicken", IngredientsDetails.getIngredients().getIngredientName());
            verify(persistenceServiceClient, times(1)).getIngredientsDetails(anyLong(),any());
        }


        @Test
        void testGetIngredientsWhenDetailsISNotFound() {
            when(persistenceServiceClient.getIngredientsDetails(anyLong(),any())).thenThrow(new ServiceException("Ingredients Details is not Found"));
            ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                    ingredientsFrontService.getIngredientsDetails(2));
            verify(persistenceServiceClient, times(1)).getIngredientsDetails(anyLong(),any());
            assertEquals("Ingredients Details is not Found", invalidRequestException.getMessage());
        }

        @Test
        void testSaveIngredientsDetails() {
            when(persistenceServiceClient.saveIngredientsDetails(any(IngredientsRequest.class), any())).thenReturn(MockDataProvider.getIngredientsDetails());
            IngredientsResponse IngredientsDetails = ingredientsFrontService.saveIngredientsDetails(MockDataProvider.getIngredientsServiceRequest());
            assertNotNull(IngredientsDetails);
            verify(persistenceServiceClient, times(1)).saveIngredientsDetails(any(IngredientsRequest.class), any());
        }

        @Test
        void testGetIngredientsDetailsWhenIngredientsDetailsNull() {
            when(persistenceServiceClient.saveIngredientsDetails(any(IngredientsRequest.class), any())).thenThrow(new ServiceException("No Ingredients returned from persistence service"));
            var request = MockDataProvider.getInvalidIngredientsServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    ingredientsFrontService.saveIngredientsDetails(request));
            assertEquals("No Ingredients returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveIngredientsDetails(any(IngredientsRequest.class), any());
        }
    }
}