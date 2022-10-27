package com.example.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.test.model.rest.SuperHeroDto;
import com.example.test.service.SuperHeroService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SuperHeroController.class)
class SuperHeroControllerTest {

	@MockBean
	SuperHeroService superHeroService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void listSuperHeroes() throws Exception {
		List<SuperHeroDto> superHeroes = createSuperHeroes();

		Mockito.when(superHeroService.getAllsuperHeroes()).thenReturn(superHeroes);

		mockMvc.perform(get("/v1/superhero/")).andExpect(status().isOk())
				.andExpect(header().string("X-Total-Count", "2")).andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].alias", Matchers.is("Captain Marvel")))
				.andExpect(jsonPath("$[1].name", Matchers.is("Tony Stark")));
	}

	@Test
	void getSuperHeroe() throws Exception {
		SuperHeroDto superHeroe = SuperHeroDto.builder()
				.alias("Captain Marvel")
				.name("Carol Danvers")
				.origin("Exposed to Space Stone reactor overload.")
				.powers(List.of(new String[] { "photon-blast", "flight", "super-strength", "healing" }))
				.associations(List.of(new String[] { "space-stone", "skrulls", "photon", "kree", "avengers" }))
				.build();

		Mockito.when(superHeroService.getSuperHeroeByAlias("Captain Marvel")).thenReturn(superHeroe);

		mockMvc.perform(get("/v1/superhero/Captain Marvel")).andExpect(status().isOk())
				.andExpect(jsonPath("$.alias", Matchers.is("Captain Marvel")))
				.andExpect(jsonPath("$.name", Matchers.is("Carol Danvers")));
	}

	private List<SuperHeroDto> createSuperHeroes() {
		List<SuperHeroDto> superHeroDtoList = new ArrayList<>();
		//@formatter:off
		superHeroDtoList.add(SuperHeroDto.builder()
				.alias("Captain Marvel")
				.name("Carol Danvers")
				.origin("Exposed to Space Stone reactor overload.")
				.powers(List.of(new String[] { "photon-blast", "flight", "super-strength", "healing" }))
				.associations(List.of(new String[] { "space-stone", "skrulls", "photon", "kree", "avengers" }))
				.build());
		superHeroDtoList.add(SuperHeroDto.builder()
				.alias("Iron Man")
				.name("Tony Stark")
				.origin("Kidnapped in Afghanistan, created the first iron-man suit to escape.")
				.powers(List.of(new String[] { "genius-intelligence", "wealth" }))
				.weapons(List.of(new String[] { "arc-reactor", "iron-man-suit", "iron-legion" }))
				.associations(List.of(new String[] { "war-machine", "avengers", "jarvis", "thanos", "pepper-potts" }))
				.build());
		//@formatter:on
		return superHeroDtoList;
	}

	@Test
	void saveSuperHero() throws Exception {
		String content = "{\"alias\":\"Captain Marvel\",\"name\":\"Carol Danvers\",\"origin\":\"Exposed to Space Stone reactor overload.\"}";
		SuperHeroDto heroDto = SuperHeroDto.builder().alias("Captain Marvel").name("Carol Danvers")
				.origin("Exposed to Space Stone reactor overload.").build();

		mockMvc.perform(post("/v1/superhero/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
		Mockito.verify(superHeroService, Mockito.times(1)).saveSuperHero(heroDto);
	}
	@Test
	void saveSuperHeroNotValid() throws Exception {
		String content = "{\"name\":\"Carol Danvers\",\"origin\":\"Exposed to Space Stone reactor overload.\"}";
		mockMvc.perform(post("/v1/superhero/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isBadRequest());
	}

	@Test
	void getSuperHeroError() throws Exception {
		Mockito.when(superHeroService.getAllsuperHeroes()).thenThrow(new RuntimeException("Very unexpected exception."));
		mockMvc.perform(get("/v1/superhero/"))
				.andExpect(status().isInternalServerError());
	}
}