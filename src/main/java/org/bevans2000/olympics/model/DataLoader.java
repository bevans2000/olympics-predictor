package org.bevans2000.olympics.model;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bevans2000.olympics.PointCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Loads generated test data
 * @author barryeva
 *
 */
@Component
public class DataLoader {

	private static final String FEMALE_EVENT = " - Women";

	private static final String MALE_EVENT = " - Men";

	private static Log logger = LogFactory.getLog(DataLoader.class);
    
	@Autowired
	private SportRepository sportRepo;
	
	@Autowired
	private EventRepository eventRepo; 
	
	@Autowired
	private CountryRepository countryRepo;
	
	@Autowired
	private PoolRepository poolRepo;
	
	@Autowired
	private MedalRepository medalRepo;
	
	@Autowired
	private PointCalculator calculator;
	
	@PostConstruct
	public void loadData() {

		//Check size of Sports
		if (sportRepo.count() == 0) {
			loadCountries();
			
			loadEvents();
			
			loadMedals();
		}
		else {
			logger.info("Data already present");
		}
	}

	private void loadMedals() {
		createMedal("Football", "Team" + MALE_EVENT, "GBR", "FRA", "CHN");		
	}

	private void createMedal(String sportName, String eventName, String gold, String silver, String bronze) {
		Sport sport = sportRepo.findByName(sportName);
		if (sport == null) {
			throw new IllegalArgumentException("Can not find Sport called " + sportName);
		}
		
		Event event = eventRepo.findBySportAndName(sport, eventName);
		if (event == null) {
			throw new IllegalArgumentException("Can not find Event called " + eventName + " in " + sportName);
		}
		
		Country goldWinner = countryRepo.findByCode(gold);
		medalRepo.save(new Medal(event, MedalColour.GOLD, goldWinner));
		
		Country silverWinner = countryRepo.findByCode(silver);
		medalRepo.save(new Medal(event, MedalColour.GOLD, silverWinner));

		Country bronzeWinner = countryRepo.findByCode(bronze);
		medalRepo.save(new Medal(event, MedalColour.GOLD, bronzeWinner));
		
		calculator.addEvent(event);
	}

	private void loadCountries() {
		InputStreamReader csvSource = new InputStreamReader(getClass().getResourceAsStream("/countries.csv"));
		CSVParser csvFileParser;
		try {
			csvFileParser = new CSVParser(csvSource,
													CSVFormat.RFC4180.withFirstRecordAsHeader());
			
			Map<String, Pool> poolCache = new HashMap<>();
			for (CSVRecord record : csvFileParser) {
				String countryCode = record.get("code");
				String countryName = record.get("name");
				String poolName = record.get("pool");
				
				// Get Pool
				Pool pool = poolCache.get(poolName);
				if (pool == null) {
					logger.debug("Create Pool " + poolName);
					int modifier = Integer.parseInt(poolName);
					pool = poolRepo.save(new Pool("Pool #" + poolName, modifier));
					poolCache.put(poolName, pool);
				}
				
				countryRepo.save(new Country(pool, countryName, countryCode));
			}

			csvSource.close();
		}
		catch (Exception e) {
			logger.warn("Problem with Events csv" + e);
		}
		logger.info("Created " + countryRepo.count() + " Countries across "
		            + poolRepo.count() + " Pools");
		
	}

	private void loadEvents() {
		InputStreamReader csvSource = new InputStreamReader(getClass().getResourceAsStream("/events.csv"));
		CSVParser csvFileParser;
		try {
			csvFileParser = new CSVParser(csvSource,
											CSVFormat.RFC4180.withFirstRecordAsHeader());
			Map<String, Sport> sportsCache = new HashMap<>();
			for (CSVRecord record : csvFileParser) {
				String eventName = record.get("event");
				String sportName = record.get("sport");
				
				// Come back and get this off the CSV file
				Date ceremony = new Date();
				
				// Get Sport
				Sport parentSport = sportsCache.get(sportName);
				if (parentSport == null) {
					logger.debug("Create Sport " + sportName);
					parentSport = sportRepo.save(new Sport(sportName));
					sportsCache.put(sportName, parentSport);
				}
				
				char eventGender = record.get("gender").charAt(0);
				switch (eventGender) {
				case 'M':
					eventRepo.save(new Event(eventName + MALE_EVENT, parentSport, ceremony));
					break;
				case 'F':
					eventRepo.save(new Event(eventName + FEMALE_EVENT, parentSport, ceremony));
					break;
				case 'X':
					eventRepo.save(new Event(eventName, parentSport, ceremony));
					break;
				case 'B':
					eventRepo.save(new Event(eventName + FEMALE_EVENT, parentSport, ceremony));
					eventRepo.save(new Event(eventName + MALE_EVENT, parentSport, ceremony));
					break;					
				default:
					throw new IllegalArgumentException("Unknown Event type '" + eventGender + "'");
				}
			}

			csvSource.close();
		}
		catch (Exception e) {
			logger.warn("Problem with Events csv" + e);
		}
		logger.info("Created " + eventRepo.count() + " Events across "
		            + sportRepo.count() + " Sports");
	}
}
