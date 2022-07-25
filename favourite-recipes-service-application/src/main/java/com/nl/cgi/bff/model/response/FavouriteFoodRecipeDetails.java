package com.nl.cgi.bff.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteFoodRecipeDetails {
    private long id;
    private Dishes dishes;
    private Ingredients ingredients;
  }

