package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.model.response.IngredientsResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class IngredientsFrontService {
    private final PersistenceServiceClient persistenceServiceClient;
    private final PersistenceServiceProperties persistenceServiceProperties;

    public IngredientsResponse getIngredientsDetails(final long id) {
        return persistenceServiceClient.getIngredientsDetails(id, persistenceServiceProperties.getIngredientsDetailsUrl());
    }

    public IngredientsResponse saveIngredientsDetails(final IngredientsRequest ingredientsRequest) {
        return persistenceServiceClient.saveIngredientsDetails(ingredientsRequest, persistenceServiceProperties.getIngredientsDetailsUrl());
    }

    public IngredientsResponse updateIngredientsDetails(final long id, final IngredientsRequest ingredientsRequest) {
        return persistenceServiceClient.updateIngredientsDetails(id, ingredientsRequest, persistenceServiceProperties.getIngredientsDetailsUrl());
    }

}
