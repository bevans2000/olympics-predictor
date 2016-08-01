package org.bevans2000.olympics.controller;

import java.util.Collection;

import org.bevans2000.olympics.model.Event;
import org.bevans2000.olympics.model.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
class EventController {

	@Autowired
	private EventRepository eventRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Event readEvent(@PathVariable String userId, @PathVariable Long id) {
		return this.eventRepository.findOne(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Event> readSports() {
		return this.eventRepository.findAll();
	}
}