package com.nl.cgi.bff.web;

import com.nl.cgi.bff.exception.ErrorDetail;
import com.nl.cgi.bff.exception.ServiceException;
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

    private static final String URI = "/dish-frontend-service/ingredient";
    private static final String UPDATE_URI = "/dish-frontend-service/ingredient/1";
    @Mock
    IngredientsFrontService ingredientsFrontService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        IngredientsController controller = new IngredientsController(ingredientsFrontService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

    }

    @Nested
    @DisplayName("Ingredients all service all scenario")
    class TestGetIngredientsAndSaveRecipesDetails {
        @Test
        void testGetIngredientsDetails() throws Exception {
            when(ingredientsFrontService.getIngredientsDetails(anyLong())).thenReturn(MockDataProvider.getIngredientsDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI + "/?id=1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(ingredientsFrontService, times(1)).getIngredientsDetails(anyLong());
        }

        @Test
        void testSaveIngredientsDetails() throws Exception {
            when(ingredientsFrontService.saveIngredientsDetails(any(IngredientsRequest.class))).thenReturn(MockDataProvider.getIngredientsDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getIngredientsSaveRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(ingredientsFrontService, times(1)).saveIngredientsDetails(any(IngredientsRequest.class));
        }


        @Test
        void testUpdateIngredientsDetails() throws Exception {
            when(ingredientsFrontService.updateIngredientsDetails(anyLong(), any(IngredientsRequest.class))).thenReturn(MockDataProvider.getIngredientsDetails());
            mockMvc
                    .perform(MockMvcRequestBuilders.put(UPDATE_URI)
                            .content(MockDataProvider.getIngredientsUpdateRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            verify(ingredientsFrontService, times(1)).updateIngredientsDetails(anyLong(), any(IngredientsRequest.class));
        }


    }

    @Nested
    @DisplayName("Get Ingredients details - Service Exceptions")
    class GetAndSaveIngredientsServiceExceptions {
        @Test
        void checkExceptionThrownWhenRequestIsNullGETIngredients() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.get(URI)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }


        @Test
        void checkSaveExceptionThrownWhenIngredientsNameIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(URI)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void checkUpdateExceptionThrownWhenIngredientsRequestIsNull() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.put(UPDATE_URI)
                            .content(MockDataProvider.getInvalidServiceRequest().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

    }
}
