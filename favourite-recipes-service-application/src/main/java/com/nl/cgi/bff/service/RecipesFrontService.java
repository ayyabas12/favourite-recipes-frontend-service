package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.model.request.RecipesRequest;
import com.nl.cgi.bff.model.response.RecipesResponse;
import com.nl.cgi.bff.model.response.SearchRecipesResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class RecipesFrontService {
    private final PersistenceServiceClient persistenceServiceClient;
    private final PersistenceServiceProperties persistenceServiceProperties;

    /**
     *
     * @param id recipe id
     * @return recipe response from PS
     */
    public RecipesResponse getRecipesDetails(long id) {
        return persistenceServiceClient.getRecipesDetails(id, persistenceServiceProperties.getRecipesDetailsUrl());
    }

    /**
     *
     * @param recipesRequest request
     * @return recipe response from PS
     */
    public RecipesResponse saveRecipesDetails(RecipesRequest recipesRequest) {
        return persistenceServiceClient.saveRecipesDetails(recipesRequest, persistenceServiceProperties.getRecipesDetailsUrl());
    }

    /**
     *
     * @param id recipe id
     * @param recipesRequest request
     * @return recipe response from PS
     */
    public RecipesResponse updateRecipesDetails(long id, RecipesRequest recipesRequest) {
        return persistenceServiceClient.updateRecipesDetails(id, recipesRequest, persistenceServiceProperties.getRecipesDetailsUrl());
    }

    /**
     *
     * @param id recipe id to delete
     * @return boolean
     */
    public boolean deleteRecipesDetails(Long id) {
        return persistenceServiceClient.deleteRecipeDetails(id, persistenceServiceProperties.getRecipesDetailsUrl());
    }

    /**
     *
     * @param category of recipes
     * @param quantity of recipes
     * @param instructions to prepare recipes
     * @return filter of recipes
     */
    public SearchRecipesResponse searchFoodRecipeDetails(String category , long quantity , String instructions) {
        return persistenceServiceClient.searchFoodRecipesDetails(category,quantity, instructions, persistenceServiceProperties.getSearchRecipesUrl());
    }

}
