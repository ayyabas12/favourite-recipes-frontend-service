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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc()
public class RecipesFrontControllerTest {

    private static final String URI = "/dish-frontend-service/recipes?id=1";

    private static final String INVALID_URI = "/dish-frontend-service/recipes";
    private static final String DELETE_URI = "/dish-frontend-service/recipes?id=1";
    private static final String UPDATE_URI = "/dish-frontend-service/recipes/1/ingredients";

    private static final String SEARCH_URL = "/dish-frontend-service/recipes/search?category=\"VEGETARIAN\"&quantity=3&instructions=\"vegetarian\"";

    @Mock
    RecipesFrontService recipesFrontService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        RecipesFrontController stateController = new RecipesFrontController(recipesFrontService);
        mockMvc = MockMvcBuilders.standaloneSetup(stateController)
                .build();

    }

    @Nested
    @DisplayName("Recipes all service all scenario")
    class TestGetAndSaveRecipesDetails {
        @Test
        void testGetRecipesDetails() throws Exception {
            when(recipesFrontService.getRecipesDetails(anyLong())).thenReturn(MockDataProvider.getRecipesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(recipesFrontService, times(1)).getRecipesDetails(anyLong());
        }

        @Test
        void testSaveRecipesDetails() throws Exception {
            when(recipesFrontService.saveRecipesDetails(any(RecipesRequest.class))).thenReturn(MockDataProvider.getRecipesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getRecipesSaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(recipesFrontService, times(1)).saveRecipesDetails(any(RecipesRequest.class));
        }


        @Test
        void testUpdateRecipesDetails() throws Exception {
            when(recipesFrontService.updateRecipesDetails(anyLong(), any(RecipesRequest.class))).thenReturn(MockDataProvider.getRecipesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.put(UPDATE_URI)
                            .content(MockDataProvider.getRecipesUpdateRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(recipesFrontService, times(1)).updateRecipesDetails(anyLong(), any(RecipesRequest.class));
        }

        @Test
        void testDeleteRecipesDetails() throws Exception {
            when(recipesFrontService.deleteRecipesDetails(any(Long.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.delete(DELETE_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(recipesFrontService, times(1)).deleteRecipesDetails(any(Long.class));
        }

        @Test
        void testSearchRecipesDetails() throws Exception {
            when(recipesFrontService.searchFoodRecipeDetails(anyString(), anyLong(), anyString())).thenReturn(MockDataProvider.getRecipesDetailsByFilter());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(SEARCH_URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(recipesFrontService, times(1)).searchFoodRecipeDetails(anyString(), anyLong(), anyString());
        }
    }

    @Nested
    @DisplayName("Get Recipes details - Service Exceptions")
    class GetAndSaveDishServiceExceptions {
        @Test
        void checkExceptionThrownWhenRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.get(INVALID_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }


        @Test
        void checkSaveExceptionThrownWhenRequestNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkUpdateExceptionThrownWhenRequestNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.put(UPDATE_URI)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkSearchExceptionThrownWhenRequestNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.put(SEARCH_URL)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkDeleteExceptionThrownWhenRequestNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.put(SEARCH_URL)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }
    }
}
