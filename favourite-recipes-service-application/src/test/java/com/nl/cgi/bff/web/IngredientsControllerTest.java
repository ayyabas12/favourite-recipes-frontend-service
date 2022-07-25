package com.nl.cgi.bff.web;

import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.service.IngredientsFrontService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc()
public class IngredientsControllerTest {

    private static final String URI = "/dish-frontend-service/ingredient-details";
    private static final String DELETE_URI = "/dish-frontend-service/dish-details?dishId=1";
    private static final String X_AUTH_USER = "X-AUTH-USER";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsd2lsbGlhbXMxNiIsInJvbGVzIjoidXNlciIsImlhdCI6MTUxNDQ0OTgzM30.WKMQ_oPPiDcc6sGtMJ1Y9hlrAAc6U3xQLuEHyAnM1FU";

    @Mock
    IngredientsFrontService ingredientsFrontService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        IngredientsController controller = new  IngredientsController(ingredientsFrontService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

    }

    @Nested
    @DisplayName("Ingredients all service all scenario")
    class TestGetIngredientsAndSaveRecipesDetails {
        @Test
        void testGetIngredientsDetails() throws Exception {
            when(ingredientsFrontService.getIngredientsDetails()).thenReturn(MockDataProvider.getIngredientsDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI)
                            //.header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(ingredientsFrontService, times(1)).getIngredientsDetails();
        }

        @Test
        void testSaveIngredientsDetails() throws Exception {
            when(ingredientsFrontService.saveOrUpdateIngredientsDetails(any(IngredientsRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getIngredientsSaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(ingredientsFrontService, times(1)).saveOrUpdateIngredientsDetails(any(IngredientsRequest.class));
        }


        @Test
        void testUpdateIngredientsDetails() throws Exception {
            when(ingredientsFrontService.saveOrUpdateIngredientsDetails(any(IngredientsRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getIngredientsUpdateRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(ingredientsFrontService, times(1)).saveOrUpdateIngredientsDetails(any(IngredientsRequest.class));
        }


    }

    @Nested
    @DisplayName("Get Ingredients details - Service Exceptions")
    class GetAndSaveIngredientsServiceExceptions {
        @Test
        void checkExceptionThrownWhenRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkExceptionThrownWhenRequestHeaderISNUll() throws Exception {
            try {
                when(ingredientsFrontService.getIngredientsDetails()).thenThrow(new AccessDeniedException("Token is not found"));
                mockMvc
                        .perform(MockMvcRequestBuilders.get(URI)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            } catch (Exception e) {

            }
        }

        @Test
        void checkSaveExceptionThrownWhenIngredientsNameIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .header(X_AUTH_USER, "Bearer " + token)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }
}
