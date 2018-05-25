package com.houssem.poc.repository;

import com.houssem.poc.domain.Compte;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

}
