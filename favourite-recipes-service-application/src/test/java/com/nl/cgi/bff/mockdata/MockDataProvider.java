package com.nl.cgi.bff.mockdata;

import com.google.gson.JsonObject;
import com.nl.cgi.bff.model.Enum.CuisineCategory;
import com.nl.cgi.bff.model.request.*;
import com.nl.cgi.bff.model.response.*;

import java.util.*;

public class MockDataProvider {


    public static RecipesResponse getDishesDetails() {
        var dishes1 = Recipes.builder().recipeId(1).recipeName("chicken breast curry").category(CuisineCategory.NON_VEGETARIAN).quantity(2).instructions("chicken recipe").build();
        return RecipesResponse.builder().recipes(dishes1).build();
    }

    public static JsonObject getDishesSaveRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("recipeName", "chicken breast")
                .withProperty("quantity", "2")
                .withProperty("instructions", "chicken breast make ")
                .withProperty("category", CuisineCategory.NON_VEGETARIAN.name())
                .build();
    }

    public static JsonObject getDishesUpdateRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("recipeId", "1")
                .withProperty("recipeName", "chicken breast")
                .withProperty("quantity", "2")
                .withProperty("instructions", "chicken breast make ")
                .withProperty("category", CuisineCategory.NON_VEGETARIAN.name())
                .build();
    }

    public static JsonObject getInvalidServiceRequest() {
        return MockJsonBuilder.aRequest()
                .build();
    }


    public static RecipesRequest getDishesServiceRequest() {
        return RecipesRequest.builder().recipeId(1).build();
    }


    public static IngredientsResponse getIngredientsDetails() {
        var ingredient1 = Ingredients.builder().ingredientsId(1).ingredientName("chicken").build();
        return IngredientsResponse.builder().ingredients(ingredient1).build();
    }





    public static JsonObject getIngredientsSaveRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("ingredientName", "chicken breast")
                .withProperty("imageURL", "chicken.png")
                .build();
    }

    public static JsonObject getIngredientsUpdateRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("ingredientId", "1")
                .withProperty("ingredientName", "chicken")
                .withProperty("imageURL", "chicken.png")
                .build();
    }


    public static JsonObject getFoodRecipesSaveRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("recipe", "1")
                .withProperty("ingredientId", "1")
                .build();
    }

    public static JsonObject getFoodRecipesUpdateRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("ingredientId", "1")
                .withProperty("recipe", "1")
                .withProperty("id", "1")
                .build();
    }

    public static JsonObject getFoodRecipesSearchRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("quantity", "1")
                .withProperty("instructions", "chicken with food")
                .withProperty("category", CuisineCategory.VEGETARIAN.name())
                .build();
    }

    public static RecipesRequest getDishServiceRequest() {
        return RecipesRequest.builder()
                .recipeName("Chicken")
                .category(CuisineCategory.NON_VEGETARIAN)
                .quantity(2)
                .instructions("instructions")
                .build();
    }

    public static RecipesRequest getInvalidDishesServiceRequest() {
        return RecipesRequest.builder().build();
    }


    public static IngredientsRequest getIngredientsServiceRequest() {
        return IngredientsRequest.builder()
                .ingredientName("Chicken")
                .imageURL("imageURL")
                .build();
    }

    public static IngredientsRequest getInvalidIngredientsServiceRequest() {
        return IngredientsRequest.builder()
                .imageURL("imageURL")
                .build();
    }
}
