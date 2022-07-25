package com.nl.cgi.bff.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteFoodRecipesRequest implements Serializable {

    @JsonProperty(value = "id")
    private long id;

    @ApiModelProperty(required = true, notes = "dishId of the recipes")
    @JsonProperty(value = "dishId")
    private Long dishId;

    @ApiModelProperty(required = true, notes = "ingredientId of the recipes")
    @JsonProperty(value = "ingredientId")
    private Long ingredientId;
}
