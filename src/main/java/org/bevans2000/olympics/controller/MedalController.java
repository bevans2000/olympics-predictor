package org.bevans2000.olympics.controller;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.bevans2000.olympics.PointCalculator;
import org.bevans2000.olympics.model.Country;
import org.bevans2000.olympics.model.CountryRepository;
import org.bevans2000.olympics.model.DataLoader;
import org.bevans2000.olympics.model.Event;
import org.bevans2000.olympics.model.EventRepository;
import org.bevans2000.olympics.model.Medal;
import org.bevans2000.olympics.model.MedalColour;
import org.bevans2000.olympics.model.MedalRepository;
import org.bevans2000.olympics.model.Sport;
import org.bevans2000.olympics.model.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medals")
class MedalController {
	@Autowired
	private SportRepository sportRepo;
	
	@Autowired
	private EventRepository eventRepo; 
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private MedalRepository medalRepo;
	
	@Autowired
	private PointCalculator calculator;
	
	@Autowired
	private DataLoader loader;

	@RequestMapping(value = "/{country}", method = RequestMethod.GET)
	Collection<Medal> readByCountry(@PathVariable String country) {
		return this.medalRepo.findByWinnerCode(country);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Medal> readMedals() {
		return this.medalRepo.findAll();
	}
	
	/**
	 * This load some test data
	 */
	@PostConstruct
	public void loadMedals() {
		//createMedals("Football", "Team" + DataLoader.MALE_EVENT, "GBR", "FRA", "CHN");	
		createMedals(10L, "GBR", "FRA", "CHN");	
		createMedals(20L, "GER", "USA", "CHN");	
		createMedals(30L, "GER", "HUN", "JPN");	
		createMedals(40L, "CHN", "USA", "CAM");	
		createMedals(50L, "SUI", "PRK", "CHN");	

	}

	/**
	 * Create medals via the explicit sport and event name.
	 * @param sportName
	 * @param eventName
	 * @param gold
	 * @param silver
	 * @param bronze
	 */
	private void createMedals(String sportName, String eventName, String gold, String silver, String bronze) {
		Sport sport = sportRepo.findByName(sportName);
		if (sport == null) {
			throw new IllegalArgumentException("Can not find Sport called " + sportName);
		}
		
		Event event = eventRepo.findBySportAndName(sport, eventName);
		if (event == null) {
			throw new IllegalArgumentException("Can not find Event called " + eventName + " in " + sportName);
		}
		
		createMedals(event, gold, silver, bronze);
	}
	
	
	/**
	 * Create medals for an event defined by identifier
	 * @param eventId
	 * @param gold
	 * @param silver
	 * @param bronze
	 */
	private void createMedals(Long eventId, String gold, String silver, String bronze) {
	
		Event event = eventRepo.findOne(eventId);
		if (event == null) {
			throw new IllegalArgumentException("Can not find Event for id " + eventId);
		}
		
		createMedals(event, gold, silver, bronze);
	}

	/**
	 * Create medal for an event.
	 * @param event
	 * @param gold
	 * @param silver
	 * @param bronze
	 */
	private void createMedals(Event event, String gold, String silver, String bronze) {

		Country goldWinner = countryRepo.findByCode(gold);
		medalRepo.save(new Medal(event, MedalColour.GOLD, goldWinner));
		
		Country silverWinner = countryRepo.findByCode(silver);
		medalRepo.save(new Medal(event, MedalColour.SILVER, silverWinner));

		Country bronzeWinner = countryRepo.findByCode(bronze);
		medalRepo.save(new Medal(event, MedalColour.BRONZE, bronzeWinner));
		
		calculator.addEvent(event);
	}

}