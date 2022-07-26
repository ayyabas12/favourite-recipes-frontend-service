package com.nl.cgi.bff.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nl.cgi.bff.model.Enum.CuisineCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipesSearchRequest implements Serializable {


    @JsonProperty(value = "quantity")
    private int quantity;

    @JsonProperty(value = "instructions")
    private String instructions;

    @JsonProperty(value = "category")
    private CuisineCategory category;

    @JsonProperty(value = "ingredientId")
    private List<Long> ingredientId;


}
