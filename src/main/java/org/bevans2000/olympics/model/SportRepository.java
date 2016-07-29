package org.bevans2000.olympics.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long>{
	Sport findByName(String name);
}
