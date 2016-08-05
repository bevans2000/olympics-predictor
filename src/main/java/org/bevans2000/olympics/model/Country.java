package org.bevans2000.olympics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Country {

    @ManyToOne
    private Pool pool;
	
    @Id
    @GeneratedValue
    private Long id;

    public String name;
    
    public int points;
    
    @Column(unique=true, nullable=false)
    public String code;

    public Country(Pool pool, String name, String code) {
    	this.pool= pool;
    	this.name = name;
        this.code = code;
        this.points = 0;
    }

    Country() { // jpa only
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
    
    public Pool getPool() {
    	return pool;
    }

    public int getPoints() {
    	return points;
    }
    
	public void addPoints(int pointIncrease) {
		int points = pointIncrease;
	}
}