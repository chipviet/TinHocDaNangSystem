package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.domain.PromotionProduction;
import com.chipviet.tinhocdanang.repository.PromotionProductionRepository;
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
 * REST controller for managing {@link com.chipviet.tinhocdanang.domain.PromotionProduction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PromotionProductionResource {

    private final Logger log = LoggerFactory.getLogger(PromotionProductionResource.class);

    private static final String ENTITY_NAME = "promotionProduction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PromotionProductionRepository promotionProductionRepository;

    public PromotionProductionResource(PromotionProductionRepository promotionProductionRepository) {
        this.promotionProductionRepository = promotionProductionRepository;
    }

    /**
     * {@code POST  /promotion-productions} : Create a new promotionProduction.
     *
     * @param promotionProduction the promotionProduction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new promotionProduction, or with status {@code 400 (Bad Request)} if the promotionProduction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/promotion-productions")
    public ResponseEntity<PromotionProduction> createPromotionProduction(@Valid @RequestBody PromotionProduction promotionProduction) throws URISyntaxException {
        log.debug("REST request to save PromotionProduction : {}", promotionProduction);
        if (promotionProduction.getId() != null) {
            throw new BadRequestAlertException("A new promotionProduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PromotionProduction result = promotionProductionRepository.save(promotionProduction);
        return ResponseEntity.created(new URI("/api/promotion-productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /promotion-productions} : Updates an existing promotionProduction.
     *
     * @param promotionProduction the promotionProduction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated promotionProduction,
     * or with status {@code 400 (Bad Request)} if the promotionProduction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the promotionProduction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/promotion-productions")
    public ResponseEntity<PromotionProduction> updatePromotionProduction(@Valid @RequestBody PromotionProduction promotionProduction) throws URISyntaxException {
        log.debug("REST request to update PromotionProduction : {}", promotionProduction);
        if (promotionProduction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PromotionProduction result = promotionProductionRepository.save(promotionProduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, promotionProduction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /promotion-productions} : get all the promotionProductions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of promotionProductions in body.
     */
    @GetMapping("/promotion-productions")
    public ResponseEntity<List<PromotionProduction>> getAllPromotionProductions(Pageable pageable) {
        log.debug("REST request to get a page of PromotionProductions");
        Page<PromotionProduction> page = promotionProductionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /promotion-productions/:id} : get the "id" promotionProduction.
     *
     * @param id the id of the promotionProduction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the promotionProduction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/promotion-productions/{id}")
    public ResponseEntity<PromotionProduction> getPromotionProduction(@PathVariable Long id) {
        log.debug("REST request to get PromotionProduction : {}", id);
        Optional<PromotionProduction> promotionProduction = promotionProductionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(promotionProduction);
    }

    /**
     * {@code DELETE  /promotion-productions/:id} : delete the "id" promotionProduction.
     *
     * @param id the id of the promotionProduction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/promotion-productions/{id}")
    public ResponseEntity<Void> deletePromotionProduction(@PathVariable Long id) {
        log.debug("REST request to delete PromotionProduction : {}", id);
        promotionProductionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
