package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Cotizacion;
import com.mycompany.myapp.repository.CotizacionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Cotizacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CotizacionResource {

    private final Logger log = LoggerFactory.getLogger(CotizacionResource.class);

    private static final String ENTITY_NAME = "cotizacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CotizacionRepository cotizacionRepository;

    public CotizacionResource(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    /**
     * {@code POST  /cotizacions} : Create a new cotizacion.
     *
     * @param cotizacion the cotizacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cotizacion, or with status {@code 400 (Bad Request)} if the cotizacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cotizacions")
    public ResponseEntity<Cotizacion> createCotizacion(@RequestBody Cotizacion cotizacion) throws URISyntaxException {
        log.debug("REST request to save Cotizacion : {}", cotizacion);
        if (cotizacion.getId() != null) {
            throw new BadRequestAlertException("A new cotizacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cotizacion result = cotizacionRepository.save(cotizacion);
        return ResponseEntity
            .created(new URI("/api/cotizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cotizacions/:id} : Updates an existing cotizacion.
     *
     * @param id the id of the cotizacion to save.
     * @param cotizacion the cotizacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cotizacion,
     * or with status {@code 400 (Bad Request)} if the cotizacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cotizacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cotizacions/{id}")
    public ResponseEntity<Cotizacion> updateCotizacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cotizacion cotizacion
    ) throws URISyntaxException {
        log.debug("REST request to update Cotizacion : {}, {}", id, cotizacion);
        if (cotizacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cotizacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cotizacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cotizacion result = cotizacionRepository.save(cotizacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cotizacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cotizacions/:id} : Partial updates given fields of an existing cotizacion, field will ignore if it is null
     *
     * @param id the id of the cotizacion to save.
     * @param cotizacion the cotizacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cotizacion,
     * or with status {@code 400 (Bad Request)} if the cotizacion is not valid,
     * or with status {@code 404 (Not Found)} if the cotizacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the cotizacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cotizacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cotizacion> partialUpdateCotizacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cotizacion cotizacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cotizacion partially : {}, {}", id, cotizacion);
        if (cotizacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cotizacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cotizacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cotizacion> result = cotizacionRepository
            .findById(cotizacion.getId())
            .map(existingCotizacion -> {
                if (cotizacion.getFecha() != null) {
                    existingCotizacion.setFecha(cotizacion.getFecha());
                }
                if (cotizacion.getCreateByUser() != null) {
                    existingCotizacion.setCreateByUser(cotizacion.getCreateByUser());
                }
                if (cotizacion.getCreatedOn() != null) {
                    existingCotizacion.setCreatedOn(cotizacion.getCreatedOn());
                }
                if (cotizacion.getModifyByUser() != null) {
                    existingCotizacion.setModifyByUser(cotizacion.getModifyByUser());
                }
                if (cotizacion.getModifiedOn() != null) {
                    existingCotizacion.setModifiedOn(cotizacion.getModifiedOn());
                }
                if (cotizacion.getAuditStatus() != null) {
                    existingCotizacion.setAuditStatus(cotizacion.getAuditStatus());
                }

                return existingCotizacion;
            })
            .map(cotizacionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cotizacion.getId().toString())
        );
    }

    /**
     * {@code GET  /cotizacions} : get all the cotizacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cotizacions in body.
     */
    @GetMapping("/cotizacions")
    public List<Cotizacion> getAllCotizacions() {
        log.debug("REST request to get all Cotizacions");
        return cotizacionRepository.findAll();
    }

    /**
     * {@code GET  /cotizacions/:id} : get the "id" cotizacion.
     *
     * @param id the id of the cotizacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cotizacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cotizacions/{id}")
    public ResponseEntity<Cotizacion> getCotizacion(@PathVariable Long id) {
        log.debug("REST request to get Cotizacion : {}", id);
        Optional<Cotizacion> cotizacion = cotizacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cotizacion);
    }

    /**
     * {@code DELETE  /cotizacions/:id} : delete the "id" cotizacion.
     *
     * @param id the id of the cotizacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cotizacions/{id}")
    public ResponseEntity<Void> deleteCotizacion(@PathVariable Long id) {
        log.debug("REST request to delete Cotizacion : {}", id);
        cotizacionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
