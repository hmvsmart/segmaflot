package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CP;
import com.mycompany.myapp.repository.CPRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CP}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CPResource {

    private final Logger log = LoggerFactory.getLogger(CPResource.class);

    private static final String ENTITY_NAME = "cP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPRepository cPRepository;

    public CPResource(CPRepository cPRepository) {
        this.cPRepository = cPRepository;
    }

    /**
     * {@code POST  /cps} : Create a new cP.
     *
     * @param cP the cP to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cP, or with status {@code 400 (Bad Request)} if the cP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cps")
    public ResponseEntity<CP> createCP(@RequestBody CP cP) throws URISyntaxException {
        log.debug("REST request to save CP : {}", cP);
        if (cP.getId() != null) {
            throw new BadRequestAlertException("A new cP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CP result = cPRepository.save(cP);
        return ResponseEntity
            .created(new URI("/api/cps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cps/:id} : Updates an existing cP.
     *
     * @param id the id of the cP to save.
     * @param cP the cP to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cP,
     * or with status {@code 400 (Bad Request)} if the cP is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cP couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cps/{id}")
    public ResponseEntity<CP> updateCP(@PathVariable(value = "id", required = false) final Long id, @RequestBody CP cP)
        throws URISyntaxException {
        log.debug("REST request to update CP : {}, {}", id, cP);
        if (cP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cP.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CP result = cPRepository.save(cP);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cP.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cps/:id} : Partial updates given fields of an existing cP, field will ignore if it is null
     *
     * @param id the id of the cP to save.
     * @param cP the cP to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cP,
     * or with status {@code 400 (Bad Request)} if the cP is not valid,
     * or with status {@code 404 (Not Found)} if the cP is not found,
     * or with status {@code 500 (Internal Server Error)} if the cP couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CP> partialUpdateCP(@PathVariable(value = "id", required = false) final Long id, @RequestBody CP cP)
        throws URISyntaxException {
        log.debug("REST request to partial update CP partially : {}, {}", id, cP);
        if (cP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cP.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CP> result = cPRepository
            .findById(cP.getId())
            .map(existingCP -> {
                if (cP.getCp() != null) {
                    existingCP.setCp(cP.getCp());
                }

                return existingCP;
            })
            .map(cPRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cP.getId().toString())
        );
    }

    /**
     * {@code GET  /cps} : get all the cPS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPS in body.
     */
    @GetMapping("/cps")
    public List<CP> getAllCPS() {
        log.debug("REST request to get all CPS");
        return cPRepository.findAll();
    }

    /**
     * {@code GET  /cps/:id} : get the "id" cP.
     *
     * @param id the id of the cP to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cP, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cps/{id}")
    public ResponseEntity<CP> getCP(@PathVariable Long id) {
        log.debug("REST request to get CP : {}", id);
        Optional<CP> cP = cPRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cP);
    }

    /**
     * {@code DELETE  /cps/:id} : delete the "id" cP.
     *
     * @param id the id of the cP to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cps/{id}")
    public ResponseEntity<Void> deleteCP(@PathVariable Long id) {
        log.debug("REST request to delete CP : {}", id);
        cPRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
