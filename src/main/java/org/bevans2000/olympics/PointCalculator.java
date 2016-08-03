package org.bevans2000.olympics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.bevans2000.olympics.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Component
public class PointCalculator {

	private static final String COUNTER_KEY = "counter.processQ,size";

	// Hold Events to be processed
	private BlockingQueue<Event> events = new LinkedBlockingQueue<>();
	
	// Expose queue size via Spring Actuator
	@Autowired
	private CounterService counterService;
	
	/**
	 * Add the event to the calculator to be processed
	 * @param event
	 */
	public void addEvent(Event event) {
		try {
			events.put(event);
			counterService.increment(COUNTER_KEY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
