package com.nl.cgi.bff.web;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.model.request.DishRequest;
import com.nl.cgi.bff.model.request.FavouriteFoodRecipesRequest;
import com.nl.cgi.bff.model.response.DishesResponse;
import com.nl.cgi.bff.model.response.FavouriteFoodRecipesResponse;
import com.nl.cgi.bff.model.response.Response;
import com.nl.cgi.bff.service.DishesFrontService;
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
@RequestMapping("/dish-frontend-service/dish-details")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class DishFrontController {

    private final DishesFrontService recipesFrontService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve dish details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the dish details", produces = "application/json", value = "Gets the Food dish details")
    public ResponseEntity<DishesResponse> getDishesDetails() {
        log.info("Inside the get dish details");
        return ResponseEntity.ok(recipesFrontService.getDishesDetails());
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved dishes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "save the Recipes details", produces = "application/json", value = "save the dishes details")
    public ResponseEntity<Boolean> updateDishesDetails(@Valid @RequestBody DishRequest dishRequest) {
        log.info("Inside save dish method call");
        return ResponseEntity.ok(recipesFrontService.saveOrUpdateDishesDetails(dishRequest));
    }


    @DeleteMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete dishes", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })

    @ApiOperation(notes = "delete the dish details", produces = "application/json", value = "delete the dishes details")
    public ResponseEntity<Boolean> deleteDishesDetails(@Valid @RequestParam("dishId") Long dishId) {
        log.info("Inside delete request Dishes method call");
        return ResponseEntity.ok(recipesFrontService.deleteDishesDetails(dishId));
    }




}
