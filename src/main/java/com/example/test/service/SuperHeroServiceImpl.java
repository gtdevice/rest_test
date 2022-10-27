package com.example.test.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.test.exception.SuperHeroAlreadyExistException;
import com.example.test.exception.SuperHeroNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.model.bdmodel.SuperHeroModel;
import com.example.test.model.rest.SuperHeroDto;
import com.example.test.repository.SuperHeroRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuperHeroServiceImpl implements SuperHeroService {

	private final SuperHeroRepository superHeroRepository;

	@Autowired
	public SuperHeroServiceImpl(SuperHeroRepository superHeroRepository) {
		this.superHeroRepository = superHeroRepository;
	}

	@Override
	public List<SuperHeroDto> getAllsuperHeroes() {
		List<SuperHeroModel> superHeroModelList = superHeroRepository.findAll();
		return superHeroModelList.stream().map(SuperHeroDto::new).collect(Collectors.toList());
	}

	@Override
	public void saveSuperHero(SuperHeroDto body) {
		SuperHeroModel superHeroModel = new SuperHeroModel(body);
		SuperHeroModel s = superHeroRepository.findByAlias(body.getAlias());
		if (s!=null){
			throw new SuperHeroAlreadyExistException(body.getAlias());
		}
		superHeroRepository.saveAndFlush(superHeroModel);
	}

	@Override public SuperHeroDto getSuperHeroeByAlias(String alias) {
		SuperHeroModel model = superHeroRepository.findByAlias(alias);
		if (model==null){
			throw new SuperHeroNotFoundException(alias);
		}
		return new SuperHeroDto(model);
	}
}
