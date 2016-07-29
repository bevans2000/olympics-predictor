package org.bevans2000.olympics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Country {

    @ManyToOne
    private Pool pool;
	
    @Id
    @GeneratedValue
    private Long id;
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @JsonIgnore
    public String name;
    public String code;

    public Country(Pool pool, String name, String code) {
    	this.pool= pool;
    	this.name = name;
        this.code = code;
    }

    Country() { // jpa only
    }
}