package com.example.test.service;

import com.example.test.model.rest.SuperHeroDto;

import java.util.List;

public interface SuperHeroService {

	List<SuperHeroDto> getAllsuperHeroes();

	void saveSuperHero(SuperHeroDto body);

	SuperHeroDto getSuperHeroeByAlias(String alias);
}
