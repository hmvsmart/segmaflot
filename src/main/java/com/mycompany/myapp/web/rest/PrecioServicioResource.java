package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PrecioServicio;
import com.mycompany.myapp.repository.PrecioServicioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PrecioServicio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrecioServicioResource {

    private final Logger log = LoggerFactory.getLogger(PrecioServicioResource.class);

    private static final String ENTITY_NAME = "precioServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrecioServicioRepository precioServicioRepository;

    public PrecioServicioResource(PrecioServicioRepository precioServicioRepository) {
        this.precioServicioRepository = precioServicioRepository;
    }

    /**
     * {@code POST  /precio-servicios} : Create a new precioServicio.
     *
     * @param precioServicio the precioServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precioServicio, or with status {@code 400 (Bad Request)} if the precioServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/precio-servicios")
    public ResponseEntity<PrecioServicio> createPrecioServicio(@RequestBody PrecioServicio precioServicio) throws URISyntaxException {
        log.debug("REST request to save PrecioServicio : {}", precioServicio);
        if (precioServicio.getId() != null) {
            throw new BadRequestAlertException("A new precioServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecioServicio result = precioServicioRepository.save(precioServicio);
        return ResponseEntity
            .created(new URI("/api/precio-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /precio-servicios/:id} : Updates an existing precioServicio.
     *
     * @param id the id of the precioServicio to save.
     * @param precioServicio the precioServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioServicio,
     * or with status {@code 400 (Bad Request)} if the precioServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the precioServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/precio-servicios/{id}")
    public ResponseEntity<PrecioServicio> updatePrecioServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrecioServicio precioServicio
    ) throws URISyntaxException {
        log.debug("REST request to update PrecioServicio : {}, {}", id, precioServicio);
        if (precioServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precioServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precioServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrecioServicio result = precioServicioRepository.save(precioServicio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioServicio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /precio-servicios/:id} : Partial updates given fields of an existing precioServicio, field will ignore if it is null
     *
     * @param id the id of the precioServicio to save.
     * @param precioServicio the precioServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioServicio,
     * or with status {@code 400 (Bad Request)} if the precioServicio is not valid,
     * or with status {@code 404 (Not Found)} if the precioServicio is not found,
     * or with status {@code 500 (Internal Server Error)} if the precioServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/precio-servicios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrecioServicio> partialUpdatePrecioServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrecioServicio precioServicio
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrecioServicio partially : {}, {}", id, precioServicio);
        if (precioServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precioServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precioServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrecioServicio> result = precioServicioRepository
            .findById(precioServicio.getId())
            .map(existingPrecioServicio -> {
                if (precioServicio.getFecha() != null) {
                    existingPrecioServicio.setFecha(precioServicio.getFecha());
                }
                if (precioServicio.getCosto() != null) {
                    existingPrecioServicio.setCosto(precioServicio.getCosto());
                }
                if (precioServicio.getCreateByUser() != null) {
                    existingPrecioServicio.setCreateByUser(precioServicio.getCreateByUser());
                }
                if (precioServicio.getCreatedOn() != null) {
                    existingPrecioServicio.setCreatedOn(precioServicio.getCreatedOn());
                }
                if (precioServicio.getModifyByUser() != null) {
                    existingPrecioServicio.setModifyByUser(precioServicio.getModifyByUser());
                }
                if (precioServicio.getModifiedOn() != null) {
                    existingPrecioServicio.setModifiedOn(precioServicio.getModifiedOn());
                }
                if (precioServicio.getAuditStatus() != null) {
                    existingPrecioServicio.setAuditStatus(precioServicio.getAuditStatus());
                }

                return existingPrecioServicio;
            })
            .map(precioServicioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioServicio.getId().toString())
        );
    }

    /**
     * {@code GET  /precio-servicios} : get all the precioServicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of precioServicios in body.
     */
    @GetMapping("/precio-servicios")
    public List<PrecioServicio> getAllPrecioServicios() {
        log.debug("REST request to get all PrecioServicios");
        return precioServicioRepository.findAll();
    }

    /**
     * {@code GET  /precio-servicios/:id} : get the "id" precioServicio.
     *
     * @param id the id of the precioServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precioServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/precio-servicios/{id}")
    public ResponseEntity<PrecioServicio> getPrecioServicio(@PathVariable Long id) {
        log.debug("REST request to get PrecioServicio : {}", id);
        Optional<PrecioServicio> precioServicio = precioServicioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(precioServicio);
    }

    /**
     * {@code DELETE  /precio-servicios/:id} : delete the "id" precioServicio.
     *
     * @param id the id of the precioServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/precio-servicios/{id}")
    public ResponseEntity<Void> deletePrecioServicio(@PathVariable Long id) {
        log.debug("REST request to delete PrecioServicio : {}", id);
        precioServicioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
