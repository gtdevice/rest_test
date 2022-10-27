package com.example.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.test.model.rest.ErrorResponse;
import com.example.test.model.rest.SuperHeroDto;
import com.example.test.service.SuperHeroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@Tag(name = "SuperHeroes", description = "Controller for saving and retrieving superheroes.")
@RequestMapping("v1/superhero/")
public class SuperHeroController {

	private final SuperHeroService superHeroService;

	public SuperHeroController(SuperHeroService superHeroService) {
		this.superHeroService = superHeroService;
	}

	@Operation(description = "JSON REST-API that allows a User to retrieve all currently available Superheroes.", operationId = "get-Superheroes", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class))), responseCode = "200", headers = {
					@Header(name = "X-Total-Count", description = "Total amount of elements in the response.") }),
			@ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponse.class)), responseCode = "500", description = "Internal server error has occurred.") })
	@GetMapping(value = "/", produces = { "application/json" })
	public ResponseEntity<List<SuperHeroDto>> listSuperHeroes() {
		List<SuperHeroDto> allSuperHeroes = superHeroService.getAllsuperHeroes();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-Total-Count", String.valueOf(allSuperHeroes.size()));
		return new ResponseEntity<>(allSuperHeroes, httpHeaders, HttpStatus.OK);
	}

	@Operation(description = "JSON REST-API that allows a User to retrieve Superheroes by alias.", operationId = "get-Superheroes-alias", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHeroDto.class))), responseCode = "200"),
			@ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponse.class)), responseCode = "404", description = "Requested SuperHeroe was not found."),
			@ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponse.class)), responseCode = "500", description = "Internal server error has occurred.") })
	@GetMapping(value = "/{alias}", produces = { "application/json" })
	public ResponseEntity<SuperHeroDto> getSuperHeroeByAlias(@PathVariable("alias") String alias) {
		SuperHeroDto sh = superHeroService.getSuperHeroeByAlias(alias);
		return ResponseEntity.ok().body(sh);
	}

	@Operation(description = "Endpoint for saving superheroes in the application.", operationId = "save-superheroe", responses = {
			@ApiResponse(content = @Content(schema = @Schema()), responseCode = "200"),
			@ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponse.class)), responseCode = "400", description = "Bad Request. Superhero does not have valid format or already exists."),
			@ApiResponse(content = @Content(schema = @Schema(implementation = ErrorResponse.class)), responseCode = "500", description = "Internal server error has occurred.") })
	@PostMapping(value = "/", produces = { "application/json" })
	public ResponseEntity<List<SuperHeroDto>> saveSuperHero(@Valid @RequestBody SuperHeroDto body) {
		superHeroService.saveSuperHero(body);
		return ResponseEntity.ok().build();
	}
}
