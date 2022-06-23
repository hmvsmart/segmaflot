package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Seccion;
import com.mycompany.myapp.repository.SeccionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Seccion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SeccionResource {

    private final Logger log = LoggerFactory.getLogger(SeccionResource.class);

    private static final String ENTITY_NAME = "seccion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeccionRepository seccionRepository;

    public SeccionResource(SeccionRepository seccionRepository) {
        this.seccionRepository = seccionRepository;
    }

    /**
     * {@code POST  /seccions} : Create a new seccion.
     *
     * @param seccion the seccion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seccion, or with status {@code 400 (Bad Request)} if the seccion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seccions")
    public ResponseEntity<Seccion> createSeccion(@RequestBody Seccion seccion) throws URISyntaxException {
        log.debug("REST request to save Seccion : {}", seccion);
        if (seccion.getId() != null) {
            throw new BadRequestAlertException("A new seccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seccion result = seccionRepository.save(seccion);
        return ResponseEntity
            .created(new URI("/api/seccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seccions/:id} : Updates an existing seccion.
     *
     * @param id the id of the seccion to save.
     * @param seccion the seccion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccion,
     * or with status {@code 400 (Bad Request)} if the seccion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seccion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seccions/{id}")
    public ResponseEntity<Seccion> updateSeccion(@PathVariable(value = "id", required = false) final Long id, @RequestBody Seccion seccion)
        throws URISyntaxException {
        log.debug("REST request to update Seccion : {}, {}", id, seccion);
        if (seccion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seccion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seccionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Seccion result = seccionRepository.save(seccion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seccions/:id} : Partial updates given fields of an existing seccion, field will ignore if it is null
     *
     * @param id the id of the seccion to save.
     * @param seccion the seccion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seccion,
     * or with status {@code 400 (Bad Request)} if the seccion is not valid,
     * or with status {@code 404 (Not Found)} if the seccion is not found,
     * or with status {@code 500 (Internal Server Error)} if the seccion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seccions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Seccion> partialUpdateSeccion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Seccion seccion
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seccion partially : {}, {}", id, seccion);
        if (seccion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seccion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seccionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Seccion> result = seccionRepository
            .findById(seccion.getId())
            .map(existingSeccion -> {
                if (seccion.getNombre() != null) {
                    existingSeccion.setNombre(seccion.getNombre());
                }
                if (seccion.getCreateByUser() != null) {
                    existingSeccion.setCreateByUser(seccion.getCreateByUser());
                }
                if (seccion.getCreatedOn() != null) {
                    existingSeccion.setCreatedOn(seccion.getCreatedOn());
                }
                if (seccion.getModifyByUser() != null) {
                    existingSeccion.setModifyByUser(seccion.getModifyByUser());
                }
                if (seccion.getModifiedOn() != null) {
                    existingSeccion.setModifiedOn(seccion.getModifiedOn());
                }
                if (seccion.getAuditStatus() != null) {
                    existingSeccion.setAuditStatus(seccion.getAuditStatus());
                }

                return existingSeccion;
            })
            .map(seccionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seccion.getId().toString())
        );
    }

    /**
     * {@code GET  /seccions} : get all the seccions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seccions in body.
     */
    @GetMapping("/seccions")
    public List<Seccion> getAllSeccions() {
        log.debug("REST request to get all Seccions");
        return seccionRepository.findAll();
    }

    /**
     * {@code GET  /seccions/:id} : get the "id" seccion.
     *
     * @param id the id of the seccion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seccion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seccions/{id}")
    public ResponseEntity<Seccion> getSeccion(@PathVariable Long id) {
        log.debug("REST request to get Seccion : {}", id);
        Optional<Seccion> seccion = seccionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seccion);
    }

    /**
     * {@code DELETE  /seccions/:id} : delete the "id" seccion.
     *
     * @param id the id of the seccion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seccions/{id}")
    public ResponseEntity<Void> deleteSeccion(@PathVariable Long id) {
        log.debug("REST request to delete Seccion : {}", id);
        seccionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
