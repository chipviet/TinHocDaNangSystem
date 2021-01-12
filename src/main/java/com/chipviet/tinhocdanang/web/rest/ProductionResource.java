package com.chipviet.tinhocdanang.web.rest;

import com.chipviet.tinhocdanang.domain.Production;
import com.chipviet.tinhocdanang.repository.ProductionRepository;
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
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chipviet.tinhocdanang.domain.Production}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductionResource {

    private final Logger log = LoggerFactory.getLogger(ProductionResource.class);

    private static final String ENTITY_NAME = "production";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductionRepository productionRepository;

    public ProductionResource(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    /**
     * {@code POST  /productions} : Create a new production.
     *
     * @param production the production to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new production, or with status {@code 400 (Bad Request)} if the production has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @RequestMapping(value="/productions", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE,
        consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Production> createProduction(@Valid @RequestPart("properties") Production production, @RequestPart("files") MultipartFile[] files) throws URISyntaxException, IOException {
        log.debug("REST request to save Production : {}", production);
        if (production.getId() != null) {
            throw new BadRequestAlertException("A new production cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Production latestProduction = productionRepository.getLatestProduction();
        log.debug("latestId : {}", latestProduction);
        String folder = "/Users/sangpham/Documents/ChipViet/MyProjects/TinHocDaNangWeb/src/main/java/com/chipviet/tinhocdanang/assets/" + latestProduction.getId().toString();

        System.out.println("fileImages:" + files.length);
        for(int i = 0; i < files.length; i++){
            System.out.println("file");
        }
        System.out.println("1");
        List<File> uploadedFiles = new ArrayList<File>();
        System.out.println("2");
        Production newProduct = new Production();
        System.out.println("3");
        List<String> listURL = new ArrayList<>();
        System.out.println("4");
        try{
            System.out.println("5");
            for(MultipartFile fileImage : files) {
                System.out.println("Move on loop");
                try {
                    File file = new File(folder);
                    boolean bool = file.mkdirs();
                    if(bool){
                        System.out.println("Directory created successfully");
                    }else{
                        System.out.println("Sorry couldnt create specified directory");
                    }
                    File serverFile = new File(folder + File.separator + fileImage.getOriginalFilename());

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileImage.getBytes());
                    stream.close();

                    uploadedFiles.add(serverFile);

                    listURL.add(serverFile.getAbsolutePath());
                    System.out.println("Write file:" + serverFile);
                } catch (Exception e) {
                    System.out.println("Failure");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("out loop");
 //       newProduct.setImageURL(listURL);
        newProduct.setId(production.getId());
        newProduct.setName(production.getName());
        newProduct.setPrice(production.getPrice());
        newProduct.setDescription(production.getDescription());
        newProduct.setSalePrice(production.getSalePrice());
        newProduct.setConfiguration(production.getConfiguration());
        newProduct.setCondition(production.getCondition());
        newProduct.setOrigin(production.getOrigin());
        newProduct.setCreationDate(production.getCreationDate());
        newProduct.setBrand(production.getBrand());
        newProduct.setCategory(production.getCategory());
        Production result = productionRepository.save(newProduct);
        return ResponseEntity.created(new URI("/api/productions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /productions} : Updates an existing production.
     *
     * @param production the production to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated production,
     * or with status {@code 400 (Bad Request)} if the production is not valid,
     * or with status {@code 500 (Internal Server Error)} if the production couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productions")
    public ResponseEntity<Production> updateProduction(@Valid @RequestBody Production production) throws URISyntaxException {
        log.debug("REST request to update Production : {}", production);
        if (production.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Production result = productionRepository.save(production);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, production.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /productions} : get all the productions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productions in body.
     */
    @GetMapping("/productions")
    public ResponseEntity<List<Production>> getAllProductions(Pageable pageable) {
        log.debug("REST request to get a page of Productions");
        Page<Production> page = productionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /productions/:id} : get the "id" production.
     *
     * @param id the id of the production to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the production, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productions/{id}")
    public ResponseEntity<Production> getProduction(@PathVariable Long id) {
        log.debug("REST request to get Production : {}", id);
        Optional<Production> production = productionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(production);
    }

    /**
     * {@code DELETE  /productions/:id} : delete the "id" production.
     *
     * @param id the id of the production to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productions/{id}")
    public ResponseEntity<Void> deleteProduction(@PathVariable Long id) {
        log.debug("REST request to delete Production : {}", id);
        productionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
