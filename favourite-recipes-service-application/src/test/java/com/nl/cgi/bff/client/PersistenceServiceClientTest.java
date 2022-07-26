package com.nl.cgi.bff.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nl.cgi.bff.exception.ExceptionResponse;
import com.nl.cgi.bff.exception.ServiceException;
import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.mockdata.StubServerConfig;
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
                    persistenceServiceClient.getRecipesDetails(1,"url")
            );
            assertEquals("Bad Request error from service", exception.getErrorDetail().getErrorMessage());
            assertEquals("Exception occurred during get dishes details call", exception.getMessage());

        }

    }

    @Nested
    @DisplayName("Test Save dish es Service Details")
    class TestSaveDishesDetailsPSService {

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
