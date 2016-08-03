package org.bevans2000.olympics.model;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedalRepository extends JpaRepository<Medal, Long>{
	
	Collection<Medal> findByEvent(Event event);
	
	Collection<Medal> findByWinnerCode(String country);	

}