package com.example.test.service;

import com.example.test.model.bdmodel.SuperHeroModel;
import com.example.test.model.rest.SuperHeroDto;
import com.example.test.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SuperHeroServiceImplTest {
	@Autowired
	SuperHeroRepository superHeroRepository;
	SuperHeroService superHeroService;

	@BeforeEach
	void init() {
		superHeroService = new SuperHeroServiceImpl(superHeroRepository);
		superHeroRepository.deleteAll();
	}

	@Test void saveSuperHero() {
		SuperHeroDto dto = SuperHeroDto.builder().alias("Iron Man").name("Tony Stark")
				.origin("Kidnapped in Afghanistan, created the first iron-man suit to escape.").build();
		superHeroService.saveSuperHero(dto);
		List<SuperHeroModel> models = superHeroRepository.findAll();
		assertEquals(1, models.size());
		SuperHeroModel expected = new SuperHeroModel(dto);
		assertNotSame(expected, models.get(0));
		assertEquals(expected, models.get(0));
	}
	@Test void saveSuperHeroFull() {
		SuperHeroDto dto = SuperHeroDto.builder().alias("Iron Man").name("Tony Stark")
				.origin("Kidnapped in Afghanistan, created the first iron-man suit to escape.")
				.powers(List.of(new String[] { "genius-intelligence", "wealth" }))
				.weapons(List.of(new String[] { "arc-reactor", "iron-man-suit", "iron-legion" }))
				.associations(List.of(new String[] { "war-machine", "avengers", "jarvis", "thanos", "pepper-potts" }))
				.build();
		superHeroService.saveSuperHero(dto);
		List<SuperHeroModel> models = superHeroRepository.findAll();
		assertEquals(1, models.size());
		SuperHeroModel expected = new SuperHeroModel(dto);
		assertNotSame(expected, models.get(0));
		assertEquals(expected, models.get(0));

	}

	@Test void saveSuperHeroAndGet() {
		SuperHeroDto dto = SuperHeroDto.builder().alias("Iron Man").name("Tony Stark")
				.origin("Kidnapped in Afghanistan, created the first iron-man suit to escape.").build();
		superHeroService.saveSuperHero(dto);
		SuperHeroDto dto2 = SuperHeroDto.builder().alias("Captain obvious").name("Brandon Moynihan")
				.origin("He always says things that are really obvious.").build();
		superHeroService.saveSuperHero(dto2);
		SuperHeroDto iron_man = superHeroService.getSuperHeroeByAlias("Iron Man");

		assertNotSame(dto, iron_man);
		assertEquals(dto, iron_man);
	}
}