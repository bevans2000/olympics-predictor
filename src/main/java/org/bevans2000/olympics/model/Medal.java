package org.bevans2000.olympics.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity	
public class Medal {

    @Id
    @GeneratedValue
    private Long id;
    
	@ManyToOne
	private Event event;
	
	private MedalColour colour;
	
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

	public MedalColour getColour() {
		return colour;
	}

	public Country getWinner() {
		return winner;
	}
	
}
