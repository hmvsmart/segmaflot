package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.MaterialServicio;
import com.mycompany.myapp.repository.MaterialServicioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MaterialServicio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MaterialServicioResource {

    private final Logger log = LoggerFactory.getLogger(MaterialServicioResource.class);

    private static final String ENTITY_NAME = "materialServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialServicioRepository materialServicioRepository;

    public MaterialServicioResource(MaterialServicioRepository materialServicioRepository) {
        this.materialServicioRepository = materialServicioRepository;
    }

    /**
     * {@code POST  /material-servicios} : Create a new materialServicio.
     *
     * @param materialServicio the materialServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materialServicio, or with status {@code 400 (Bad Request)} if the materialServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/material-servicios")
    public ResponseEntity<MaterialServicio> createMaterialServicio(@RequestBody MaterialServicio materialServicio)
        throws URISyntaxException {
        log.debug("REST request to save MaterialServicio : {}", materialServicio);
        if (materialServicio.getId() != null) {
            throw new BadRequestAlertException("A new materialServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MaterialServicio result = materialServicioRepository.save(materialServicio);
        return ResponseEntity
            .created(new URI("/api/material-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /material-servicios/:id} : Updates an existing materialServicio.
     *
     * @param id the id of the materialServicio to save.
     * @param materialServicio the materialServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialServicio,
     * or with status {@code 400 (Bad Request)} if the materialServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materialServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/material-servicios/{id}")
    public ResponseEntity<MaterialServicio> updateMaterialServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialServicio materialServicio
    ) throws URISyntaxException {
        log.debug("REST request to update MaterialServicio : {}, {}", id, materialServicio);
        if (materialServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MaterialServicio result = materialServicioRepository.save(materialServicio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialServicio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /material-servicios/:id} : Partial updates given fields of an existing materialServicio, field will ignore if it is null
     *
     * @param id the id of the materialServicio to save.
     * @param materialServicio the materialServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materialServicio,
     * or with status {@code 400 (Bad Request)} if the materialServicio is not valid,
     * or with status {@code 404 (Not Found)} if the materialServicio is not found,
     * or with status {@code 500 (Internal Server Error)} if the materialServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/material-servicios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MaterialServicio> partialUpdateMaterialServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MaterialServicio materialServicio
    ) throws URISyntaxException {
        log.debug("REST request to partial update MaterialServicio partially : {}, {}", id, materialServicio);
        if (materialServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materialServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MaterialServicio> result = materialServicioRepository
            .findById(materialServicio.getId())
            .map(existingMaterialServicio -> {
                if (materialServicio.getFecha() != null) {
                    existingMaterialServicio.setFecha(materialServicio.getFecha());
                }
                if (materialServicio.getCantidad() != null) {
                    existingMaterialServicio.setCantidad(materialServicio.getCantidad());
                }
                if (materialServicio.getCreateByUser() != null) {
                    existingMaterialServicio.setCreateByUser(materialServicio.getCreateByUser());
                }
                if (materialServicio.getCreatedOn() != null) {
                    existingMaterialServicio.setCreatedOn(materialServicio.getCreatedOn());
                }
                if (materialServicio.getModifyByUser() != null) {
                    existingMaterialServicio.setModifyByUser(materialServicio.getModifyByUser());
                }
                if (materialServicio.getModifiedOn() != null) {
                    existingMaterialServicio.setModifiedOn(materialServicio.getModifiedOn());
                }
                if (materialServicio.getAuditStatus() != null) {
                    existingMaterialServicio.setAuditStatus(materialServicio.getAuditStatus());
                }

                return existingMaterialServicio;
            })
            .map(materialServicioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materialServicio.getId().toString())
        );
    }

    /**
     * {@code GET  /material-servicios} : get all the materialServicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materialServicios in body.
     */
    @GetMapping("/material-servicios")
    public List<MaterialServicio> getAllMaterialServicios() {
        log.debug("REST request to get all MaterialServicios");
        return materialServicioRepository.findAll();
    }

    /**
     * {@code GET  /material-servicios/:id} : get the "id" materialServicio.
     *
     * @param id the id of the materialServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materialServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/material-servicios/{id}")
    public ResponseEntity<MaterialServicio> getMaterialServicio(@PathVariable Long id) {
        log.debug("REST request to get MaterialServicio : {}", id);
        Optional<MaterialServicio> materialServicio = materialServicioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materialServicio);
    }

    /**
     * {@code DELETE  /material-servicios/:id} : delete the "id" materialServicio.
     *
     * @param id the id of the materialServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/material-servicios/{id}")
    public ResponseEntity<Void> deleteMaterialServicio(@PathVariable Long id) {
        log.debug("REST request to delete MaterialServicio : {}", id);
        materialServicioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
