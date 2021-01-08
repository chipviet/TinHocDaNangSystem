package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.TinhocdanangApp;
import com.chipviet.tinhocdanang.domain.PromotionProduction;
import com.chipviet.tinhocdanang.domain.Production;
import com.chipviet.tinhocdanang.domain.Promotion;
import com.chipviet.tinhocdanang.repository.PromotionProductionRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PromotionProductionResource} REST controller.
 */
@SpringBootTest(classes = TinhocdanangApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PromotionProductionResourceIT {

    @Autowired
    private PromotionProductionRepository promotionProductionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPromotionProductionMockMvc;

    private PromotionProduction promotionProduction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PromotionProduction createEntity(EntityManager em) {
        PromotionProduction promotionProduction = new PromotionProduction();
        // Add required entity
        Production production;
        if (TestUtil.findAll(em, Production.class).isEmpty()) {
            production = ProductionResourceIT.createEntity(em);
            em.persist(production);
            em.flush();
        } else {
            production = TestUtil.findAll(em, Production.class).get(0);
        }
        promotionProduction.setProduction(production);
        // Add required entity
        Promotion promotion;
        if (TestUtil.findAll(em, Promotion.class).isEmpty()) {
            promotion = PromotionResourceIT.createEntity(em);
            em.persist(promotion);
            em.flush();
        } else {
            promotion = TestUtil.findAll(em, Promotion.class).get(0);
        }
        promotionProduction.setPromotion(promotion);
        return promotionProduction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PromotionProduction createUpdatedEntity(EntityManager em) {
        PromotionProduction promotionProduction = new PromotionProduction();
        // Add required entity
        Production production;
        if (TestUtil.findAll(em, Production.class).isEmpty()) {
            production = ProductionResourceIT.createUpdatedEntity(em);
            em.persist(production);
            em.flush();
        } else {
            production = TestUtil.findAll(em, Production.class).get(0);
        }
        promotionProduction.setProduction(production);
        // Add required entity
        Promotion promotion;
        if (TestUtil.findAll(em, Promotion.class).isEmpty()) {
            promotion = PromotionResourceIT.createUpdatedEntity(em);
            em.persist(promotion);
            em.flush();
        } else {
            promotion = TestUtil.findAll(em, Promotion.class).get(0);
        }
        promotionProduction.setPromotion(promotion);
        return promotionProduction;
    }

    @BeforeEach
    public void initTest() {
        promotionProduction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromotionProduction() throws Exception {
        int databaseSizeBeforeCreate = promotionProductionRepository.findAll().size();
        // Create the PromotionProduction
        restPromotionProductionMockMvc.perform(post("/api/promotion-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotionProduction)))
            .andExpect(status().isCreated());

        // Validate the PromotionProduction in the database
        List<PromotionProduction> promotionProductionList = promotionProductionRepository.findAll();
        assertThat(promotionProductionList).hasSize(databaseSizeBeforeCreate + 1);
        PromotionProduction testPromotionProduction = promotionProductionList.get(promotionProductionList.size() - 1);
    }

    @Test
    @Transactional
    public void createPromotionProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = promotionProductionRepository.findAll().size();

        // Create the PromotionProduction with an existing ID
        promotionProduction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromotionProductionMockMvc.perform(post("/api/promotion-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotionProduction)))
            .andExpect(status().isBadRequest());

        // Validate the PromotionProduction in the database
        List<PromotionProduction> promotionProductionList = promotionProductionRepository.findAll();
        assertThat(promotionProductionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPromotionProductions() throws Exception {
        // Initialize the database
        promotionProductionRepository.saveAndFlush(promotionProduction);

        // Get all the promotionProductionList
        restPromotionProductionMockMvc.perform(get("/api/promotion-productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotionProduction.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPromotionProduction() throws Exception {
        // Initialize the database
        promotionProductionRepository.saveAndFlush(promotionProduction);

        // Get the promotionProduction
        restPromotionProductionMockMvc.perform(get("/api/promotion-productions/{id}", promotionProduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(promotionProduction.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPromotionProduction() throws Exception {
        // Get the promotionProduction
        restPromotionProductionMockMvc.perform(get("/api/promotion-productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotionProduction() throws Exception {
        // Initialize the database
        promotionProductionRepository.saveAndFlush(promotionProduction);

        int databaseSizeBeforeUpdate = promotionProductionRepository.findAll().size();

        // Update the promotionProduction
        PromotionProduction updatedPromotionProduction = promotionProductionRepository.findById(promotionProduction.getId()).get();
        // Disconnect from session so that the updates on updatedPromotionProduction are not directly saved in db
        em.detach(updatedPromotionProduction);

        restPromotionProductionMockMvc.perform(put("/api/promotion-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPromotionProduction)))
            .andExpect(status().isOk());

        // Validate the PromotionProduction in the database
        List<PromotionProduction> promotionProductionList = promotionProductionRepository.findAll();
        assertThat(promotionProductionList).hasSize(databaseSizeBeforeUpdate);
        PromotionProduction testPromotionProduction = promotionProductionList.get(promotionProductionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPromotionProduction() throws Exception {
        int databaseSizeBeforeUpdate = promotionProductionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromotionProductionMockMvc.perform(put("/api/promotion-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(promotionProduction)))
            .andExpect(status().isBadRequest());

        // Validate the PromotionProduction in the database
        List<PromotionProduction> promotionProductionList = promotionProductionRepository.findAll();
        assertThat(promotionProductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePromotionProduction() throws Exception {
        // Initialize the database
        promotionProductionRepository.saveAndFlush(promotionProduction);

        int databaseSizeBeforeDelete = promotionProductionRepository.findAll().size();

        // Delete the promotionProduction
        restPromotionProductionMockMvc.perform(delete("/api/promotion-productions/{id}", promotionProduction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PromotionProduction> promotionProductionList = promotionProductionRepository.findAll();
        assertThat(promotionProductionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
