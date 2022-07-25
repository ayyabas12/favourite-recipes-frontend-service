package com.nl.cgi.bff.web;

import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.RecipesRequest;
import com.nl.cgi.bff.service.RecipesFrontService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc()
public class RecipesFrontControllerTest {

    private static final String URI = "/dish-frontend-service/recipes?id=1";
    private static final String DELETE_URI = "/dish-frontend-service/recipes?id=1";
    private static final String X_AUTH_USER = "X-AUTH-USER";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsd2lsbGlhbXMxNiIsInJvbGVzIjoidXNlciIsImlhdCI6MTUxNDQ0OTgzM30.WKMQ_oPPiDcc6sGtMJ1Y9hlrAAc6U3xQLuEHyAnM1FU";

    @Mock
    RecipesFrontService dishesFrontService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        RecipesFrontController stateController = new RecipesFrontController(dishesFrontService);
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .build();

    }

    @Nested
    @DisplayName("dishes all service all scenario")
    class TestGetAndSaveDishesDetails {
        @Test
        void testGetDishesDetails() throws Exception {
            when(dishesFrontService.getRecipesDetails(anyLong())).thenReturn(MockDataProvider.getDishesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI)
                            //.header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(dishesFrontService, times(1)).getRecipesDetails(anyLong());
        }

        @Test
        void testSaveDishesDetails() throws Exception {
            when(dishesFrontService.saveRecipesDetails(any(RecipesRequest.class))).thenReturn(MockDataProvider.getDishesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getDishesSaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(dishesFrontService, times(1)).saveRecipesDetails(any(RecipesRequest.class));
        }


        @Test
        void testUpdateDishesDetails() throws Exception {
            when(dishesFrontService.saveRecipesDetails(any(RecipesRequest.class))).thenReturn(MockDataProvider.getDishesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getDishesUpdateRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(dishesFrontService, times(1)).saveRecipesDetails(any(RecipesRequest.class));
        }

        @Test
        void testDeleteDishesDetails() throws Exception {
            when(dishesFrontService.deleteRecipesDetails(any(Long.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.delete(DELETE_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(dishesFrontService, times(1)).deleteRecipesDetails(any(Long.class));
        }
    }

    @Nested
    @DisplayName("Get dishes details - Service Exceptions")
    class GetAndSaveDishServiceExceptions {
        @Test
        void checkExceptionThrownWhenRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }


        @Test
        void checkSaveExceptionThrownWhenDishesNameIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }
}
