package com.nl.cgi.bff.model.response;

import com.nl.cgi.bff.model.Enum.CuisineCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dishes {
    private long dishId;
    private String dishName;
    private long quantity;
    private String instructions;
    private CuisineCategory category;
}

