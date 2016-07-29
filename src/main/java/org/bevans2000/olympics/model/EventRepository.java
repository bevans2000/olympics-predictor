package org.bevans2000.olympics.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>{
	Event findByName(String name);
	
	Collection<Event> findByCeremony(Date ceremonyDate);

}
