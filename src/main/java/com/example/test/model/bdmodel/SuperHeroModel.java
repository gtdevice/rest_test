package com.example.test.model.bdmodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.example.test.model.rest.SuperHeroDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
public class SuperHeroModel {
	@Id
	private String alias;
	private String name;
	@ElementCollection
	private List<String> powers;
	@ElementCollection
	private List<String> weapons;
	private String origin;
	@ElementCollection
	private List<String> associations;

	public SuperHeroModel(SuperHeroDto dto) {
		this.alias = dto.getAlias();
		this.name = dto.getName();
		if (dto.getPowers() != null && !dto.getPowers().isEmpty()) {
			this.powers = new ArrayList<>(dto.getPowers());
		}
		if (dto.getWeapons() != null && !dto.getWeapons().isEmpty()) {
			this.weapons = new ArrayList<>(dto.getWeapons());
		}
		this.origin = dto.getOrigin();
		if (dto.getAssociations() != null && !dto.getAssociations().isEmpty()) {
			this.associations = new ArrayList<>(dto.getAssociations());
		}
	}
}
