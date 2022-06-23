package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.RenglonVenta;
import com.mycompany.myapp.repository.RenglonVentaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RenglonVenta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RenglonVentaResource {

    private final Logger log = LoggerFactory.getLogger(RenglonVentaResource.class);

    private static final String ENTITY_NAME = "renglonVenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RenglonVentaRepository renglonVentaRepository;

    public RenglonVentaResource(RenglonVentaRepository renglonVentaRepository) {
        this.renglonVentaRepository = renglonVentaRepository;
    }

    /**
     * {@code POST  /renglon-ventas} : Create a new renglonVenta.
     *
     * @param renglonVenta the renglonVenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new renglonVenta, or with status {@code 400 (Bad Request)} if the renglonVenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/renglon-ventas")
    public ResponseEntity<RenglonVenta> createRenglonVenta(@RequestBody RenglonVenta renglonVenta) throws URISyntaxException {
        log.debug("REST request to save RenglonVenta : {}", renglonVenta);
        if (renglonVenta.getId() != null) {
            throw new BadRequestAlertException("A new renglonVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RenglonVenta result = renglonVentaRepository.save(renglonVenta);
        return ResponseEntity
            .created(new URI("/api/renglon-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /renglon-ventas/:id} : Updates an existing renglonVenta.
     *
     * @param id the id of the renglonVenta to save.
     * @param renglonVenta the renglonVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated renglonVenta,
     * or with status {@code 400 (Bad Request)} if the renglonVenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the renglonVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/renglon-ventas/{id}")
    public ResponseEntity<RenglonVenta> updateRenglonVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RenglonVenta renglonVenta
    ) throws URISyntaxException {
        log.debug("REST request to update RenglonVenta : {}, {}", id, renglonVenta);
        if (renglonVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, renglonVenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!renglonVentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RenglonVenta result = renglonVentaRepository.save(renglonVenta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, renglonVenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /renglon-ventas/:id} : Partial updates given fields of an existing renglonVenta, field will ignore if it is null
     *
     * @param id the id of the renglonVenta to save.
     * @param renglonVenta the renglonVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated renglonVenta,
     * or with status {@code 400 (Bad Request)} if the renglonVenta is not valid,
     * or with status {@code 404 (Not Found)} if the renglonVenta is not found,
     * or with status {@code 500 (Internal Server Error)} if the renglonVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/renglon-ventas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RenglonVenta> partialUpdateRenglonVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RenglonVenta renglonVenta
    ) throws URISyntaxException {
        log.debug("REST request to partial update RenglonVenta partially : {}, {}", id, renglonVenta);
        if (renglonVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, renglonVenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!renglonVentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RenglonVenta> result = renglonVentaRepository
            .findById(renglonVenta.getId())
            .map(existingRenglonVenta -> {
                if (renglonVenta.getCantidad() != null) {
                    existingRenglonVenta.setCantidad(renglonVenta.getCantidad());
                }
                if (renglonVenta.getCreateByUser() != null) {
                    existingRenglonVenta.setCreateByUser(renglonVenta.getCreateByUser());
                }
                if (renglonVenta.getCreatedOn() != null) {
                    existingRenglonVenta.setCreatedOn(renglonVenta.getCreatedOn());
                }
                if (renglonVenta.getModifyByUser() != null) {
                    existingRenglonVenta.setModifyByUser(renglonVenta.getModifyByUser());
                }
                if (renglonVenta.getModifiedOn() != null) {
                    existingRenglonVenta.setModifiedOn(renglonVenta.getModifiedOn());
                }
                if (renglonVenta.getAuditStatus() != null) {
                    existingRenglonVenta.setAuditStatus(renglonVenta.getAuditStatus());
                }

                return existingRenglonVenta;
            })
            .map(renglonVentaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, renglonVenta.getId().toString())
        );
    }

    /**
     * {@code GET  /renglon-ventas} : get all the renglonVentas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of renglonVentas in body.
     */
    @GetMapping("/renglon-ventas")
    public List<RenglonVenta> getAllRenglonVentas() {
        log.debug("REST request to get all RenglonVentas");
        return renglonVentaRepository.findAll();
    }

    /**
     * {@code GET  /renglon-ventas/:id} : get the "id" renglonVenta.
     *
     * @param id the id of the renglonVenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the renglonVenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/renglon-ventas/{id}")
    public ResponseEntity<RenglonVenta> getRenglonVenta(@PathVariable Long id) {
        log.debug("REST request to get RenglonVenta : {}", id);
        Optional<RenglonVenta> renglonVenta = renglonVentaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(renglonVenta);
    }

    /**
     * {@code DELETE  /renglon-ventas/:id} : delete the "id" renglonVenta.
     *
     * @param id the id of the renglonVenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/renglon-ventas/{id}")
    public ResponseEntity<Void> deleteRenglonVenta(@PathVariable Long id) {
        log.debug("REST request to delete RenglonVenta : {}", id);
        renglonVentaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
