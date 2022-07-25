package com.nl.cgi.bff.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nl.cgi.bff.model.Enum.CuisineCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipes {
    private long recipeId;
    private String recipeName;
    private long quantity;
    private String instructions;
    private CuisineCategory category;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> ingredientsList;
}

