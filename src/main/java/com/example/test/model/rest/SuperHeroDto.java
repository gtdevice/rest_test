package com.example.test.model.rest;

import com.example.test.model.bdmodel.SuperHeroModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@JsonPropertyOrder(value = {"alias", "name, powers, weapons, origin, associations"})
public class SuperHeroDto {
	@JsonProperty("alias")
	@NotEmpty
	@NotNull
	private String alias;
	@JsonProperty("name")
	@NotEmpty
	private String name;
	@JsonProperty("powers")
	private List<String> powers;
	@JsonProperty("weapons")
	private List<String> weapons;
	@JsonProperty("origin")
	private String origin;
	@JsonProperty("associations")
	private List<String> associations;

	public SuperHeroDto(SuperHeroModel model) {
		this.alias = model.getAlias();
		this.name=model.getName();
		if (model.getPowers() != null && !model.getPowers().isEmpty()) {
			this.powers = new ArrayList<>(model.getPowers());
		}
		if (model.getWeapons() != null && !model.getWeapons().isEmpty()) {
			this.weapons = new ArrayList<>(model.getWeapons());
		}
		this.origin = model.getOrigin();
		if (model.getAssociations() != null && !model.getAssociations().isEmpty()) {
			this.associations = new ArrayList<>(model.getAssociations());
		}
	}
}
