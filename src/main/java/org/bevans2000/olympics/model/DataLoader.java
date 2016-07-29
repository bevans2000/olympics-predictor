package org.bevans2000.olympics.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Loads generated test data
 * @author barryeva
 *
 */
@Component
public class DataLoader {

	private static Log logger = LogFactory.getLog(DataLoader.class);
    
	private static final String [] EVENT_HEADER = {"event","sport","ceremony"};
    
	@Autowired
	private SportRepository sportRepo;
	
	@Autowired
	private EventRepository eventRepo;
	
	@PostConstruct
	public void loadData() {

		//Check size of Sports
		if (sportRepo.count() == 0) {
			//initialize CSVParser object

			
			InputStreamReader csvSource = new InputStreamReader(getClass().getResourceAsStream("events.csv"));
			CSVParser csvFileParser;
			try {
				csvFileParser = new CSVParser(csvSource,
														CSVFormat.DEFAULT.withHeader(EVENT_HEADER));

				Sport lastSport = null;
				for (CSVRecord record : csvFileParser) {
					String eventName = record.get("event");
					String sportName = record.get("sport");
					Date ceremony = new Date();
	
				}
	
				csvSource.close();
			}
			catch (Exception e) {
				logger.warn("Problem with Events csv" + e);
			}
			logger.info("Created " + eventRepo.count() + " Events across "
			            + sportRepo.count() + " Sports");
		}
		else {
			logger.info("Data already present");
		}
	}
}
