package org.bevans2000.olympics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bevans2000.olympics.model.Country;
import org.bevans2000.olympics.model.DataLoader;
import org.bevans2000.olympics.model.Event;
import org.bevans2000.olympics.model.Medal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Component
public class PointCalculator {
	private static Log logger = LogFactory.getLog(PointCalculator.class);

	private static class PointRunner implements Runnable {

		private PointCalculator calculator;
		private boolean keepRunning;
		
		public PointRunner(PointCalculator calculator) {
			this.calculator = calculator;
			this.keepRunning = true;
		}

		@Override
		public void run() {
			logger.info("Runner starting......");
			
			// Sleep for a while to allow the system to come up
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				logger.warn("Runner initial wait", e1);
			}
			
			while (keepRunning) {
				try {
					calculator.doNext();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public void stopRunning() {
			keepRunning = false;
		}
		
	}
	
	
	private static final String COUNTER_KEY = "counter.processQ,size";

	// Hold Events to be processed
	private BlockingQueue<Event> events = new LinkedBlockingQueue<>();
	
	// Expose queue size via Spring Actuator
	@Autowired
	private CounterService counterService;

	private PointRunner runner;
	
	private void startRunner() {		
		runner = new PointRunner(this);
		(new Thread(runner)).start();
	}
	
	@PreDestroy
	public void stopRunner() {
		logger.info("Stop runner");
		runner.stopRunning();
	}
	
	/**
	 * Pull the next event off the queue and porcess the points.
	 * @throws InterruptedException 
	 */
	void doNext() throws InterruptedException {
		Event nextEvent = events.take();
		for (Medal medal : nextEvent.getMedals()) {
			Country winner = medal.getWinner();
			
			logger.info("Add " + medal.getPointsValue() + " to " + winner.getCode());
			winner.addPoints(medal.getPointsValue());
		}
	}


	/**
	 * Add the event to the calculator to be processed
	 * @param event
	 */
	public synchronized void addEvent(Event event)  {
		try {
			logger.info("Adding Event " + event + ", size=" + events.size());
			
			events.put(event);
			counterService.increment(COUNTER_KEY);
			
			if (runner == null) {
				startRunner();
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
