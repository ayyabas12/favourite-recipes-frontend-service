package com.nl.cgi.bff.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nl.cgi.bff.model.Enum.CuisineCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishRequest implements Serializable {


    @JsonProperty(value = "dishId")
    private long dishId;

    @JsonProperty(value = "dishName")
    @ApiModelProperty(required = true, notes = "dishName of the recipes")
    @NotBlank(message = "dishName cannot be null")
    private String dishName;


    @ApiModelProperty(required = true, notes = "quantity of the recipes")
    @JsonProperty(value = "quantity")
    private int quantity;

    @ApiModelProperty(required = true, notes = "instructions of the recipes")
    @NotBlank(message = "instructions cannot be empty")
    @JsonProperty(value = "instructions")
    private String instructions;

    @ApiModelProperty(required = true, notes = "category of the recipes")
    @NotNull(message = "category cannot be empty")
    @JsonProperty(value = "category")
    private CuisineCategory category;

    @JsonProperty(value = "ingredientId")
    private List<Long> ingredientId;


}
