package org.bevans2000.olympics.model;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PoolRepository extends JpaRepository<Pool, Long> {
    Pool findByName(String name);
}
