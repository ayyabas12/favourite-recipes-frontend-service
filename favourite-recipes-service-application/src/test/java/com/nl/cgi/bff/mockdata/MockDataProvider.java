package com.nl.cgi.bff.mockdata;

import com.google.gson.JsonObject;
import com.nl.cgi.bff.model.Enum.CuisineCategory;
import com.nl.cgi.bff.model.request.*;
import com.nl.cgi.bff.model.response.*;

import java.util.*;

public class MockDataProvider {


    public static DishesResponse getDishesDetails(){
        var dishes1 = Dishes.builder().dishId(1).dishName("chicken breast curry").category(CuisineCategory.NON_VEGETARIAN).quantity(2).instructions("chicken recipe").build();
        var dishes2 = Dishes.builder().dishId(2).dishName("tomato soup recipe").category(CuisineCategory.VEGETARIAN).quantity(3).instructions("tomato recipe").build();
        return DishesResponse.builder().dishesNames(Arrays.asList(dishes1,dishes2)).build();
    }

    public static JsonObject getDishesSaveRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("dishName", "chicken breast")
                .withProperty("quantity", "2")
                .withProperty("instructions", "chicken breast make ")
                .withProperty("category", CuisineCategory.NON_VEGETARIAN.name())
                .build();
    }
    public static JsonObject getDishesUpdateRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("dishId", "1")
                .withProperty("dishName", "chicken breast")
                .withProperty("quantity", "2")
                .withProperty("instructions", "chicken breast make ")
                .withProperty("category", CuisineCategory.NON_VEGETARIAN.name())
                .build();
    }
    public static JsonObject getInvalidServiceRequest() {
        return MockJsonBuilder.aRequest()
                .build();
    }


    public static DishRequest getDishesServiceRequest() {
        return DishRequest.builder().dishId(1).build();
    }


    public static IngredientsResponse getIngredientsDetails() {
        var ingredient1 = Ingredients.builder().ingredientId(1).ingredientName("chicken").build();
        var ingredient2 = Ingredients.builder().ingredientId(2).ingredientName("tomoto").build();
        return IngredientsResponse.builder().ingredients(Arrays.asList(ingredient1, ingredient2)).build();
    }

    public static FavouriteFoodRecipesResponse getFoodRecipesDetails() {
        var dishes1 = Dishes.builder().dishId(1).dishName("chicken breast curry").category(CuisineCategory.NON_VEGETARIAN).quantity(2).instructions("chicken recipe").build();
        Map<Dishes, List<Ingredients>> foodRecipeDetails= new HashMap<>();
        foodRecipeDetails.put(dishes1, getIngredientsDetails().getIngredients());
        return FavouriteFoodRecipesResponse.builder().foodRecipeDetails(foodRecipeDetails).build();
    }
    public static Response getSearchFoodRecipesDetails(){
        var dishes = Dishes.builder().dishId(1).dishName("chicken breast curry").category(CuisineCategory.NON_VEGETARIAN).quantity(2).instructions("chicken recipe").build();
        var ingredient = Ingredients.builder().ingredientId(2).ingredientName("tomoto").build();
        List<FavouriteFoodRecipeDetails> foodRecipeDetails =new ArrayList<>();
        foodRecipeDetails.add(FavouriteFoodRecipeDetails.builder().id(1).dishes(dishes).ingredients(ingredient).build());
        return Response.builder().foodRecipeDetails(foodRecipeDetails).build();

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
                .withProperty("dishId", "1")
                .withProperty("ingredientId", "1")
                .build();
    }
    public static JsonObject getFoodRecipesUpdateRequest() {
        return MockJsonBuilder.aRequest()
                .withProperty("ingredientId", "1")
                .withProperty("dishId", "1")
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

    public static DishRequest getDishServiceRequest() {
        return DishRequest.builder()
                 .dishName("Chicken")
                .category(CuisineCategory.NON_VEGETARIAN)
                .quantity(2)
                .instructions("instructions")
                .build();
    }

    public static DishRequest getInvalidDishesServiceRequest() {
        return DishRequest.builder().build();
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

    public static FavouriteFoodRecipesRequest getFavouriteFoodRecipesServiceRequest() {
        return FavouriteFoodRecipesRequest.builder()
                .dishId(1L)
                .ingredientId(1L)
                .build();
    }

    public static FavouriteFoodRecipesRequest getInvalidFavouriteFoodRecipesServiceRequest() {
        return FavouriteFoodRecipesRequest.builder()
                .dishId(1L)
                .build();
    }
}
