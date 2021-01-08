package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.TinhocdanangApp;
import com.chipviet.tinhocdanang.domain.CartProduction;
import com.chipviet.tinhocdanang.domain.Cart;
import com.chipviet.tinhocdanang.domain.Production;
import com.chipviet.tinhocdanang.repository.CartProductionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CartProductionResource} REST controller.
 */
@SpringBootTest(classes = TinhocdanangApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CartProductionResourceIT {

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_QUANLITY = 1L;
    private static final Long UPDATED_QUANLITY = 2L;

    @Autowired
    private CartProductionRepository cartProductionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartProductionMockMvc;

    private CartProduction cartProduction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartProduction createEntity(EntityManager em) {
        CartProduction cartProduction = new CartProduction()
            .creationDate(DEFAULT_CREATION_DATE)
            .quanlity(DEFAULT_QUANLITY);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        cartProduction.setCart(cart);
        // Add required entity
        Production production;
        if (TestUtil.findAll(em, Production.class).isEmpty()) {
            production = ProductionResourceIT.createEntity(em);
            em.persist(production);
            em.flush();
        } else {
            production = TestUtil.findAll(em, Production.class).get(0);
        }
        cartProduction.setProdution(production);
        return cartProduction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartProduction createUpdatedEntity(EntityManager em) {
        CartProduction cartProduction = new CartProduction()
            .creationDate(UPDATED_CREATION_DATE)
            .quanlity(UPDATED_QUANLITY);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createUpdatedEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        cartProduction.setCart(cart);
        // Add required entity
        Production production;
        if (TestUtil.findAll(em, Production.class).isEmpty()) {
            production = ProductionResourceIT.createUpdatedEntity(em);
            em.persist(production);
            em.flush();
        } else {
            production = TestUtil.findAll(em, Production.class).get(0);
        }
        cartProduction.setProdution(production);
        return cartProduction;
    }

    @BeforeEach
    public void initTest() {
        cartProduction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartProduction() throws Exception {
        int databaseSizeBeforeCreate = cartProductionRepository.findAll().size();
        // Create the CartProduction
        restCartProductionMockMvc.perform(post("/api/cart-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartProduction)))
            .andExpect(status().isCreated());

        // Validate the CartProduction in the database
        List<CartProduction> cartProductionList = cartProductionRepository.findAll();
        assertThat(cartProductionList).hasSize(databaseSizeBeforeCreate + 1);
        CartProduction testCartProduction = cartProductionList.get(cartProductionList.size() - 1);
        assertThat(testCartProduction.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCartProduction.getQuanlity()).isEqualTo(DEFAULT_QUANLITY);
    }

    @Test
    @Transactional
    public void createCartProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartProductionRepository.findAll().size();

        // Create the CartProduction with an existing ID
        cartProduction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartProductionMockMvc.perform(post("/api/cart-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartProduction)))
            .andExpect(status().isBadRequest());

        // Validate the CartProduction in the database
        List<CartProduction> cartProductionList = cartProductionRepository.findAll();
        assertThat(cartProductionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCartProductions() throws Exception {
        // Initialize the database
        cartProductionRepository.saveAndFlush(cartProduction);

        // Get all the cartProductionList
        restCartProductionMockMvc.perform(get("/api/cart-productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartProduction.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].quanlity").value(hasItem(DEFAULT_QUANLITY.intValue())));
    }
    
    @Test
    @Transactional
    public void getCartProduction() throws Exception {
        // Initialize the database
        cartProductionRepository.saveAndFlush(cartProduction);

        // Get the cartProduction
        restCartProductionMockMvc.perform(get("/api/cart-productions/{id}", cartProduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cartProduction.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.quanlity").value(DEFAULT_QUANLITY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCartProduction() throws Exception {
        // Get the cartProduction
        restCartProductionMockMvc.perform(get("/api/cart-productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartProduction() throws Exception {
        // Initialize the database
        cartProductionRepository.saveAndFlush(cartProduction);

        int databaseSizeBeforeUpdate = cartProductionRepository.findAll().size();

        // Update the cartProduction
        CartProduction updatedCartProduction = cartProductionRepository.findById(cartProduction.getId()).get();
        // Disconnect from session so that the updates on updatedCartProduction are not directly saved in db
        em.detach(updatedCartProduction);
        updatedCartProduction
            .creationDate(UPDATED_CREATION_DATE)
            .quanlity(UPDATED_QUANLITY);

        restCartProductionMockMvc.perform(put("/api/cart-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartProduction)))
            .andExpect(status().isOk());

        // Validate the CartProduction in the database
        List<CartProduction> cartProductionList = cartProductionRepository.findAll();
        assertThat(cartProductionList).hasSize(databaseSizeBeforeUpdate);
        CartProduction testCartProduction = cartProductionList.get(cartProductionList.size() - 1);
        assertThat(testCartProduction.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCartProduction.getQuanlity()).isEqualTo(UPDATED_QUANLITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCartProduction() throws Exception {
        int databaseSizeBeforeUpdate = cartProductionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartProductionMockMvc.perform(put("/api/cart-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartProduction)))
            .andExpect(status().isBadRequest());

        // Validate the CartProduction in the database
        List<CartProduction> cartProductionList = cartProductionRepository.findAll();
        assertThat(cartProductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCartProduction() throws Exception {
        // Initialize the database
        cartProductionRepository.saveAndFlush(cartProduction);

        int databaseSizeBeforeDelete = cartProductionRepository.findAll().size();

        // Delete the cartProduction
        restCartProductionMockMvc.perform(delete("/api/cart-productions/{id}", cartProduction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CartProduction> cartProductionList = cartProductionRepository.findAll();
        assertThat(cartProductionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
