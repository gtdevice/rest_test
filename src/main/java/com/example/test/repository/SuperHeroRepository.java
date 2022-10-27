package com.example.test.repository;

import com.example.test.model.bdmodel.SuperHeroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHeroModel, Long> {
	SuperHeroModel findByAlias(String alias);
}
