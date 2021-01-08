package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.TinhocdanangApp;
import com.chipviet.tinhocdanang.domain.Guarantee;
import com.chipviet.tinhocdanang.repository.GuaranteeRepository;

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
 * Integration tests for the {@link GuaranteeResource} REST controller.
 */
@SpringBootTest(classes = TinhocdanangApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GuaranteeResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private GuaranteeRepository guaranteeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuaranteeMockMvc;

    private Guarantee guarantee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guarantee createEntity(EntityManager em) {
        Guarantee guarantee = new Guarantee()
            .description(DEFAULT_DESCRIPTION)
            .creationDate(DEFAULT_CREATION_DATE);
        return guarantee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guarantee createUpdatedEntity(EntityManager em) {
        Guarantee guarantee = new Guarantee()
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE);
        return guarantee;
    }

    @BeforeEach
    public void initTest() {
        guarantee = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuarantee() throws Exception {
        int databaseSizeBeforeCreate = guaranteeRepository.findAll().size();
        // Create the Guarantee
        restGuaranteeMockMvc.perform(post("/api/guarantees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guarantee)))
            .andExpect(status().isCreated());

        // Validate the Guarantee in the database
        List<Guarantee> guaranteeList = guaranteeRepository.findAll();
        assertThat(guaranteeList).hasSize(databaseSizeBeforeCreate + 1);
        Guarantee testGuarantee = guaranteeList.get(guaranteeList.size() - 1);
        assertThat(testGuarantee.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGuarantee.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createGuaranteeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = guaranteeRepository.findAll().size();

        // Create the Guarantee with an existing ID
        guarantee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuaranteeMockMvc.perform(post("/api/guarantees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guarantee)))
            .andExpect(status().isBadRequest());

        // Validate the Guarantee in the database
        List<Guarantee> guaranteeList = guaranteeRepository.findAll();
        assertThat(guaranteeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGuarantees() throws Exception {
        // Initialize the database
        guaranteeRepository.saveAndFlush(guarantee);

        // Get all the guaranteeList
        restGuaranteeMockMvc.perform(get("/api/guarantees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guarantee.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGuarantee() throws Exception {
        // Initialize the database
        guaranteeRepository.saveAndFlush(guarantee);

        // Get the guarantee
        restGuaranteeMockMvc.perform(get("/api/guarantees/{id}", guarantee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guarantee.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGuarantee() throws Exception {
        // Get the guarantee
        restGuaranteeMockMvc.perform(get("/api/guarantees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuarantee() throws Exception {
        // Initialize the database
        guaranteeRepository.saveAndFlush(guarantee);

        int databaseSizeBeforeUpdate = guaranteeRepository.findAll().size();

        // Update the guarantee
        Guarantee updatedGuarantee = guaranteeRepository.findById(guarantee.getId()).get();
        // Disconnect from session so that the updates on updatedGuarantee are not directly saved in db
        em.detach(updatedGuarantee);
        updatedGuarantee
            .description(UPDATED_DESCRIPTION)
            .creationDate(UPDATED_CREATION_DATE);

        restGuaranteeMockMvc.perform(put("/api/guarantees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGuarantee)))
            .andExpect(status().isOk());

        // Validate the Guarantee in the database
        List<Guarantee> guaranteeList = guaranteeRepository.findAll();
        assertThat(guaranteeList).hasSize(databaseSizeBeforeUpdate);
        Guarantee testGuarantee = guaranteeList.get(guaranteeList.size() - 1);
        assertThat(testGuarantee.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGuarantee.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGuarantee() throws Exception {
        int databaseSizeBeforeUpdate = guaranteeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuaranteeMockMvc.perform(put("/api/guarantees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(guarantee)))
            .andExpect(status().isBadRequest());

        // Validate the Guarantee in the database
        List<Guarantee> guaranteeList = guaranteeRepository.findAll();
        assertThat(guaranteeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGuarantee() throws Exception {
        // Initialize the database
        guaranteeRepository.saveAndFlush(guarantee);

        int databaseSizeBeforeDelete = guaranteeRepository.findAll().size();

        // Delete the guarantee
        restGuaranteeMockMvc.perform(delete("/api/guarantees/{id}", guarantee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guarantee> guaranteeList = guaranteeRepository.findAll();
        assertThat(guaranteeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
