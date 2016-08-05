package org.bevans2000.olympics.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event implements Comparable<Event>{
    @Id
    @GeneratedValue
    private Long id;
    
	@JsonIgnore
    @ManyToOne
    private Sport sport;
	
	@JsonIgnore
    @OneToMany(mappedBy = "event")
    private Set<Medal> medals = new HashSet<>();
    
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

	public Set<Medal> getMedals() {
		return medals;
	}
	
	@Override
	public int compareTo(Event o) {
		return name.compareTo(o.name);
	}
}
