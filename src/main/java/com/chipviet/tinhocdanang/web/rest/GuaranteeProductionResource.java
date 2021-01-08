package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.domain.GuaranteeProduction;
import com.chipviet.tinhocdanang.repository.GuaranteeProductionRepository;
import com.chipviet.tinhocdanang.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chipviet.tinhocdanang.domain.GuaranteeProduction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GuaranteeProductionResource {

    private final Logger log = LoggerFactory.getLogger(GuaranteeProductionResource.class);

    private static final String ENTITY_NAME = "guaranteeProduction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuaranteeProductionRepository guaranteeProductionRepository;

    public GuaranteeProductionResource(GuaranteeProductionRepository guaranteeProductionRepository) {
        this.guaranteeProductionRepository = guaranteeProductionRepository;
    }

    /**
     * {@code POST  /guarantee-productions} : Create a new guaranteeProduction.
     *
     * @param guaranteeProduction the guaranteeProduction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guaranteeProduction, or with status {@code 400 (Bad Request)} if the guaranteeProduction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/guarantee-productions")
    public ResponseEntity<GuaranteeProduction> createGuaranteeProduction(@Valid @RequestBody GuaranteeProduction guaranteeProduction) throws URISyntaxException {
        log.debug("REST request to save GuaranteeProduction : {}", guaranteeProduction);
        if (guaranteeProduction.getId() != null) {
            throw new BadRequestAlertException("A new guaranteeProduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuaranteeProduction result = guaranteeProductionRepository.save(guaranteeProduction);
        return ResponseEntity.created(new URI("/api/guarantee-productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /guarantee-productions} : Updates an existing guaranteeProduction.
     *
     * @param guaranteeProduction the guaranteeProduction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guaranteeProduction,
     * or with status {@code 400 (Bad Request)} if the guaranteeProduction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guaranteeProduction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/guarantee-productions")
    public ResponseEntity<GuaranteeProduction> updateGuaranteeProduction(@Valid @RequestBody GuaranteeProduction guaranteeProduction) throws URISyntaxException {
        log.debug("REST request to update GuaranteeProduction : {}", guaranteeProduction);
        if (guaranteeProduction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GuaranteeProduction result = guaranteeProductionRepository.save(guaranteeProduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guaranteeProduction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /guarantee-productions} : get all the guaranteeProductions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guaranteeProductions in body.
     */
    @GetMapping("/guarantee-productions")
    public ResponseEntity<List<GuaranteeProduction>> getAllGuaranteeProductions(Pageable pageable) {
        log.debug("REST request to get a page of GuaranteeProductions");
        Page<GuaranteeProduction> page = guaranteeProductionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guarantee-productions/:id} : get the "id" guaranteeProduction.
     *
     * @param id the id of the guaranteeProduction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guaranteeProduction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/guarantee-productions/{id}")
    public ResponseEntity<GuaranteeProduction> getGuaranteeProduction(@PathVariable Long id) {
        log.debug("REST request to get GuaranteeProduction : {}", id);
        Optional<GuaranteeProduction> guaranteeProduction = guaranteeProductionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(guaranteeProduction);
    }

    /**
     * {@code DELETE  /guarantee-productions/:id} : delete the "id" guaranteeProduction.
     *
     * @param id the id of the guaranteeProduction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/guarantee-productions/{id}")
    public ResponseEntity<Void> deleteGuaranteeProduction(@PathVariable Long id) {
        log.debug("REST request to delete GuaranteeProduction : {}", id);
        guaranteeProductionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
