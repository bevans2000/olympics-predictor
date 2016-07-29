package org.bevans2000.olympics.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Pool {

	@JsonIgnore
    @OneToMany(mappedBy = "pool")
    private Set<Country> countries = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private int modifier;

	public Pool(String name, int modifier) {
		super();
		this.name = name;
		this.modifier = modifier;
	}

	public Pool() {
		// For JPA
	}
	
	public String getName() {
		return name;
	}

	public int getModifier() {
		return modifier;
	}

	public Set<Country> getCountries() {
		return countries;
	}
    
}
