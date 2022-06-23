package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UnidadViaje;
import com.mycompany.myapp.repository.UnidadViajeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UnidadViaje}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UnidadViajeResource {

    private final Logger log = LoggerFactory.getLogger(UnidadViajeResource.class);

    private static final String ENTITY_NAME = "unidadViaje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadViajeRepository unidadViajeRepository;

    public UnidadViajeResource(UnidadViajeRepository unidadViajeRepository) {
        this.unidadViajeRepository = unidadViajeRepository;
    }

    /**
     * {@code POST  /unidad-viajes} : Create a new unidadViaje.
     *
     * @param unidadViaje the unidadViaje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadViaje, or with status {@code 400 (Bad Request)} if the unidadViaje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidad-viajes")
    public ResponseEntity<UnidadViaje> createUnidadViaje(@RequestBody UnidadViaje unidadViaje) throws URISyntaxException {
        log.debug("REST request to save UnidadViaje : {}", unidadViaje);
        if (unidadViaje.getId() != null) {
            throw new BadRequestAlertException("A new unidadViaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadViaje result = unidadViajeRepository.save(unidadViaje);
        return ResponseEntity
            .created(new URI("/api/unidad-viajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidad-viajes/:id} : Updates an existing unidadViaje.
     *
     * @param id the id of the unidadViaje to save.
     * @param unidadViaje the unidadViaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadViaje,
     * or with status {@code 400 (Bad Request)} if the unidadViaje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadViaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidad-viajes/{id}")
    public ResponseEntity<UnidadViaje> updateUnidadViaje(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadViaje unidadViaje
    ) throws URISyntaxException {
        log.debug("REST request to update UnidadViaje : {}, {}", id, unidadViaje);
        if (unidadViaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadViaje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadViajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UnidadViaje result = unidadViajeRepository.save(unidadViaje);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadViaje.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /unidad-viajes/:id} : Partial updates given fields of an existing unidadViaje, field will ignore if it is null
     *
     * @param id the id of the unidadViaje to save.
     * @param unidadViaje the unidadViaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadViaje,
     * or with status {@code 400 (Bad Request)} if the unidadViaje is not valid,
     * or with status {@code 404 (Not Found)} if the unidadViaje is not found,
     * or with status {@code 500 (Internal Server Error)} if the unidadViaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/unidad-viajes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UnidadViaje> partialUpdateUnidadViaje(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UnidadViaje unidadViaje
    ) throws URISyntaxException {
        log.debug("REST request to partial update UnidadViaje partially : {}, {}", id, unidadViaje);
        if (unidadViaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, unidadViaje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!unidadViajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UnidadViaje> result = unidadViajeRepository
            .findById(unidadViaje.getId())
            .map(existingUnidadViaje -> {
                if (unidadViaje.getFecha() != null) {
                    existingUnidadViaje.setFecha(unidadViaje.getFecha());
                }
                if (unidadViaje.getStatus() != null) {
                    existingUnidadViaje.setStatus(unidadViaje.getStatus());
                }
                if (unidadViaje.getCreateByUser() != null) {
                    existingUnidadViaje.setCreateByUser(unidadViaje.getCreateByUser());
                }
                if (unidadViaje.getCreatedOn() != null) {
                    existingUnidadViaje.setCreatedOn(unidadViaje.getCreatedOn());
                }
                if (unidadViaje.getModifyByUser() != null) {
                    existingUnidadViaje.setModifyByUser(unidadViaje.getModifyByUser());
                }
                if (unidadViaje.getModifiedOn() != null) {
                    existingUnidadViaje.setModifiedOn(unidadViaje.getModifiedOn());
                }
                if (unidadViaje.getAuditStatus() != null) {
                    existingUnidadViaje.setAuditStatus(unidadViaje.getAuditStatus());
                }

                return existingUnidadViaje;
            })
            .map(unidadViajeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadViaje.getId().toString())
        );
    }

    /**
     * {@code GET  /unidad-viajes} : get all the unidadViajes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadViajes in body.
     */
    @GetMapping("/unidad-viajes")
    public List<UnidadViaje> getAllUnidadViajes() {
        log.debug("REST request to get all UnidadViajes");
        return unidadViajeRepository.findAll();
    }

    /**
     * {@code GET  /unidad-viajes/:id} : get the "id" unidadViaje.
     *
     * @param id the id of the unidadViaje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadViaje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidad-viajes/{id}")
    public ResponseEntity<UnidadViaje> getUnidadViaje(@PathVariable Long id) {
        log.debug("REST request to get UnidadViaje : {}", id);
        Optional<UnidadViaje> unidadViaje = unidadViajeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidadViaje);
    }

    /**
     * {@code DELETE  /unidad-viajes/:id} : delete the "id" unidadViaje.
     *
     * @param id the id of the unidadViaje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidad-viajes/{id}")
    public ResponseEntity<Void> deleteUnidadViaje(@PathVariable Long id) {
        log.debug("REST request to delete UnidadViaje : {}", id);
        unidadViajeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
