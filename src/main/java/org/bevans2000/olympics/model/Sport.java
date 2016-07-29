package org.bevans2000.olympics.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sport {

    @OneToMany(mappedBy = "account")
    private Set<Event> events = new HashSet<>();
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    public Sport(String name) {
		super();
		this.name = name;
	}

	Sport() { //JPA
    }
    
    public Set<Event> getEvents() {
        return events;
    }

    public Long getId() {
        return id;
    }

	public String getName() {
		return name;
	}
}
