package com.houssem.poc.service;

import com.houssem.poc.service.dto.CompteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Compte.
 */
public interface CompteService {

    /**
     * Save a compte.
     *
     * @param compteDTO the entity to save
     * @return the persisted entity
     */
    CompteDTO save(CompteDTO compteDTO);

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" compte.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CompteDTO findOne(Long id);

    /**
     * Delete the "id" compte.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
