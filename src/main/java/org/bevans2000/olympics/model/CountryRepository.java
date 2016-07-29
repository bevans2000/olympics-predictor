package org.bevans2000.olympics.model;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCode(String countryCode);
}
