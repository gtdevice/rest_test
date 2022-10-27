package com.example.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.test.repository.SuperHeroRepository;
import com.example.test.service.SuperHeroService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {
	@Autowired
	SuperHeroService superHeroService;
	@Autowired
	SuperHeroRepository superHeroRepository;
	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		superHeroRepository.deleteAll();
	}

	@Test
	@DisplayName("Save superhero and than get using API.")
	void savingSuperHeroAndGet() throws Exception {
		String content = "{\n" + "    \"alias\": \"Iron Man\",\n" + "    \"name\": \"Tony Stark\",\n" + "   "
				+ " \"powers\": [\n" + "        \"genius-intelligence\",\n" + "        \"wealth\"\n" + "   " + " ],\n"
				+ "    \"weapons\": [\n" + "        \"arc-reactor\",\n" + "        \"iron-man-suit\",\n" + "   "
				+ "     \"iron-legion\"\n" + "    ],\n"
				+ "    \"origin\": \"Kidnapped in Afghanistan, created the first iron-man suit to escape.\",\n" + "  "
				+ "  \"associations\": [\n" + "        \"war-machine\",\n" + "        \"avengers\",\n" + "      "
				+ "  \"jarvis\",\n" + "        \"thanos\",\n" + "        \"pepper-potts\"\n" + "    ]\n" + "}";

		mvc.perform(post("/v1/superhero/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());

		mvc.perform(get("/v1/superhero/")).andExpect(status().isOk()).andExpect(header().string("X-Total-Count", "1"))
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].alias", Matchers.is("Iron Man")))
				.andExpect(jsonPath("$[0].associations", Matchers.hasSize(5)))
				.andExpect(jsonPath("$[0].weapons", Matchers.hasSize(3)))
				.andExpect(jsonPath("$[0].weapons[0]", Matchers.is("arc-reactor")))
				.andExpect(jsonPath("$[0].powers", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].powers[0]", Matchers.is("genius-intelligence")))
				.andExpect(jsonPath("$[0].name", Matchers.is("Tony Stark")));

	}

	@Test
	@DisplayName("Save superhero and get badrequest.")
	void savingSuperHeroDuplicate() throws Exception {
		String content = "{\n" + "    \"alias\": \"Iron Man\",\n" + "    \"name\": \"Tony Stark\",\n" + "   "
				+ " \"powers\": [\n" + "        \"genius-intelligence\",\n" + "        \"wealth\"\n" + "   " + " ],\n"
				+ "    \"weapons\": [\n" + "        \"arc-reactor\",\n" + "        \"iron-man-suit\",\n" + "   "
				+ "     \"iron-legion\"\n" + "    ],\n"
				+ "    \"origin\": \"Kidnapped in Afghanistan, created the first iron-man suit to escape.\",\n" + "  "
				+ "  \"associations\": [\n" + "        \"war-machine\",\n" + "        \"avengers\",\n" + "      "
				+ "  \"jarvis\",\n" + "        \"thanos\",\n" + "        \"pepper-potts\"\n" + "    ]\n" + "}";

		mvc.perform(post("/v1/superhero/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
		mvc.perform(post("/v1/superhero/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Save superhero and get not Found.")
	void savingSuperHeroNotFound() throws Exception {
		String content = "{\n" + "    \"alias\": \"Iron Man\",\n" + "    \"name\": \"Tony Stark\",\n" + "   "
				+ " \"powers\": [\n" + "        \"genius-intelligence\",\n" + "        \"wealth\"\n" + "   " + " ],\n"
				+ "    \"weapons\": [\n" + "        \"arc-reactor\",\n" + "        \"iron-man-suit\",\n" + "   "
				+ "     \"iron-legion\"\n" + "    ],\n"
				+ "    \"origin\": \"Kidnapped in Afghanistan, created the first iron-man suit to escape.\",\n" + "  "
				+ "  \"associations\": [\n" + "        \"war-machine\",\n" + "        \"avengers\",\n" + "      "
				+ "  \"jarvis\",\n" + "        \"thanos\",\n" + "        \"pepper-potts\"\n" + "    ]\n" + "}";

		mvc.perform(post("/v1/superhero/").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
		mvc.perform(get("/v1/superhero/Capitan Marvel").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isNotFound());
	}

}
