package org.bevans2000.olympics.controller;

import java.util.Collection;

import org.bevans2000.olympics.model.Country;
import org.bevans2000.olympics.model.Pool;
import org.bevans2000.olympics.model.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pool")
class PoolRestController {

	@Autowired
	private PoolRepository poolRepository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Pool readPool(@PathVariable Long id) {
		return this.poolRepository.findOne(id);
	}

	@RequestMapping(value = "/{id}/countries", method = RequestMethod.GET)
	Collection<Country> readCountries(@PathVariable Long id) {
		Pool found = this.poolRepository.findOne(id);

		return found.getCountries();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<Pool> readPools() {
		return this.poolRepository.findAll();
	}
}

