package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.LicenciaManejo;
import com.mycompany.myapp.repository.LicenciaManejoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.LicenciaManejo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LicenciaManejoResource {

    private final Logger log = LoggerFactory.getLogger(LicenciaManejoResource.class);

    private static final String ENTITY_NAME = "licenciaManejo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenciaManejoRepository licenciaManejoRepository;

    public LicenciaManejoResource(LicenciaManejoRepository licenciaManejoRepository) {
        this.licenciaManejoRepository = licenciaManejoRepository;
    }

    /**
     * {@code POST  /licencia-manejos} : Create a new licenciaManejo.
     *
     * @param licenciaManejo the licenciaManejo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenciaManejo, or with status {@code 400 (Bad Request)} if the licenciaManejo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licencia-manejos")
    public ResponseEntity<LicenciaManejo> createLicenciaManejo(@RequestBody LicenciaManejo licenciaManejo) throws URISyntaxException {
        log.debug("REST request to save LicenciaManejo : {}", licenciaManejo);
        if (licenciaManejo.getId() != null) {
            throw new BadRequestAlertException("A new licenciaManejo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenciaManejo result = licenciaManejoRepository.save(licenciaManejo);
        return ResponseEntity
            .created(new URI("/api/licencia-manejos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licencia-manejos/:id} : Updates an existing licenciaManejo.
     *
     * @param id the id of the licenciaManejo to save.
     * @param licenciaManejo the licenciaManejo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenciaManejo,
     * or with status {@code 400 (Bad Request)} if the licenciaManejo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenciaManejo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licencia-manejos/{id}")
    public ResponseEntity<LicenciaManejo> updateLicenciaManejo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LicenciaManejo licenciaManejo
    ) throws URISyntaxException {
        log.debug("REST request to update LicenciaManejo : {}, {}", id, licenciaManejo);
        if (licenciaManejo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenciaManejo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenciaManejoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LicenciaManejo result = licenciaManejoRepository.save(licenciaManejo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licenciaManejo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /licencia-manejos/:id} : Partial updates given fields of an existing licenciaManejo, field will ignore if it is null
     *
     * @param id the id of the licenciaManejo to save.
     * @param licenciaManejo the licenciaManejo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenciaManejo,
     * or with status {@code 400 (Bad Request)} if the licenciaManejo is not valid,
     * or with status {@code 404 (Not Found)} if the licenciaManejo is not found,
     * or with status {@code 500 (Internal Server Error)} if the licenciaManejo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/licencia-manejos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LicenciaManejo> partialUpdateLicenciaManejo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LicenciaManejo licenciaManejo
    ) throws URISyntaxException {
        log.debug("REST request to partial update LicenciaManejo partially : {}, {}", id, licenciaManejo);
        if (licenciaManejo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenciaManejo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenciaManejoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LicenciaManejo> result = licenciaManejoRepository
            .findById(licenciaManejo.getId())
            .map(existingLicenciaManejo -> {
                if (licenciaManejo.getFecha() != null) {
                    existingLicenciaManejo.setFecha(licenciaManejo.getFecha());
                }
                if (licenciaManejo.getTipo() != null) {
                    existingLicenciaManejo.setTipo(licenciaManejo.getTipo());
                }
                if (licenciaManejo.getStatus() != null) {
                    existingLicenciaManejo.setStatus(licenciaManejo.getStatus());
                }
                if (licenciaManejo.getFechaExpiracion() != null) {
                    existingLicenciaManejo.setFechaExpiracion(licenciaManejo.getFechaExpiracion());
                }
                if (licenciaManejo.getCreateByUser() != null) {
                    existingLicenciaManejo.setCreateByUser(licenciaManejo.getCreateByUser());
                }
                if (licenciaManejo.getCreatedOn() != null) {
                    existingLicenciaManejo.setCreatedOn(licenciaManejo.getCreatedOn());
                }
                if (licenciaManejo.getModifyByUser() != null) {
                    existingLicenciaManejo.setModifyByUser(licenciaManejo.getModifyByUser());
                }
                if (licenciaManejo.getModifiedOn() != null) {
                    existingLicenciaManejo.setModifiedOn(licenciaManejo.getModifiedOn());
                }
                if (licenciaManejo.getAuditStatus() != null) {
                    existingLicenciaManejo.setAuditStatus(licenciaManejo.getAuditStatus());
                }

                return existingLicenciaManejo;
            })
            .map(licenciaManejoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licenciaManejo.getId().toString())
        );
    }

    /**
     * {@code GET  /licencia-manejos} : get all the licenciaManejos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenciaManejos in body.
     */
    @GetMapping("/licencia-manejos")
    public List<LicenciaManejo> getAllLicenciaManejos() {
        log.debug("REST request to get all LicenciaManejos");
        return licenciaManejoRepository.findAll();
    }

    /**
     * {@code GET  /licencia-manejos/:id} : get the "id" licenciaManejo.
     *
     * @param id the id of the licenciaManejo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenciaManejo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licencia-manejos/{id}")
    public ResponseEntity<LicenciaManejo> getLicenciaManejo(@PathVariable Long id) {
        log.debug("REST request to get LicenciaManejo : {}", id);
        Optional<LicenciaManejo> licenciaManejo = licenciaManejoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(licenciaManejo);
    }

    /**
     * {@code DELETE  /licencia-manejos/:id} : delete the "id" licenciaManejo.
     *
     * @param id the id of the licenciaManejo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licencia-manejos/{id}")
    public ResponseEntity<Void> deleteLicenciaManejo(@PathVariable Long id) {
        log.debug("REST request to delete LicenciaManejo : {}", id);
        licenciaManejoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
