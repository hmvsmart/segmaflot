package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DireccionPersona;
import com.mycompany.myapp.repository.DireccionPersonaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DireccionPersona}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DireccionPersonaResource {

    private final Logger log = LoggerFactory.getLogger(DireccionPersonaResource.class);

    private static final String ENTITY_NAME = "direccionPersona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DireccionPersonaRepository direccionPersonaRepository;

    public DireccionPersonaResource(DireccionPersonaRepository direccionPersonaRepository) {
        this.direccionPersonaRepository = direccionPersonaRepository;
    }

    /**
     * {@code POST  /direccion-personas} : Create a new direccionPersona.
     *
     * @param direccionPersona the direccionPersona to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new direccionPersona, or with status {@code 400 (Bad Request)} if the direccionPersona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/direccion-personas")
    public ResponseEntity<DireccionPersona> createDireccionPersona(@RequestBody DireccionPersona direccionPersona)
        throws URISyntaxException {
        log.debug("REST request to save DireccionPersona : {}", direccionPersona);
        if (direccionPersona.getId() != null) {
            throw new BadRequestAlertException("A new direccionPersona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DireccionPersona result = direccionPersonaRepository.save(direccionPersona);
        return ResponseEntity
            .created(new URI("/api/direccion-personas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /direccion-personas/:id} : Updates an existing direccionPersona.
     *
     * @param id the id of the direccionPersona to save.
     * @param direccionPersona the direccionPersona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated direccionPersona,
     * or with status {@code 400 (Bad Request)} if the direccionPersona is not valid,
     * or with status {@code 500 (Internal Server Error)} if the direccionPersona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/direccion-personas/{id}")
    public ResponseEntity<DireccionPersona> updateDireccionPersona(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DireccionPersona direccionPersona
    ) throws URISyntaxException {
        log.debug("REST request to update DireccionPersona : {}, {}", id, direccionPersona);
        if (direccionPersona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, direccionPersona.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!direccionPersonaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DireccionPersona result = direccionPersonaRepository.save(direccionPersona);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, direccionPersona.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /direccion-personas/:id} : Partial updates given fields of an existing direccionPersona, field will ignore if it is null
     *
     * @param id the id of the direccionPersona to save.
     * @param direccionPersona the direccionPersona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated direccionPersona,
     * or with status {@code 400 (Bad Request)} if the direccionPersona is not valid,
     * or with status {@code 404 (Not Found)} if the direccionPersona is not found,
     * or with status {@code 500 (Internal Server Error)} if the direccionPersona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/direccion-personas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DireccionPersona> partialUpdateDireccionPersona(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DireccionPersona direccionPersona
    ) throws URISyntaxException {
        log.debug("REST request to partial update DireccionPersona partially : {}, {}", id, direccionPersona);
        if (direccionPersona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, direccionPersona.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!direccionPersonaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DireccionPersona> result = direccionPersonaRepository
            .findById(direccionPersona.getId())
            .map(existingDireccionPersona -> {
                if (direccionPersona.getFecha() != null) {
                    existingDireccionPersona.setFecha(direccionPersona.getFecha());
                }
                if (direccionPersona.getStatus() != null) {
                    existingDireccionPersona.setStatus(direccionPersona.getStatus());
                }
                if (direccionPersona.getCreateByUser() != null) {
                    existingDireccionPersona.setCreateByUser(direccionPersona.getCreateByUser());
                }
                if (direccionPersona.getCreatedOn() != null) {
                    existingDireccionPersona.setCreatedOn(direccionPersona.getCreatedOn());
                }
                if (direccionPersona.getModifyByUser() != null) {
                    existingDireccionPersona.setModifyByUser(direccionPersona.getModifyByUser());
                }
                if (direccionPersona.getModifiedOn() != null) {
                    existingDireccionPersona.setModifiedOn(direccionPersona.getModifiedOn());
                }
                if (direccionPersona.getAuditStatus() != null) {
                    existingDireccionPersona.setAuditStatus(direccionPersona.getAuditStatus());
                }

                return existingDireccionPersona;
            })
            .map(direccionPersonaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, direccionPersona.getId().toString())
        );
    }

    /**
     * {@code GET  /direccion-personas} : get all the direccionPersonas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of direccionPersonas in body.
     */
    @GetMapping("/direccion-personas")
    public List<DireccionPersona> getAllDireccionPersonas() {
        log.debug("REST request to get all DireccionPersonas");
        return direccionPersonaRepository.findAll();
    }

    /**
     * {@code GET  /direccion-personas/:id} : get the "id" direccionPersona.
     *
     * @param id the id of the direccionPersona to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the direccionPersona, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/direccion-personas/{id}")
    public ResponseEntity<DireccionPersona> getDireccionPersona(@PathVariable Long id) {
        log.debug("REST request to get DireccionPersona : {}", id);
        Optional<DireccionPersona> direccionPersona = direccionPersonaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(direccionPersona);
    }

    /**
     * {@code DELETE  /direccion-personas/:id} : delete the "id" direccionPersona.
     *
     * @param id the id of the direccionPersona to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/direccion-personas/{id}")
    public ResponseEntity<Void> deleteDireccionPersona(@PathVariable Long id) {
        log.debug("REST request to delete DireccionPersona : {}", id);
        direccionPersonaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
