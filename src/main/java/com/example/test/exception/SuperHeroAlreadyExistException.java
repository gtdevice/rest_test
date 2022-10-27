package com.example.test.exception;

public class SuperHeroAlreadyExistException extends RuntimeException{
	public SuperHeroAlreadyExistException(String message) {
		super("SuperHero "+ message +" already exist. Create the new one with different alias");
	}
}
