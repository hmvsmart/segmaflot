package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ExperienciaHabilidad;
import com.mycompany.myapp.repository.ExperienciaHabilidadRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ExperienciaHabilidad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExperienciaHabilidadResource {

    private final Logger log = LoggerFactory.getLogger(ExperienciaHabilidadResource.class);

    private static final String ENTITY_NAME = "experienciaHabilidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExperienciaHabilidadRepository experienciaHabilidadRepository;

    public ExperienciaHabilidadResource(ExperienciaHabilidadRepository experienciaHabilidadRepository) {
        this.experienciaHabilidadRepository = experienciaHabilidadRepository;
    }

    /**
     * {@code POST  /experiencia-habilidads} : Create a new experienciaHabilidad.
     *
     * @param experienciaHabilidad the experienciaHabilidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new experienciaHabilidad, or with status {@code 400 (Bad Request)} if the experienciaHabilidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/experiencia-habilidads")
    public ResponseEntity<ExperienciaHabilidad> createExperienciaHabilidad(@RequestBody ExperienciaHabilidad experienciaHabilidad)
        throws URISyntaxException {
        log.debug("REST request to save ExperienciaHabilidad : {}", experienciaHabilidad);
        if (experienciaHabilidad.getId() != null) {
            throw new BadRequestAlertException("A new experienciaHabilidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienciaHabilidad result = experienciaHabilidadRepository.save(experienciaHabilidad);
        return ResponseEntity
            .created(new URI("/api/experiencia-habilidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /experiencia-habilidads/:id} : Updates an existing experienciaHabilidad.
     *
     * @param id the id of the experienciaHabilidad to save.
     * @param experienciaHabilidad the experienciaHabilidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experienciaHabilidad,
     * or with status {@code 400 (Bad Request)} if the experienciaHabilidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the experienciaHabilidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/experiencia-habilidads/{id}")
    public ResponseEntity<ExperienciaHabilidad> updateExperienciaHabilidad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExperienciaHabilidad experienciaHabilidad
    ) throws URISyntaxException {
        log.debug("REST request to update ExperienciaHabilidad : {}, {}", id, experienciaHabilidad);
        if (experienciaHabilidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experienciaHabilidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienciaHabilidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExperienciaHabilidad result = experienciaHabilidadRepository.save(experienciaHabilidad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, experienciaHabilidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /experiencia-habilidads/:id} : Partial updates given fields of an existing experienciaHabilidad, field will ignore if it is null
     *
     * @param id the id of the experienciaHabilidad to save.
     * @param experienciaHabilidad the experienciaHabilidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated experienciaHabilidad,
     * or with status {@code 400 (Bad Request)} if the experienciaHabilidad is not valid,
     * or with status {@code 404 (Not Found)} if the experienciaHabilidad is not found,
     * or with status {@code 500 (Internal Server Error)} if the experienciaHabilidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/experiencia-habilidads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExperienciaHabilidad> partialUpdateExperienciaHabilidad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExperienciaHabilidad experienciaHabilidad
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExperienciaHabilidad partially : {}, {}", id, experienciaHabilidad);
        if (experienciaHabilidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, experienciaHabilidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!experienciaHabilidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExperienciaHabilidad> result = experienciaHabilidadRepository
            .findById(experienciaHabilidad.getId())
            .map(existingExperienciaHabilidad -> {
                if (experienciaHabilidad.getFecha() != null) {
                    existingExperienciaHabilidad.setFecha(experienciaHabilidad.getFecha());
                }
                if (experienciaHabilidad.getDescripcion() != null) {
                    existingExperienciaHabilidad.setDescripcion(experienciaHabilidad.getDescripcion());
                }
                if (experienciaHabilidad.getDescripcionContentType() != null) {
                    existingExperienciaHabilidad.setDescripcionContentType(experienciaHabilidad.getDescripcionContentType());
                }
                if (experienciaHabilidad.getCreateByUser() != null) {
                    existingExperienciaHabilidad.setCreateByUser(experienciaHabilidad.getCreateByUser());
                }
                if (experienciaHabilidad.getCreatedOn() != null) {
                    existingExperienciaHabilidad.setCreatedOn(experienciaHabilidad.getCreatedOn());
                }
                if (experienciaHabilidad.getModifyByUser() != null) {
                    existingExperienciaHabilidad.setModifyByUser(experienciaHabilidad.getModifyByUser());
                }
                if (experienciaHabilidad.getModifiedOn() != null) {
                    existingExperienciaHabilidad.setModifiedOn(experienciaHabilidad.getModifiedOn());
                }
                if (experienciaHabilidad.getAuditStatus() != null) {
                    existingExperienciaHabilidad.setAuditStatus(experienciaHabilidad.getAuditStatus());
                }

                return existingExperienciaHabilidad;
            })
            .map(experienciaHabilidadRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, experienciaHabilidad.getId().toString())
        );
    }

    /**
     * {@code GET  /experiencia-habilidads} : get all the experienciaHabilidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of experienciaHabilidads in body.
     */
    @GetMapping("/experiencia-habilidads")
    public List<ExperienciaHabilidad> getAllExperienciaHabilidads() {
        log.debug("REST request to get all ExperienciaHabilidads");
        return experienciaHabilidadRepository.findAll();
    }

    /**
     * {@code GET  /experiencia-habilidads/:id} : get the "id" experienciaHabilidad.
     *
     * @param id the id of the experienciaHabilidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the experienciaHabilidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/experiencia-habilidads/{id}")
    public ResponseEntity<ExperienciaHabilidad> getExperienciaHabilidad(@PathVariable Long id) {
        log.debug("REST request to get ExperienciaHabilidad : {}", id);
        Optional<ExperienciaHabilidad> experienciaHabilidad = experienciaHabilidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(experienciaHabilidad);
    }

    /**
     * {@code DELETE  /experiencia-habilidads/:id} : delete the "id" experienciaHabilidad.
     *
     * @param id the id of the experienciaHabilidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/experiencia-habilidads/{id}")
    public ResponseEntity<Void> deleteExperienciaHabilidad(@PathVariable Long id) {
        log.debug("REST request to delete ExperienciaHabilidad : {}", id);
        experienciaHabilidadRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
