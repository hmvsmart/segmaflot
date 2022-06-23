package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.OperadorUnidad;
import com.mycompany.myapp.repository.OperadorUnidadRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.OperadorUnidad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OperadorUnidadResource {

    private final Logger log = LoggerFactory.getLogger(OperadorUnidadResource.class);

    private static final String ENTITY_NAME = "operadorUnidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperadorUnidadRepository operadorUnidadRepository;

    public OperadorUnidadResource(OperadorUnidadRepository operadorUnidadRepository) {
        this.operadorUnidadRepository = operadorUnidadRepository;
    }

    /**
     * {@code POST  /operador-unidads} : Create a new operadorUnidad.
     *
     * @param operadorUnidad the operadorUnidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operadorUnidad, or with status {@code 400 (Bad Request)} if the operadorUnidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operador-unidads")
    public ResponseEntity<OperadorUnidad> createOperadorUnidad(@RequestBody OperadorUnidad operadorUnidad) throws URISyntaxException {
        log.debug("REST request to save OperadorUnidad : {}", operadorUnidad);
        if (operadorUnidad.getId() != null) {
            throw new BadRequestAlertException("A new operadorUnidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperadorUnidad result = operadorUnidadRepository.save(operadorUnidad);
        return ResponseEntity
            .created(new URI("/api/operador-unidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operador-unidads/:id} : Updates an existing operadorUnidad.
     *
     * @param id the id of the operadorUnidad to save.
     * @param operadorUnidad the operadorUnidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operadorUnidad,
     * or with status {@code 400 (Bad Request)} if the operadorUnidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operadorUnidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operador-unidads/{id}")
    public ResponseEntity<OperadorUnidad> updateOperadorUnidad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperadorUnidad operadorUnidad
    ) throws URISyntaxException {
        log.debug("REST request to update OperadorUnidad : {}, {}", id, operadorUnidad);
        if (operadorUnidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operadorUnidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operadorUnidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperadorUnidad result = operadorUnidadRepository.save(operadorUnidad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operadorUnidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operador-unidads/:id} : Partial updates given fields of an existing operadorUnidad, field will ignore if it is null
     *
     * @param id the id of the operadorUnidad to save.
     * @param operadorUnidad the operadorUnidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operadorUnidad,
     * or with status {@code 400 (Bad Request)} if the operadorUnidad is not valid,
     * or with status {@code 404 (Not Found)} if the operadorUnidad is not found,
     * or with status {@code 500 (Internal Server Error)} if the operadorUnidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operador-unidads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperadorUnidad> partialUpdateOperadorUnidad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperadorUnidad operadorUnidad
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperadorUnidad partially : {}, {}", id, operadorUnidad);
        if (operadorUnidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operadorUnidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operadorUnidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperadorUnidad> result = operadorUnidadRepository
            .findById(operadorUnidad.getId())
            .map(existingOperadorUnidad -> {
                if (operadorUnidad.getFecha() != null) {
                    existingOperadorUnidad.setFecha(operadorUnidad.getFecha());
                }
                if (operadorUnidad.getRol() != null) {
                    existingOperadorUnidad.setRol(operadorUnidad.getRol());
                }
                if (operadorUnidad.getStatus() != null) {
                    existingOperadorUnidad.setStatus(operadorUnidad.getStatus());
                }
                if (operadorUnidad.getCreateByUser() != null) {
                    existingOperadorUnidad.setCreateByUser(operadorUnidad.getCreateByUser());
                }
                if (operadorUnidad.getCreatedOn() != null) {
                    existingOperadorUnidad.setCreatedOn(operadorUnidad.getCreatedOn());
                }
                if (operadorUnidad.getModifyByUser() != null) {
                    existingOperadorUnidad.setModifyByUser(operadorUnidad.getModifyByUser());
                }
                if (operadorUnidad.getModifiedOn() != null) {
                    existingOperadorUnidad.setModifiedOn(operadorUnidad.getModifiedOn());
                }
                if (operadorUnidad.getAuditStatus() != null) {
                    existingOperadorUnidad.setAuditStatus(operadorUnidad.getAuditStatus());
                }

                return existingOperadorUnidad;
            })
            .map(operadorUnidadRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operadorUnidad.getId().toString())
        );
    }

    /**
     * {@code GET  /operador-unidads} : get all the operadorUnidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operadorUnidads in body.
     */
    @GetMapping("/operador-unidads")
    public List<OperadorUnidad> getAllOperadorUnidads() {
        log.debug("REST request to get all OperadorUnidads");
        return operadorUnidadRepository.findAll();
    }

    /**
     * {@code GET  /operador-unidads/:id} : get the "id" operadorUnidad.
     *
     * @param id the id of the operadorUnidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operadorUnidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operador-unidads/{id}")
    public ResponseEntity<OperadorUnidad> getOperadorUnidad(@PathVariable Long id) {
        log.debug("REST request to get OperadorUnidad : {}", id);
        Optional<OperadorUnidad> operadorUnidad = operadorUnidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(operadorUnidad);
    }

    /**
     * {@code DELETE  /operador-unidads/:id} : delete the "id" operadorUnidad.
     *
     * @param id the id of the operadorUnidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operador-unidads/{id}")
    public ResponseEntity<Void> deleteOperadorUnidad(@PathVariable Long id) {
        log.debug("REST request to delete OperadorUnidad : {}", id);
        operadorUnidadRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
