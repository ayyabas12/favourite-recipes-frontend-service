package com.nl.cgi.bff.web;

import com.nl.cgi.bff.exception.ExceptionUtil;
import com.nl.cgi.bff.model.request.IngredientsRequest;
import com.nl.cgi.bff.model.response.IngredientsResponse;
import com.nl.cgi.bff.service.IngredientsFrontService;
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
import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/dish-frontend-service/ingredient")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class IngredientsController {

    private final IngredientsFrontService ingredientsFrontService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve ingredients details", response = List.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "Gets the ingredients details", produces = "application/json", value = "Gets the ingredients details")
    public ResponseEntity<IngredientsResponse> get(@RequestParam("id") Long id) {
        log.info("Inside the get ingredientsFrontService details");
        return ResponseEntity.ok(ingredientsFrontService.getIngredientsDetails(id));
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "save ingredients details", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "save the ingredients details", produces = "application/json", value = "save the ingredients details")
    public ResponseEntity<IngredientsResponse> save(@Valid @RequestBody IngredientsRequest ingredientsRequest) {
        log.info("Inside request ingredients method call");
        return ResponseEntity.ok(ingredientsFrontService.saveIngredientsDetails(ingredientsRequest));
    }

    @PutMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "update ingredients details", response = Boolean.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionUtil.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionUtil.class)
    })
    @ApiOperation(notes = "update the ingredients details", produces = "application/json", value = "save the ingredients details")
    public ResponseEntity<IngredientsResponse> update(@PathVariable("id") long id , @Valid @RequestBody IngredientsRequest ingredientsRequest) {
        log.info("Inside request ingredients method call");
        return ResponseEntity.ok(ingredientsFrontService.updateIngredientsDetails(id, ingredientsRequest));
    }

}
