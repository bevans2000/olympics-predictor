package org.bevans2000.olympics.controller;

import java.util.Collection;

import org.bevans2000.olympics.model.Sport;
import org.bevans2000.olympics.model.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sports")
class SportController {

	@Autowired
	private SportRepository sportRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Sport readBookmark(@PathVariable String userId, @PathVariable Long id) {
		return this.sportRepository.findOne(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Sport> readSports() {
		return this.sportRepository.findAll();
	}
}