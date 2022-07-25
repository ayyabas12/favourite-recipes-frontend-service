package com.nl.cgi.bff.web;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.model.request.DishRequest;
import com.nl.cgi.bff.model.request.DishSearchRequest;
import com.nl.cgi.bff.model.request.FavouriteFoodRecipesRequest;
import com.nl.cgi.bff.model.response.DishesResponse;
import com.nl.cgi.bff.model.response.FavouriteFoodRecipesResponse;
import com.nl.cgi.bff.model.response.Response;
import com.nl.cgi.bff.service.FavouriteFoodRecipeFrontService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/dish-frontend-service/recipe-details")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class FavouriteFoodRecipesController {

    private final FavouriteFoodRecipeFrontService favouriteFoodRecipeFrontService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve food recipe details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the dish details", produces = "application/json", value = "Gets the Food Recipe details")
    public ResponseEntity<FavouriteFoodRecipesResponse> getFoodDishesDetails() {
        log.info("Inside the get food recipes details");
        return ResponseEntity.ok(favouriteFoodRecipeFrontService.getFoodRecipeDetails());
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved food recipes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })

    @ApiOperation(notes = "save the food recipes details", produces = "application/json", value = "save the food recipes details")
    public ResponseEntity<Boolean> updateDishesDetails(@Valid @RequestBody FavouriteFoodRecipesRequest recipesRequest) {
        log.info("Inside save request food recipes method call");
        return ResponseEntity.ok(favouriteFoodRecipeFrontService.saveOrUpdateFoodRecipeDetails(recipesRequest));
    }


    @DeleteMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete food recipes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "save the Recipes details", produces = "application/json", value = "delete the food recipes details")
    public ResponseEntity<Boolean> deleteDishesDetails(@Valid @RequestParam("foodRecipeId") Long foodRecipeId) {
        log.info("Inside delete request food recipes method call");
        return ResponseEntity.ok(favouriteFoodRecipeFrontService.deleteFoodRecipeDetails(foodRecipeId));
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully food recipes details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the dish details", produces = "application/json", value = "Gets the Food Recipe details")
    public ResponseEntity<Response> searchFoodDishesDetails(@Valid @RequestBody DishSearchRequest dishRequest) {
        log.info("Inside the search food recipes details");
        return ResponseEntity.ok(favouriteFoodRecipeFrontService.searchFoodRecipeDetails(dishRequest));
    }


    /**
     *
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<Response> getRecipesDetailsBySearchInstruction(@RequestParam(name = "searchString") String searchString) {
        log.info("Inside filter by instructions request dishes");
        Response response= favouriteFoodRecipeFrontService.getRecipesNamesByInstructions(searchString);
        return ResponseEntity.ok(response);
    }

}
