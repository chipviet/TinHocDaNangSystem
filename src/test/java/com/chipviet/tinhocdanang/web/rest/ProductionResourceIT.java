package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.TinhocdanangApp;
import com.chipviet.tinhocdanang.domain.Production;
import com.chipviet.tinhocdanang.domain.Brand;
import com.chipviet.tinhocdanang.domain.Category;
import com.chipviet.tinhocdanang.repository.ProductionRepository;

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
 * Integration tests for the {@link ProductionResource} REST controller.
 */
@SpringBootTest(classes = TinhocdanangApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_SALE_PRICE = 1L;
    private static final Long UPDATED_SALE_PRICE = 2L;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Double DEFAULT_CONDITION = 1D;
    private static final Double UPDATED_CONDITION = 2D;

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductionMockMvc;

    private Production production;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Production createEntity(EntityManager em) {
        Production production = new Production()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .imageURL(DEFAULT_IMAGE_URL)
            .salePrice(DEFAULT_SALE_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .condition(DEFAULT_CONDITION)
            .origin(DEFAULT_ORIGIN)
            .configuration(DEFAULT_CONFIGURATION)
            .creationDate(DEFAULT_CREATION_DATE);
        // Add required entity
        Brand brand;
        if (TestUtil.findAll(em, Brand.class).isEmpty()) {
            brand = BrandResourceIT.createEntity(em);
            em.persist(brand);
            em.flush();
        } else {
            brand = TestUtil.findAll(em, Brand.class).get(0);
        }
        production.setBrand(brand);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        production.setCategory(category);
        return production;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Production createUpdatedEntity(EntityManager em) {
        Production production = new Production()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .imageURL(UPDATED_IMAGE_URL)
            .salePrice(UPDATED_SALE_PRICE)
            .quantity(UPDATED_QUANTITY)
            .condition(UPDATED_CONDITION)
            .origin(UPDATED_ORIGIN)
            .configuration(UPDATED_CONFIGURATION)
            .creationDate(UPDATED_CREATION_DATE);
        // Add required entity
        Brand brand;
        if (TestUtil.findAll(em, Brand.class).isEmpty()) {
            brand = BrandResourceIT.createUpdatedEntity(em);
            em.persist(brand);
            em.flush();
        } else {
            brand = TestUtil.findAll(em, Brand.class).get(0);
        }
        production.setBrand(brand);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        production.setCategory(category);
        return production;
    }

    @BeforeEach
    public void initTest() {
        production = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduction() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();
        // Create the Production
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isCreated());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate + 1);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduction.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduction.getImageURL()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProduction.getSalePrice()).isEqualTo(DEFAULT_SALE_PRICE);
        assertThat(testProduction.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduction.getCondition()).isEqualTo(DEFAULT_CONDITION);
        assertThat(testProduction.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testProduction.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);
        assertThat(testProduction.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production with an existing ID
        production.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isBadRequest());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionRepository.findAll().size();
        // set the field null
        production.setName(null);

        // Create the Production, which fails.


        restProductionMockMvc.perform(post("/api/productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isBadRequest());

        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionRepository.findAll().size();
        // set the field null
        production.setPrice(null);

        // Create the Production, which fails.


        restProductionMockMvc.perform(post("/api/productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isBadRequest());

        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductions() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList
        restProductionMockMvc.perform(get("/api/productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageURL").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].condition").value(hasItem(DEFAULT_CONDITION.doubleValue())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(production.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageURL").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.salePrice").value(DEFAULT_SALE_PRICE.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.condition").value(DEFAULT_CONDITION.doubleValue()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.configuration").value(DEFAULT_CONFIGURATION))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProduction() throws Exception {
        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Update the production
        Production updatedProduction = productionRepository.findById(production.getId()).get();
        // Disconnect from session so that the updates on updatedProduction are not directly saved in db
        em.detach(updatedProduction);
        updatedProduction
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .imageURL(UPDATED_IMAGE_URL)
            .salePrice(UPDATED_SALE_PRICE)
            .quantity(UPDATED_QUANTITY)
            .condition(UPDATED_CONDITION)
            .origin(UPDATED_ORIGIN)
            .configuration(UPDATED_CONFIGURATION)
            .creationDate(UPDATED_CREATION_DATE);

        restProductionMockMvc.perform(put("/api/productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduction)))
            .andExpect(status().isOk());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduction.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduction.getImageURL()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduction.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
        assertThat(testProduction.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduction.getCondition()).isEqualTo(UPDATED_CONDITION);
        assertThat(testProduction.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testProduction.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);
        assertThat(testProduction.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduction() throws Exception {
        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionMockMvc.perform(put("/api/productions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(production)))
            .andExpect(status().isBadRequest());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        int databaseSizeBeforeDelete = productionRepository.findAll().size();

        // Delete the production
        restProductionMockMvc.perform(delete("/api/productions/{id}", production.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
