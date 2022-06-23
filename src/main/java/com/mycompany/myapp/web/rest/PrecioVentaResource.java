package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PrecioVenta;
import com.mycompany.myapp.repository.PrecioVentaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PrecioVenta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrecioVentaResource {

    private final Logger log = LoggerFactory.getLogger(PrecioVentaResource.class);

    private static final String ENTITY_NAME = "precioVenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrecioVentaRepository precioVentaRepository;

    public PrecioVentaResource(PrecioVentaRepository precioVentaRepository) {
        this.precioVentaRepository = precioVentaRepository;
    }

    /**
     * {@code POST  /precio-ventas} : Create a new precioVenta.
     *
     * @param precioVenta the precioVenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new precioVenta, or with status {@code 400 (Bad Request)} if the precioVenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/precio-ventas")
    public ResponseEntity<PrecioVenta> createPrecioVenta(@RequestBody PrecioVenta precioVenta) throws URISyntaxException {
        log.debug("REST request to save PrecioVenta : {}", precioVenta);
        if (precioVenta.getId() != null) {
            throw new BadRequestAlertException("A new precioVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecioVenta result = precioVentaRepository.save(precioVenta);
        return ResponseEntity
            .created(new URI("/api/precio-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /precio-ventas/:id} : Updates an existing precioVenta.
     *
     * @param id the id of the precioVenta to save.
     * @param precioVenta the precioVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioVenta,
     * or with status {@code 400 (Bad Request)} if the precioVenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the precioVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/precio-ventas/{id}")
    public ResponseEntity<PrecioVenta> updatePrecioVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrecioVenta precioVenta
    ) throws URISyntaxException {
        log.debug("REST request to update PrecioVenta : {}, {}", id, precioVenta);
        if (precioVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precioVenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precioVentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrecioVenta result = precioVentaRepository.save(precioVenta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioVenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /precio-ventas/:id} : Partial updates given fields of an existing precioVenta, field will ignore if it is null
     *
     * @param id the id of the precioVenta to save.
     * @param precioVenta the precioVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated precioVenta,
     * or with status {@code 400 (Bad Request)} if the precioVenta is not valid,
     * or with status {@code 404 (Not Found)} if the precioVenta is not found,
     * or with status {@code 500 (Internal Server Error)} if the precioVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/precio-ventas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrecioVenta> partialUpdatePrecioVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrecioVenta precioVenta
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrecioVenta partially : {}, {}", id, precioVenta);
        if (precioVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, precioVenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!precioVentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrecioVenta> result = precioVentaRepository
            .findById(precioVenta.getId())
            .map(existingPrecioVenta -> {
                if (precioVenta.getFecha() != null) {
                    existingPrecioVenta.setFecha(precioVenta.getFecha());
                }
                if (precioVenta.getPpu() != null) {
                    existingPrecioVenta.setPpu(precioVenta.getPpu());
                }
                if (precioVenta.getCreateByUser() != null) {
                    existingPrecioVenta.setCreateByUser(precioVenta.getCreateByUser());
                }
                if (precioVenta.getCreatedOn() != null) {
                    existingPrecioVenta.setCreatedOn(precioVenta.getCreatedOn());
                }
                if (precioVenta.getModifyByUser() != null) {
                    existingPrecioVenta.setModifyByUser(precioVenta.getModifyByUser());
                }
                if (precioVenta.getModifiedOn() != null) {
                    existingPrecioVenta.setModifiedOn(precioVenta.getModifiedOn());
                }
                if (precioVenta.getAuditStatus() != null) {
                    existingPrecioVenta.setAuditStatus(precioVenta.getAuditStatus());
                }

                return existingPrecioVenta;
            })
            .map(precioVentaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, precioVenta.getId().toString())
        );
    }

    /**
     * {@code GET  /precio-ventas} : get all the precioVentas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of precioVentas in body.
     */
    @GetMapping("/precio-ventas")
    public List<PrecioVenta> getAllPrecioVentas() {
        log.debug("REST request to get all PrecioVentas");
        return precioVentaRepository.findAll();
    }

    /**
     * {@code GET  /precio-ventas/:id} : get the "id" precioVenta.
     *
     * @param id the id of the precioVenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the precioVenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/precio-ventas/{id}")
    public ResponseEntity<PrecioVenta> getPrecioVenta(@PathVariable Long id) {
        log.debug("REST request to get PrecioVenta : {}", id);
        Optional<PrecioVenta> precioVenta = precioVentaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(precioVenta);
    }

    /**
     * {@code DELETE  /precio-ventas/:id} : delete the "id" precioVenta.
     *
     * @param id the id of the precioVenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/precio-ventas/{id}")
    public ResponseEntity<Void> deletePrecioVenta(@PathVariable Long id) {
        log.debug("REST request to delete PrecioVenta : {}", id);
        precioVentaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
