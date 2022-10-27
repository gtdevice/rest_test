package com.example.test.model.rest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonProperty("id")
	private UUID id;

	@JsonProperty("message")
	private String message;

	@JsonProperty("timestamp")
	private Timestamp timestamp;
}
