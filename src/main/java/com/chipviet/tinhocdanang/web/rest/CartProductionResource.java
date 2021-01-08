package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.domain.CartProduction;
import com.chipviet.tinhocdanang.repository.CartProductionRepository;
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
 * REST controller for managing {@link com.chipviet.tinhocdanang.domain.CartProduction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CartProductionResource {

    private final Logger log = LoggerFactory.getLogger(CartProductionResource.class);

    private static final String ENTITY_NAME = "cartProduction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartProductionRepository cartProductionRepository;

    public CartProductionResource(CartProductionRepository cartProductionRepository) {
        this.cartProductionRepository = cartProductionRepository;
    }

    /**
     * {@code POST  /cart-productions} : Create a new cartProduction.
     *
     * @param cartProduction the cartProduction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartProduction, or with status {@code 400 (Bad Request)} if the cartProduction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cart-productions")
    public ResponseEntity<CartProduction> createCartProduction(@Valid @RequestBody CartProduction cartProduction) throws URISyntaxException {
        log.debug("REST request to save CartProduction : {}", cartProduction);
        if (cartProduction.getId() != null) {
            throw new BadRequestAlertException("A new cartProduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartProduction result = cartProductionRepository.save(cartProduction);
        return ResponseEntity.created(new URI("/api/cart-productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cart-productions} : Updates an existing cartProduction.
     *
     * @param cartProduction the cartProduction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartProduction,
     * or with status {@code 400 (Bad Request)} if the cartProduction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartProduction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cart-productions")
    public ResponseEntity<CartProduction> updateCartProduction(@Valid @RequestBody CartProduction cartProduction) throws URISyntaxException {
        log.debug("REST request to update CartProduction : {}", cartProduction);
        if (cartProduction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartProduction result = cartProductionRepository.save(cartProduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartProduction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cart-productions} : get all the cartProductions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartProductions in body.
     */
    @GetMapping("/cart-productions")
    public ResponseEntity<List<CartProduction>> getAllCartProductions(Pageable pageable) {
        log.debug("REST request to get a page of CartProductions");
        Page<CartProduction> page = cartProductionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cart-productions/:id} : get the "id" cartProduction.
     *
     * @param id the id of the cartProduction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartProduction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cart-productions/{id}")
    public ResponseEntity<CartProduction> getCartProduction(@PathVariable Long id) {
        log.debug("REST request to get CartProduction : {}", id);
        Optional<CartProduction> cartProduction = cartProductionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cartProduction);
    }

    /**
     * {@code DELETE  /cart-productions/:id} : delete the "id" cartProduction.
     *
     * @param id the id of the cartProduction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cart-productions/{id}")
    public ResponseEntity<Void> deleteCartProduction(@PathVariable Long id) {
        log.debug("REST request to delete CartProduction : {}", id);
        cartProductionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
