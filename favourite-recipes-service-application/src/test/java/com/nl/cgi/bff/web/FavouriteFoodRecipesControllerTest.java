package com.nl.cgi.bff.web;

import com.nl.cgi.bff.mockdata.MockDataProvider;
import com.nl.cgi.bff.model.request.DishRequest;
import com.nl.cgi.bff.model.request.DishSearchRequest;
import com.nl.cgi.bff.model.request.FavouriteFoodRecipesRequest;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.service.FavouriteFoodRecipeFrontService;
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
public class FavouriteFoodRecipesControllerTest {

    private static final String URI = "/dish-frontend-service/recipe-details";
    private static final String DELETE_URI = "/dish-frontend-service/recipe-details?foodRecipeId=1";

    private static final String SEARCH_URI = "/dish-frontend-service/recipe-details/search?searchString=1";
    private static final String X_AUTH_USER = "X-AUTH-USER";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsd2lsbGlhbXMxNiIsInJvbGVzIjoidXNlciIsImlhdCI6MTUxNDQ0OTgzM30.WKMQ_oPPiDcc6sGtMJ1Y9hlrAAc6U3xQLuEHyAnM1FU";

    @Mock
    FavouriteFoodRecipeFrontService foodRecipeFrontService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        FavouriteFoodRecipesController controller = new FavouriteFoodRecipesController(foodRecipeFrontService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

    }

    @Nested
    @DisplayName("Ingredients all service all scenario")
    class TestGetAndSaveRecipesDetails {
        @Test
        void testGetIngredientsDetails() throws Exception {
            when(foodRecipeFrontService.getFoodRecipeDetails()).thenReturn(MockDataProvider.getFoodRecipesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI)
                            //.header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(foodRecipeFrontService, times(1)).getFoodRecipeDetails();
        }

        @Test
        void testSaveIngredientsDetails() throws Exception {
            when(foodRecipeFrontService.saveOrUpdateFoodRecipeDetails(any(FavouriteFoodRecipesRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.put(URI)
                            .content(MockDataProvider.getFoodRecipesSaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(foodRecipeFrontService, times(1)).saveOrUpdateFoodRecipeDetails(any(FavouriteFoodRecipesRequest.class));
        }


        @Test
        void testUpdateIngredientsDetails() throws Exception {
            when(foodRecipeFrontService.saveOrUpdateFoodRecipeDetails(any(FavouriteFoodRecipesRequest.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.put(URI)
                            .content(MockDataProvider.getFoodRecipesSaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(foodRecipeFrontService, times(1)).saveOrUpdateFoodRecipeDetails(any(FavouriteFoodRecipesRequest.class));
        }

        @Test
        void testDeleteDishesDetails() throws Exception {
            when(foodRecipeFrontService.deleteFoodRecipeDetails(any(Long.class))).thenReturn(true);
            mockMvc
                    .perform(MockMvcRequestBuilders.delete(DELETE_URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(foodRecipeFrontService, times(1)).deleteFoodRecipeDetails(any(Long.class));
        }

        @Test
        void testSearchIngredientsDetails() throws Exception {
            when(foodRecipeFrontService.searchFoodRecipeDetails(any(DishSearchRequest.class))).thenReturn(MockDataProvider.getSearchFoodRecipesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getFoodRecipesSearchRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(foodRecipeFrontService, times(1)).searchFoodRecipeDetails(any(DishSearchRequest.class));
        }

        @Test
        void testGetIngredientsDetailsByInstructions() throws Exception {
            when(foodRecipeFrontService.getRecipesNamesByInstructions(any(String.class))).thenReturn(MockDataProvider.getSearchFoodRecipesDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(SEARCH_URI)
                            //.header(X_AUTH_USER, "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(foodRecipeFrontService, times(1)).getRecipesNamesByInstructions(any(String.class));
        }

    }

    @Nested
    @DisplayName("Get Ingredients details - Service Exceptions")
    class GetAndSaveIngredientServiceExceptions {
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
                when(foodRecipeFrontService.getFoodRecipeDetails()).thenThrow(new AccessDeniedException("Token is not found"));
                mockMvc
                        .perform(MockMvcRequestBuilders.get(URI)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            } catch (Exception e) {

            }
        }

        //TODO
        @Test
        void checkSaveExceptionThrownWhenFaNameIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.put(URI)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
}
