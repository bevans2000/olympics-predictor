package org.bevans2000.olympics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity	
public class Medal implements Comparable<Medal>{

    @Id
    @GeneratedValue
    private Long id;
    
    @JsonIgnore
	@ManyToOne
	private Event event;
	
	private MedalColour colour;
	
	@JsonIgnore
	@ManyToOne
	private Country winner;

	public Medal(Event event, MedalColour colour, Country winner) {
		super();
		this.event = event;
		this.colour = colour;
		this.winner = winner;
	}
	
	public Medal() {
		//JPA
	}

	public Long getId() {
		return id;
	}

	public Event getEvent() {
		return event;
	}

	@JsonGetter
	public String getSportName() {
		return event.getSport().getName();
	}
	
	@JsonGetter
	public String getEventName() {
		return event.getName();
	}
	
	@JsonGetter
	public Long getEventId() {
		return event.getId();
	}
	
	public MedalColour getColour() {
		return colour;
	}

	public Country getWinner() {
		return winner;
	}

	@JsonGetter
	public String getCountryCode() {
		return winner.getCode();
	}
	
	@Override
	public int compareTo(Medal o) {
		int result = event.compareTo(o.event);
		if (result == 0) {
			result = colour.compareTo(o.colour);
		}
		return result;
	}
	
}
