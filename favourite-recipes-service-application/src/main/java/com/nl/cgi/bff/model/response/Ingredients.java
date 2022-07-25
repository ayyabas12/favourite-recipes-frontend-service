package com.nl.cgi.bff.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Ingredients {
    private long ingredientId;
    private String ingredientName;
    private String imageurl;
}

