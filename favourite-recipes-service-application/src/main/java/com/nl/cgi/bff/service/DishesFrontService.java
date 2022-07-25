package com.nl.cgi.bff.service;

import com.nl.cgi.bff.client.PersistenceServiceClient;
import com.nl.cgi.bff.config.properties.PersistenceServiceProperties;
import com.nl.cgi.bff.model.request.DishRequest;
import com.nl.cgi.bff.model.request.FavouriteFoodRecipesRequest;
import com.nl.cgi.bff.model.response.DishesResponse;
import com.nl.cgi.bff.model.response.FavouriteFoodRecipesResponse;
import com.nl.cgi.bff.model.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class DishesFrontService {
    private final PersistenceServiceClient persistenceServiceClient;
    private final PersistenceServiceProperties persistenceServiceProperties;

    public DishesResponse getDishesDetails() {
        return persistenceServiceClient.getDishDetails(persistenceServiceProperties.getDishesDetailsUrl());
    }

    public boolean saveOrUpdateDishesDetails(DishRequest dishRequest) {
        return persistenceServiceClient.saveOrUpdateDishDetails(dishRequest, persistenceServiceProperties.getDishesDetailsUrl());
    }

    public boolean deleteDishesDetails(Long dishId) {
        return persistenceServiceClient.deleteDishDetails(dishId, persistenceServiceProperties.getDishesDetailsUrl());
    }

}
