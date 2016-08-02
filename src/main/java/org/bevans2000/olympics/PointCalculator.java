package org.bevans2000.olympics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.bevans2000.olympics.model.Event;
import org.springframework.stereotype.Component;

@Component
public class PointCalculator {

	BlockingQueue<Event> events = new LinkedBlockingQueue<>();
	
	/**
	 * Add the event to the calcultor to be processed
	 * @param event
	 */
	public void addEvent(Event event) {
		try {
			events.put(event);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
