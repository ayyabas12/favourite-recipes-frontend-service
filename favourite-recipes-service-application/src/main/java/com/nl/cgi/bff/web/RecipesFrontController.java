package com.nl.cgi.bff.web;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.model.request.RecipesRequest;
import com.nl.cgi.bff.model.response.RecipesResponse;
import com.nl.cgi.bff.model.response.SearchRecipesResponse;
import com.nl.cgi.bff.service.RecipesFrontService;
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
@RequestMapping("/dish-frontend-service/recipes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class RecipesFrontController {

    private final RecipesFrontService recipesFrontService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve dish details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the dish details", produces = "application/json", value = "Gets the Food dish details")
    public ResponseEntity<RecipesResponse> getDishesDetails(final @Valid @RequestParam("id") long id) {
        log.info("Inside the get dish details");
        return ResponseEntity.ok(recipesFrontService.getRecipesDetails(id));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved dishes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "save the Recipes details", produces = "application/json", value = "save the dishes details")
    public ResponseEntity<RecipesResponse> saveDishesDetails(@Valid @RequestBody RecipesRequest recipesRequest) {
        log.info("Inside save dish method call");
        return ResponseEntity.ok(recipesFrontService.saveRecipesDetails(recipesRequest));
    }

    @PutMapping("/{id}/ingredients")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated dishes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "save the Recipes details", produces = "application/json", value = "save the dishes details")
    public ResponseEntity<RecipesResponse> updateDishesDetails(@PathVariable("id") long id, @Valid @RequestBody RecipesRequest recipesRequest) {
        log.info("Inside save dish method call");
        return ResponseEntity.ok(recipesFrontService.updateRecipesDetails(id, recipesRequest));
    }


    @DeleteMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete dishes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })

    @ApiOperation(notes = "delete the dish details", produces = "application/json", value = "delete the dishes details")
    public ResponseEntity<Boolean> deleteDishesDetails(@Valid @RequestParam("id") long id) {
        log.info("Inside delete request Dishes method call");
        return ResponseEntity.ok(recipesFrontService.deleteRecipesDetails(id));
    }

    @GetMapping("/search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully filter food recipes details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the dish details", produces = "application/json", value = "Gets the Food Recipe details")
    public ResponseEntity<SearchRecipesResponse> searchFoodDishesDetails(@RequestParam("category") String category, @RequestParam("quantity") long quantity, @RequestParam("instructions") String instructions) {
        log.info("Inside the search food recipes details");
        return ResponseEntity.ok(recipesFrontService.searchFoodRecipeDetails(category, quantity, instructions));
    }

}
