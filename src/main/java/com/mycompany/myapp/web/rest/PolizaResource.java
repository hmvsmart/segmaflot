package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Poliza;
import com.mycompany.myapp.repository.PolizaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Poliza}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PolizaResource {

    private final Logger log = LoggerFactory.getLogger(PolizaResource.class);

    private static final String ENTITY_NAME = "poliza";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolizaRepository polizaRepository;

    public PolizaResource(PolizaRepository polizaRepository) {
        this.polizaRepository = polizaRepository;
    }

    /**
     * {@code POST  /polizas} : Create a new poliza.
     *
     * @param poliza the poliza to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poliza, or with status {@code 400 (Bad Request)} if the poliza has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/polizas")
    public ResponseEntity<Poliza> createPoliza(@RequestBody Poliza poliza) throws URISyntaxException {
        log.debug("REST request to save Poliza : {}", poliza);
        if (poliza.getId() != null) {
            throw new BadRequestAlertException("A new poliza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Poliza result = polizaRepository.save(poliza);
        return ResponseEntity
            .created(new URI("/api/polizas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /polizas/:id} : Updates an existing poliza.
     *
     * @param id the id of the poliza to save.
     * @param poliza the poliza to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poliza,
     * or with status {@code 400 (Bad Request)} if the poliza is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poliza couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/polizas/{id}")
    public ResponseEntity<Poliza> updatePoliza(@PathVariable(value = "id", required = false) final Long id, @RequestBody Poliza poliza)
        throws URISyntaxException {
        log.debug("REST request to update Poliza : {}, {}", id, poliza);
        if (poliza.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poliza.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!polizaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Poliza result = polizaRepository.save(poliza);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poliza.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /polizas/:id} : Partial updates given fields of an existing poliza, field will ignore if it is null
     *
     * @param id the id of the poliza to save.
     * @param poliza the poliza to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poliza,
     * or with status {@code 400 (Bad Request)} if the poliza is not valid,
     * or with status {@code 404 (Not Found)} if the poliza is not found,
     * or with status {@code 500 (Internal Server Error)} if the poliza couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/polizas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Poliza> partialUpdatePoliza(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Poliza poliza
    ) throws URISyntaxException {
        log.debug("REST request to partial update Poliza partially : {}, {}", id, poliza);
        if (poliza.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, poliza.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!polizaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Poliza> result = polizaRepository
            .findById(poliza.getId())
            .map(existingPoliza -> {
                if (poliza.getFecha() != null) {
                    existingPoliza.setFecha(poliza.getFecha());
                }
                if (poliza.getFechaVencimiento() != null) {
                    existingPoliza.setFechaVencimiento(poliza.getFechaVencimiento());
                }
                if (poliza.getNumeroPoliza() != null) {
                    existingPoliza.setNumeroPoliza(poliza.getNumeroPoliza());
                }
                if (poliza.getCreateByUser() != null) {
                    existingPoliza.setCreateByUser(poliza.getCreateByUser());
                }
                if (poliza.getCreatedOn() != null) {
                    existingPoliza.setCreatedOn(poliza.getCreatedOn());
                }
                if (poliza.getModifyByUser() != null) {
                    existingPoliza.setModifyByUser(poliza.getModifyByUser());
                }
                if (poliza.getModifiedOn() != null) {
                    existingPoliza.setModifiedOn(poliza.getModifiedOn());
                }
                if (poliza.getAuditStatus() != null) {
                    existingPoliza.setAuditStatus(poliza.getAuditStatus());
                }

                return existingPoliza;
            })
            .map(polizaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poliza.getId().toString())
        );
    }

    /**
     * {@code GET  /polizas} : get all the polizas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of polizas in body.
     */
    @GetMapping("/polizas")
    public List<Poliza> getAllPolizas() {
        log.debug("REST request to get all Polizas");
        return polizaRepository.findAll();
    }

    /**
     * {@code GET  /polizas/:id} : get the "id" poliza.
     *
     * @param id the id of the poliza to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poliza, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/polizas/{id}")
    public ResponseEntity<Poliza> getPoliza(@PathVariable Long id) {
        log.debug("REST request to get Poliza : {}", id);
        Optional<Poliza> poliza = polizaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poliza);
    }

    /**
     * {@code DELETE  /polizas/:id} : delete the "id" poliza.
     *
     * @param id the id of the poliza to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/polizas/{id}")
    public ResponseEntity<Void> deletePoliza(@PathVariable Long id) {
        log.debug("REST request to delete Poliza : {}", id);
        polizaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
