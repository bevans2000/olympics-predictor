package org.bevans2000.olympics.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    
	@JsonIgnore
    @ManyToOne
    private Sport sport;
    
    private String name;
    private Date ceremony;
    
	public Event(String name, Sport sport, Date ceremony) {
		super();
		this.name = name;
		this.sport = sport;
		this.ceremony = ceremony;
	}

	Event() { //JPA only
	}
	
	public Sport getSport() {
		return sport;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getCeremony() {
		return ceremony;
	}
}
