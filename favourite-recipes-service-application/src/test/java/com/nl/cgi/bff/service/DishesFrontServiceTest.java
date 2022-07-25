package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.DishRequest;
import com.nl.cgi.bff.model.response.DishesResponse;
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
public class DishesFrontServiceTest {
    DishesFrontService dishesFrontService;
    @Mock
    PersistenceServiceClient persistenceServiceClient;
    @Mock
    PersistenceServiceProperties persistenceServiceProperties;

    @BeforeEach
    public void setup() {
        dishesFrontService = new DishesFrontService(persistenceServiceClient, persistenceServiceProperties);
    }

    @Nested
    @DisplayName("Get the Dishes Details")
    class RequestGetSaveRecipesDetails {

        @Test
        void testGetDishesDetails() {
            when(persistenceServiceClient.getDishDetails(any())).thenReturn(MockDataProvider.getDishesDetails());
            DishesResponse dishesDetails = dishesFrontService.getDishesDetails();
            assertNotNull(dishesDetails);
            assertEquals(1, dishesDetails.getDishesNames().get(0).getDishId());
            assertEquals("chicken breast curry", dishesDetails.getDishesNames().get(0).getDishName());
            verify(persistenceServiceClient, times(1)).getDishDetails(any());
        }


        @Test
        void testGetDishesWhenDetailsISNotFound() {
            when(persistenceServiceClient.getDishDetails(any())).thenThrow(new ServiceException("Dishes Details is not Found"));
            ServiceException invalidRequestException = Assertions.<ServiceException>assertThrows(ServiceException.class, () ->
                    dishesFrontService.getDishesDetails());
            verify(persistenceServiceClient, times(1)).getDishDetails(any());
            assertEquals("Dishes Details is not Found", invalidRequestException.getMessage());
        }

        @Test
        void testSaveDishesDetails() {
            when(persistenceServiceClient.saveOrUpdateDishDetails(any(DishRequest.class), any())).thenReturn(true);
            boolean isDishesSaved = dishesFrontService.saveOrUpdateDishesDetails(MockDataProvider.getDishServiceRequest());
            assertTrue(isDishesSaved);
            verify(persistenceServiceClient, times(1)).saveOrUpdateDishDetails(any(DishRequest.class), any());
        }

        @Test
        void testGetDishesDetailsWhenDishesDetailsNull() {
            when(persistenceServiceClient.saveOrUpdateDishDetails(any(DishRequest.class), any())).thenThrow(new ServiceException("No Dishes returned from persistence service"));
            var request = MockDataProvider.getInvalidDishesServiceRequest();
            ServiceException invalidRequestException = Assertions.assertThrows(ServiceException.class, () ->
                    dishesFrontService.saveOrUpdateDishesDetails(request));
            assertEquals("No Dishes returned from persistence service", invalidRequestException.getMessage());
            verify(persistenceServiceClient, times(1)).saveOrUpdateDishDetails(any(DishRequest.class), any());
        }
    }
}