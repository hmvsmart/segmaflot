package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Nivel;
import com.mycompany.myapp.repository.NivelRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Nivel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NivelResource {

    private final Logger log = LoggerFactory.getLogger(NivelResource.class);

    private static final String ENTITY_NAME = "nivel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelRepository nivelRepository;

    public NivelResource(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    /**
     * {@code POST  /nivels} : Create a new nivel.
     *
     * @param nivel the nivel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivel, or with status {@code 400 (Bad Request)} if the nivel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivels")
    public ResponseEntity<Nivel> createNivel(@RequestBody Nivel nivel) throws URISyntaxException {
        log.debug("REST request to save Nivel : {}", nivel);
        if (nivel.getId() != null) {
            throw new BadRequestAlertException("A new nivel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nivel result = nivelRepository.save(nivel);
        return ResponseEntity
            .created(new URI("/api/nivels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivels/:id} : Updates an existing nivel.
     *
     * @param id the id of the nivel to save.
     * @param nivel the nivel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivel,
     * or with status {@code 400 (Bad Request)} if the nivel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivels/{id}")
    public ResponseEntity<Nivel> updateNivel(@PathVariable(value = "id", required = false) final Long id, @RequestBody Nivel nivel)
        throws URISyntaxException {
        log.debug("REST request to update Nivel : {}, {}", id, nivel);
        if (nivel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nivel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nivelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Nivel result = nivelRepository.save(nivel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nivels/:id} : Partial updates given fields of an existing nivel, field will ignore if it is null
     *
     * @param id the id of the nivel to save.
     * @param nivel the nivel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivel,
     * or with status {@code 400 (Bad Request)} if the nivel is not valid,
     * or with status {@code 404 (Not Found)} if the nivel is not found,
     * or with status {@code 500 (Internal Server Error)} if the nivel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nivels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nivel> partialUpdateNivel(@PathVariable(value = "id", required = false) final Long id, @RequestBody Nivel nivel)
        throws URISyntaxException {
        log.debug("REST request to partial update Nivel partially : {}, {}", id, nivel);
        if (nivel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nivel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nivelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nivel> result = nivelRepository
            .findById(nivel.getId())
            .map(existingNivel -> {
                if (nivel.getNombre() != null) {
                    existingNivel.setNombre(nivel.getNombre());
                }
                if (nivel.getCreateByUser() != null) {
                    existingNivel.setCreateByUser(nivel.getCreateByUser());
                }
                if (nivel.getCreatedOn() != null) {
                    existingNivel.setCreatedOn(nivel.getCreatedOn());
                }
                if (nivel.getModifyByUser() != null) {
                    existingNivel.setModifyByUser(nivel.getModifyByUser());
                }
                if (nivel.getModifiedOn() != null) {
                    existingNivel.setModifiedOn(nivel.getModifiedOn());
                }
                if (nivel.getAuditStatus() != null) {
                    existingNivel.setAuditStatus(nivel.getAuditStatus());
                }

                return existingNivel;
            })
            .map(nivelRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivel.getId().toString())
        );
    }

    /**
     * {@code GET  /nivels} : get all the nivels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivels in body.
     */
    @GetMapping("/nivels")
    public List<Nivel> getAllNivels() {
        log.debug("REST request to get all Nivels");
        return nivelRepository.findAll();
    }

    /**
     * {@code GET  /nivels/:id} : get the "id" nivel.
     *
     * @param id the id of the nivel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivels/{id}")
    public ResponseEntity<Nivel> getNivel(@PathVariable Long id) {
        log.debug("REST request to get Nivel : {}", id);
        Optional<Nivel> nivel = nivelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nivel);
    }

    /**
     * {@code DELETE  /nivels/:id} : delete the "id" nivel.
     *
     * @param id the id of the nivel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivels/{id}")
    public ResponseEntity<Void> deleteNivel(@PathVariable Long id) {
        log.debug("REST request to delete Nivel : {}", id);
        nivelRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
