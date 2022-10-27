package com.example.test.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.ValidationException;

import com.example.test.exception.SuperHeroNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.test.exception.SuperHeroAlreadyExistException;
import com.example.test.model.rest.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler {

	@ExceptionHandler(SuperHeroAlreadyExistException.class)
	public ResponseEntity<ErrorResponse> superHeroAlreadyExistHandler(Exception exception) {
		log.warn("ExceptionHandler. SuperHeroAlreadyExistException - Exception {} Occurred", exception.getMessage());
		ErrorResponse responseBody = ErrorResponse.builder().id(UUID.randomUUID())
				.timestamp(Timestamp.valueOf(LocalDateTime.now())).message(exception.getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(responseBody);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> validationExceptionHandler(Exception exception) {
		log.warn("ExceptionHandler. ValidationException - Exception {} Occurred", exception.getMessage());
		ErrorResponse responseBody = ErrorResponse.builder().id(UUID.randomUUID())
				.timestamp(Timestamp.valueOf(LocalDateTime.now())).message(exception.getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(responseBody);
	}

	@ExceptionHandler(SuperHeroNotFoundException.class)
	public ResponseEntity<ErrorResponse> superHeroNotFoundExceptionHandler(Exception exception) {
		log.warn("ExceptionHandler. SuperHeroNotFoundException - Exception {} Occurred", exception.getMessage());
		ErrorResponse responseBody = ErrorResponse.builder().id(UUID.randomUUID())
				.timestamp(Timestamp.valueOf(LocalDateTime.now())).message(exception.getMessage()).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(responseBody);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> commonExceptionHandler(Exception exception) {
		log.error("ExceptionHandler. - Exception {} Occurred", exception.getMessage());
		exception.printStackTrace();
		ErrorResponse responseBody = ErrorResponse.builder().id(UUID.randomUUID())
				.timestamp(Timestamp.valueOf(LocalDateTime.now())).message("Internal exception").build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
				.body(responseBody);
	}
}
