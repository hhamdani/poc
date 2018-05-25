package com.houssem.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.houssem.poc.service.CompteService;
import com.houssem.poc.web.rest.errors.BadRequestAlertException;
import com.houssem.poc.web.rest.util.HeaderUtil;
import com.houssem.poc.web.rest.util.PaginationUtil;
import com.houssem.poc.service.dto.CompteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Compte.
 */
@RestController
@RequestMapping("/api")
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "compte";

    private final CompteService compteService;

    public CompteResource(CompteService compteService) {
        this.compteService = compteService;
    }

    
    @PostMapping("/comptes")
    @Timed
    public ResponseEntity<CompteDTO> createCompte(@Valid @RequestBody CompteDTO compteDTO) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compteDTO);
        if (compteDTO.getId() != null) {
            throw new BadRequestAlertException("A new compte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteDTO result = compteService.save(compteDTO);
        return ResponseEntity.created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

   
    @PutMapping("/comptes")
    @Timed
    public ResponseEntity<CompteDTO> updateCompte(@Valid @RequestBody CompteDTO compteDTO) throws URISyntaxException {
        log.debug("REST request to update Compte : {}", compteDTO);
        if (compteDTO.getId() == null) {
            return createCompte(compteDTO);
        }
        CompteDTO result = compteService.save(compteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteDTO.getId().toString()))
            .body(result);
    }

   
    @GetMapping("/comptes")
    @Timed
    public ResponseEntity<List<CompteDTO>> getAllComptes(Pageable pageable) {
        log.debug("REST request to get a page of Comptes");
        Page<CompteDTO> page = compteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comptes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

   
    @GetMapping("/comptes/{id}")
    @Timed
    public ResponseEntity<CompteDTO> getCompte(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        CompteDTO compteDTO = compteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteDTO));
    }

    
    @DeleteMapping("/comptes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        compteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
