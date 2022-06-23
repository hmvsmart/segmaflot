package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Resurtir;
import com.mycompany.myapp.repository.ResurtirRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Resurtir}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResurtirResource {

    private final Logger log = LoggerFactory.getLogger(ResurtirResource.class);

    private static final String ENTITY_NAME = "resurtir";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResurtirRepository resurtirRepository;

    public ResurtirResource(ResurtirRepository resurtirRepository) {
        this.resurtirRepository = resurtirRepository;
    }

    /**
     * {@code POST  /resurtirs} : Create a new resurtir.
     *
     * @param resurtir the resurtir to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resurtir, or with status {@code 400 (Bad Request)} if the resurtir has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resurtirs")
    public ResponseEntity<Resurtir> createResurtir(@RequestBody Resurtir resurtir) throws URISyntaxException {
        log.debug("REST request to save Resurtir : {}", resurtir);
        if (resurtir.getId() != null) {
            throw new BadRequestAlertException("A new resurtir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resurtir result = resurtirRepository.save(resurtir);
        return ResponseEntity
            .created(new URI("/api/resurtirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resurtirs/:id} : Updates an existing resurtir.
     *
     * @param id the id of the resurtir to save.
     * @param resurtir the resurtir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resurtir,
     * or with status {@code 400 (Bad Request)} if the resurtir is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resurtir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resurtirs/{id}")
    public ResponseEntity<Resurtir> updateResurtir(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Resurtir resurtir
    ) throws URISyntaxException {
        log.debug("REST request to update Resurtir : {}, {}", id, resurtir);
        if (resurtir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resurtir.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resurtirRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Resurtir result = resurtirRepository.save(resurtir);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resurtir.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resurtirs/:id} : Partial updates given fields of an existing resurtir, field will ignore if it is null
     *
     * @param id the id of the resurtir to save.
     * @param resurtir the resurtir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resurtir,
     * or with status {@code 400 (Bad Request)} if the resurtir is not valid,
     * or with status {@code 404 (Not Found)} if the resurtir is not found,
     * or with status {@code 500 (Internal Server Error)} if the resurtir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resurtirs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Resurtir> partialUpdateResurtir(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Resurtir resurtir
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resurtir partially : {}, {}", id, resurtir);
        if (resurtir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resurtir.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resurtirRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Resurtir> result = resurtirRepository
            .findById(resurtir.getId())
            .map(existingResurtir -> {
                if (resurtir.getFecha() != null) {
                    existingResurtir.setFecha(resurtir.getFecha());
                }
                if (resurtir.getTotal() != null) {
                    existingResurtir.setTotal(resurtir.getTotal());
                }
                if (resurtir.getCreateByUser() != null) {
                    existingResurtir.setCreateByUser(resurtir.getCreateByUser());
                }
                if (resurtir.getCreatedOn() != null) {
                    existingResurtir.setCreatedOn(resurtir.getCreatedOn());
                }
                if (resurtir.getModifyByUser() != null) {
                    existingResurtir.setModifyByUser(resurtir.getModifyByUser());
                }
                if (resurtir.getModifiedOn() != null) {
                    existingResurtir.setModifiedOn(resurtir.getModifiedOn());
                }
                if (resurtir.getAuditStatus() != null) {
                    existingResurtir.setAuditStatus(resurtir.getAuditStatus());
                }

                return existingResurtir;
            })
            .map(resurtirRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resurtir.getId().toString())
        );
    }

    /**
     * {@code GET  /resurtirs} : get all the resurtirs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resurtirs in body.
     */
    @GetMapping("/resurtirs")
    public List<Resurtir> getAllResurtirs() {
        log.debug("REST request to get all Resurtirs");
        return resurtirRepository.findAll();
    }

    /**
     * {@code GET  /resurtirs/:id} : get the "id" resurtir.
     *
     * @param id the id of the resurtir to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resurtir, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resurtirs/{id}")
    public ResponseEntity<Resurtir> getResurtir(@PathVariable Long id) {
        log.debug("REST request to get Resurtir : {}", id);
        Optional<Resurtir> resurtir = resurtirRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resurtir);
    }

    /**
     * {@code DELETE  /resurtirs/:id} : delete the "id" resurtir.
     *
     * @param id the id of the resurtir to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resurtirs/{id}")
    public ResponseEntity<Void> deleteResurtir(@PathVariable Long id) {
        log.debug("REST request to delete Resurtir : {}", id);
        resurtirRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
