package com.nl.cgi.bff.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientsRequest implements Serializable {
    @JsonProperty(value = "ingredientName")
    @ApiModelProperty(required = true, notes = "ingredientsName of the dishes")
    @NotBlank(message = "ingredientsName cannot be null")
    private String ingredientName;

    @NotBlank
    @ApiModelProperty(required = true, notes = "imageURL of the dishes")
    @NotBlank(message = "imageURL cannot be null")
    private String imageURL;
}
