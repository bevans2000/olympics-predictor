package org.bevans2000.olympics.controller;

import java.util.Collection;

import org.bevans2000.olympics.model.Country;
import org.bevans2000.olympics.model.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
class CountryController {

	@Autowired
	private CountryRepository countryRepository;

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	Country readCountry(@PathVariable String code) {
		return this.countryRepository.findByCode(code);
	}

	@RequestMapping(method = RequestMethod.GET)
	Collection<Country> readCountries() {
		return this.countryRepository.findAll();
	}
}
