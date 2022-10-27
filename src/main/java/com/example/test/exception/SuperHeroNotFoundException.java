package com.example.test.exception;

public class SuperHeroNotFoundException extends RuntimeException{
	public SuperHeroNotFoundException(String message) {
		super("SuperHero "+ message +" was not found.");
	}
}
