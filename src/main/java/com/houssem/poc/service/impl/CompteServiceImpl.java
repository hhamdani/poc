package com.houssem.poc.service.impl;

import com.houssem.poc.service.CompteService;
import com.houssem.poc.domain.Compte;
import com.houssem.poc.repository.CompteRepository;
import com.houssem.poc.service.dto.CompteDTO;
import com.houssem.poc.service.mapper.CompteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Compte.
 */
@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final Logger log = LoggerFactory.getLogger(CompteServiceImpl.class);

    private final CompteRepository compteRepository;

    private final CompteMapper compteMapper;

    public CompteServiceImpl(CompteRepository compteRepository, CompteMapper compteMapper) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
    }

    /**
     * Save a compte.
     *
     * @param compteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompteDTO save(CompteDTO compteDTO) {
        log.debug("Request to save Compte : {}", compteDTO);
        Compte compte = compteMapper.toEntity(compteDTO);
        compte = compteRepository.save(compte);
        return compteMapper.toDto(compte);
    }

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comptes");
        return compteRepository.findAll(pageable)
            .map(compteMapper::toDto);
    }

    /**
     * Get one compte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompteDTO findOne(Long id) {
        log.debug("Request to get Compte : {}", id);
        Compte compte = compteRepository.findOne(id);
        return compteMapper.toDto(compte);
    }

    /**
     * Delete the compte by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compte : {}", id);
        compteRepository.delete(id);
    }
}
