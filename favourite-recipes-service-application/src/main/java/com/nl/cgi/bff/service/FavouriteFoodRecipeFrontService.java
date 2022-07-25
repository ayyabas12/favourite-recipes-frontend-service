package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.model.request.DishRequest;
import com.nl.cgi.bff.model.request.DishSearchRequest;
import com.nl.cgi.bff.model.request.FavouriteFoodRecipesRequest;
import com.nl.cgi.bff.model.response.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class FavouriteFoodRecipeFrontService {
    private final PersistenceServiceClient persistenceServiceClient;
    private final PersistenceServiceProperties persistenceServiceProperties;

    public FavouriteFoodRecipesResponse getFoodRecipeDetails() {
        List<FavouriteFoodRecipeDetails> foodRecipeDetails =  persistenceServiceClient.getFoodRecipesDetails(persistenceServiceProperties.getFoodRecipesDetailsUrl()).getFoodRecipeDetails();
        Map<Dishes, List<Ingredients>> file= foodRecipeDetails.stream()
                .collect(Collectors.groupingBy(
                        FavouriteFoodRecipeDetails::getDishes,
                        Collectors.mapping(
                                 FavouriteFoodRecipeDetails::getIngredients, Collectors.toList()
                        )));
        return FavouriteFoodRecipesResponse.builder().foodRecipeDetails(file).build();
    }



    public boolean saveOrUpdateFoodRecipeDetails(FavouriteFoodRecipesRequest recipesRequest) {
        return persistenceServiceClient.saveOrUpdateFoodRecipesDetails(recipesRequest, persistenceServiceProperties.getFoodRecipesDetailsUrl());
    }

    public boolean deleteFoodRecipeDetails(Long id) {
        return persistenceServiceClient.deleteFoodRecipesDetails(id, persistenceServiceProperties.getFoodRecipesDetailsUrl());
    }


    public Response searchFoodRecipeDetails(DishSearchRequest recipesRequest) {
        return persistenceServiceClient.searchFoodRecipesDetails(recipesRequest, persistenceServiceProperties.getFoodRecipesDetailsUrl());
    }

    public Response getRecipesNamesByInstructions(String instructions) {
        return persistenceServiceClient.getFoodRecipesDetailsFilterByInstructions(instructions, persistenceServiceProperties.getFoodRecipesDetailsUrl());
    }
}
