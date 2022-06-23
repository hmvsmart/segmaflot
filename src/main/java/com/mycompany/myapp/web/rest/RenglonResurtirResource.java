package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.RenglonResurtir;
import com.mycompany.myapp.repository.RenglonResurtirRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RenglonResurtir}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RenglonResurtirResource {

    private final Logger log = LoggerFactory.getLogger(RenglonResurtirResource.class);

    private static final String ENTITY_NAME = "renglonResurtir";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RenglonResurtirRepository renglonResurtirRepository;

    public RenglonResurtirResource(RenglonResurtirRepository renglonResurtirRepository) {
        this.renglonResurtirRepository = renglonResurtirRepository;
    }

    /**
     * {@code POST  /renglon-resurtirs} : Create a new renglonResurtir.
     *
     * @param renglonResurtir the renglonResurtir to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new renglonResurtir, or with status {@code 400 (Bad Request)} if the renglonResurtir has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/renglon-resurtirs")
    public ResponseEntity<RenglonResurtir> createRenglonResurtir(@RequestBody RenglonResurtir renglonResurtir) throws URISyntaxException {
        log.debug("REST request to save RenglonResurtir : {}", renglonResurtir);
        if (renglonResurtir.getId() != null) {
            throw new BadRequestAlertException("A new renglonResurtir cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RenglonResurtir result = renglonResurtirRepository.save(renglonResurtir);
        return ResponseEntity
            .created(new URI("/api/renglon-resurtirs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /renglon-resurtirs/:id} : Updates an existing renglonResurtir.
     *
     * @param id the id of the renglonResurtir to save.
     * @param renglonResurtir the renglonResurtir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated renglonResurtir,
     * or with status {@code 400 (Bad Request)} if the renglonResurtir is not valid,
     * or with status {@code 500 (Internal Server Error)} if the renglonResurtir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/renglon-resurtirs/{id}")
    public ResponseEntity<RenglonResurtir> updateRenglonResurtir(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RenglonResurtir renglonResurtir
    ) throws URISyntaxException {
        log.debug("REST request to update RenglonResurtir : {}, {}", id, renglonResurtir);
        if (renglonResurtir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, renglonResurtir.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!renglonResurtirRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RenglonResurtir result = renglonResurtirRepository.save(renglonResurtir);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, renglonResurtir.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /renglon-resurtirs/:id} : Partial updates given fields of an existing renglonResurtir, field will ignore if it is null
     *
     * @param id the id of the renglonResurtir to save.
     * @param renglonResurtir the renglonResurtir to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated renglonResurtir,
     * or with status {@code 400 (Bad Request)} if the renglonResurtir is not valid,
     * or with status {@code 404 (Not Found)} if the renglonResurtir is not found,
     * or with status {@code 500 (Internal Server Error)} if the renglonResurtir couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/renglon-resurtirs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RenglonResurtir> partialUpdateRenglonResurtir(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RenglonResurtir renglonResurtir
    ) throws URISyntaxException {
        log.debug("REST request to partial update RenglonResurtir partially : {}, {}", id, renglonResurtir);
        if (renglonResurtir.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, renglonResurtir.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!renglonResurtirRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RenglonResurtir> result = renglonResurtirRepository
            .findById(renglonResurtir.getId())
            .map(existingRenglonResurtir -> {
                if (renglonResurtir.getFechaCaducidad() != null) {
                    existingRenglonResurtir.setFechaCaducidad(renglonResurtir.getFechaCaducidad());
                }
                if (renglonResurtir.getCantidad() != null) {
                    existingRenglonResurtir.setCantidad(renglonResurtir.getCantidad());
                }
                if (renglonResurtir.getPrecioCompra() != null) {
                    existingRenglonResurtir.setPrecioCompra(renglonResurtir.getPrecioCompra());
                }
                if (renglonResurtir.getCreateByUser() != null) {
                    existingRenglonResurtir.setCreateByUser(renglonResurtir.getCreateByUser());
                }
                if (renglonResurtir.getCreatedOn() != null) {
                    existingRenglonResurtir.setCreatedOn(renglonResurtir.getCreatedOn());
                }
                if (renglonResurtir.getModifyByUser() != null) {
                    existingRenglonResurtir.setModifyByUser(renglonResurtir.getModifyByUser());
                }
                if (renglonResurtir.getModifiedOn() != null) {
                    existingRenglonResurtir.setModifiedOn(renglonResurtir.getModifiedOn());
                }
                if (renglonResurtir.getAuditStatus() != null) {
                    existingRenglonResurtir.setAuditStatus(renglonResurtir.getAuditStatus());
                }

                return existingRenglonResurtir;
            })
            .map(renglonResurtirRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, renglonResurtir.getId().toString())
        );
    }

    /**
     * {@code GET  /renglon-resurtirs} : get all the renglonResurtirs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of renglonResurtirs in body.
     */
    @GetMapping("/renglon-resurtirs")
    public List<RenglonResurtir> getAllRenglonResurtirs() {
        log.debug("REST request to get all RenglonResurtirs");
        return renglonResurtirRepository.findAll();
    }

    /**
     * {@code GET  /renglon-resurtirs/:id} : get the "id" renglonResurtir.
     *
     * @param id the id of the renglonResurtir to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the renglonResurtir, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/renglon-resurtirs/{id}")
    public ResponseEntity<RenglonResurtir> getRenglonResurtir(@PathVariable Long id) {
        log.debug("REST request to get RenglonResurtir : {}", id);
        Optional<RenglonResurtir> renglonResurtir = renglonResurtirRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(renglonResurtir);
    }

    /**
     * {@code DELETE  /renglon-resurtirs/:id} : delete the "id" renglonResurtir.
     *
     * @param id the id of the renglonResurtir to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/renglon-resurtirs/{id}")
    public ResponseEntity<Void> deleteRenglonResurtir(@PathVariable Long id) {
        log.debug("REST request to delete RenglonResurtir : {}", id);
        renglonResurtirRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
