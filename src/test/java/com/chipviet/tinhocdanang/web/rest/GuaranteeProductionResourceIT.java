package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.TinhocdanangApp;
import com.chipviet.tinhocdanang.domain.GuaranteeProduction;
import com.chipviet.tinhocdanang.domain.Production;
import com.chipviet.tinhocdanang.domain.Guarantee;
import com.chipviet.tinhocdanang.repository.GuaranteeProductionRepository;

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
 * Integration tests for the {@link GuaranteeProductionResource} REST controller.
 */
@SpringBootTest(classes = TinhocdanangApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GuaranteeProductionResourceIT {

    @Autowired
    private GuaranteeProductionRepository guaranteeProductionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuaranteeProductionMockMvc;

    private GuaranteeProduction guaranteeProduction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuaranteeProduction createEntity(EntityManager em) {
        GuaranteeProduction guaranteeProduction = new GuaranteeProduction();
        // Add required entity
        Production production;
        if (TestUtil.findAll(em, Production.class).isEmpty()) {
            production = ProductionResourceIT.createEntity(em);
            em.persist(production);
            em.flush();
        } else {
            production = TestUtil.findAll(em, Production.class).get(0);
        }
        guaranteeProduction.setProduction(production);
        // Add required entity
        Guarantee guarantee;
        if (TestUtil.findAll(em, Guarantee.class).isEmpty()) {
            guarantee = GuaranteeResourceIT.createEntity(em);
            em.persist(guarantee);
            em.flush();
        } else {
            guarantee = TestUtil.findAll(em, Guarantee.class).get(0);
        }
        guaranteeProduction.setGuarantee(guarantee);
        return guaranteeProduction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuaranteeProduction createUpdatedEntity(EntityManager em) {
        GuaranteeProduction guaranteeProduction = new GuaranteeProduction();
        // Add required entity
        Production production;
        if (TestUtil.findAll(em, Production.class).isEmpty()) {
            production = ProductionResourceIT.createUpdatedEntity(em);
            em.persist(production);
            em.flush();
        } else {
            production = TestUtil.findAll(em, Production.class).get(0);
        }
        guaranteeProduction.setProduction(production);
        // Add required entity
        Guarantee guarantee;
        if (TestUtil.findAll(em, Guarantee.class).isEmpty()) {
            guarantee = GuaranteeResourceIT.createUpdatedEntity(em);
            em.persist(guarantee);
            em.flush();
        } else {
            guarantee = TestUtil.findAll(em, Guarantee.class).get(0);
        }
        guaranteeProduction.setGuarantee(guarantee);
        return guaranteeProduction;
    }

    @BeforeEach
    public void initTest() {
        guaranteeProduction = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuaranteeProduction() throws Exception {
        int databaseSizeBeforeCreate = guaranteeProductionRepository.findAll().size();
        // Create the GuaranteeProduction
        restGuaranteeProductionMockMvc.perform(post("/api/guarantee-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guaranteeProduction)))
            .andExpect(status().isCreated());

        // Validate the GuaranteeProduction in the database
        List<GuaranteeProduction> guaranteeProductionList = guaranteeProductionRepository.findAll();
        assertThat(guaranteeProductionList).hasSize(databaseSizeBeforeCreate + 1);
        GuaranteeProduction testGuaranteeProduction = guaranteeProductionList.get(guaranteeProductionList.size() - 1);
    }

    @Test
    @Transactional
    public void createGuaranteeProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guaranteeProductionRepository.findAll().size();

        // Create the GuaranteeProduction with an existing ID
        guaranteeProduction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuaranteeProductionMockMvc.perform(post("/api/guarantee-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guaranteeProduction)))
            .andExpect(status().isBadRequest());

        // Validate the GuaranteeProduction in the database
        List<GuaranteeProduction> guaranteeProductionList = guaranteeProductionRepository.findAll();
        assertThat(guaranteeProductionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGuaranteeProductions() throws Exception {
        // Initialize the database
        guaranteeProductionRepository.saveAndFlush(guaranteeProduction);

        // Get all the guaranteeProductionList
        restGuaranteeProductionMockMvc.perform(get("/api/guarantee-productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guaranteeProduction.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getGuaranteeProduction() throws Exception {
        // Initialize the database
        guaranteeProductionRepository.saveAndFlush(guaranteeProduction);

        // Get the guaranteeProduction
        restGuaranteeProductionMockMvc.perform(get("/api/guarantee-productions/{id}", guaranteeProduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guaranteeProduction.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGuaranteeProduction() throws Exception {
        // Get the guaranteeProduction
        restGuaranteeProductionMockMvc.perform(get("/api/guarantee-productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuaranteeProduction() throws Exception {
        // Initialize the database
        guaranteeProductionRepository.saveAndFlush(guaranteeProduction);

        int databaseSizeBeforeUpdate = guaranteeProductionRepository.findAll().size();

        // Update the guaranteeProduction
        GuaranteeProduction updatedGuaranteeProduction = guaranteeProductionRepository.findById(guaranteeProduction.getId()).get();
        // Disconnect from session so that the updates on updatedGuaranteeProduction are not directly saved in db
        em.detach(updatedGuaranteeProduction);

        restGuaranteeProductionMockMvc.perform(put("/api/guarantee-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGuaranteeProduction)))
            .andExpect(status().isOk());

        // Validate the GuaranteeProduction in the database
        List<GuaranteeProduction> guaranteeProductionList = guaranteeProductionRepository.findAll();
        assertThat(guaranteeProductionList).hasSize(databaseSizeBeforeUpdate);
        GuaranteeProduction testGuaranteeProduction = guaranteeProductionList.get(guaranteeProductionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGuaranteeProduction() throws Exception {
        int databaseSizeBeforeUpdate = guaranteeProductionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuaranteeProductionMockMvc.perform(put("/api/guarantee-productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guaranteeProduction)))
            .andExpect(status().isBadRequest());

        // Validate the GuaranteeProduction in the database
        List<GuaranteeProduction> guaranteeProductionList = guaranteeProductionRepository.findAll();
        assertThat(guaranteeProductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGuaranteeProduction() throws Exception {
        // Initialize the database
        guaranteeProductionRepository.saveAndFlush(guaranteeProduction);

        int databaseSizeBeforeDelete = guaranteeProductionRepository.findAll().size();

        // Delete the guaranteeProduction
        restGuaranteeProductionMockMvc.perform(delete("/api/guarantee-productions/{id}", guaranteeProduction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuaranteeProduction> guaranteeProductionList = guaranteeProductionRepository.findAll();
        assertThat(guaranteeProductionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
