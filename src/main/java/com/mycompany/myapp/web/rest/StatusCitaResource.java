package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.StatusCita;
import com.mycompany.myapp.repository.StatusCitaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.StatusCita}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StatusCitaResource {

    private final Logger log = LoggerFactory.getLogger(StatusCitaResource.class);

    private static final String ENTITY_NAME = "statusCita";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusCitaRepository statusCitaRepository;

    public StatusCitaResource(StatusCitaRepository statusCitaRepository) {
        this.statusCitaRepository = statusCitaRepository;
    }

    /**
     * {@code POST  /status-citas} : Create a new statusCita.
     *
     * @param statusCita the statusCita to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusCita, or with status {@code 400 (Bad Request)} if the statusCita has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-citas")
    public ResponseEntity<StatusCita> createStatusCita(@RequestBody StatusCita statusCita) throws URISyntaxException {
        log.debug("REST request to save StatusCita : {}", statusCita);
        if (statusCita.getId() != null) {
            throw new BadRequestAlertException("A new statusCita cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusCita result = statusCitaRepository.save(statusCita);
        return ResponseEntity
            .created(new URI("/api/status-citas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-citas/:id} : Updates an existing statusCita.
     *
     * @param id the id of the statusCita to save.
     * @param statusCita the statusCita to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusCita,
     * or with status {@code 400 (Bad Request)} if the statusCita is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusCita couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-citas/{id}")
    public ResponseEntity<StatusCita> updateStatusCita(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatusCita statusCita
    ) throws URISyntaxException {
        log.debug("REST request to update StatusCita : {}, {}", id, statusCita);
        if (statusCita.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusCita.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusCitaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusCita result = statusCitaRepository.save(statusCita);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusCita.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /status-citas/:id} : Partial updates given fields of an existing statusCita, field will ignore if it is null
     *
     * @param id the id of the statusCita to save.
     * @param statusCita the statusCita to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusCita,
     * or with status {@code 400 (Bad Request)} if the statusCita is not valid,
     * or with status {@code 404 (Not Found)} if the statusCita is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusCita couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/status-citas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatusCita> partialUpdateStatusCita(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatusCita statusCita
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatusCita partially : {}, {}", id, statusCita);
        if (statusCita.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusCita.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusCitaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusCita> result = statusCitaRepository
            .findById(statusCita.getId())
            .map(existingStatusCita -> {
                if (statusCita.getFecha() != null) {
                    existingStatusCita.setFecha(statusCita.getFecha());
                }
                if (statusCita.getStatus() != null) {
                    existingStatusCita.setStatus(statusCita.getStatus());
                }
                if (statusCita.getCreateByUser() != null) {
                    existingStatusCita.setCreateByUser(statusCita.getCreateByUser());
                }
                if (statusCita.getCreatedOn() != null) {
                    existingStatusCita.setCreatedOn(statusCita.getCreatedOn());
                }
                if (statusCita.getModifyByUser() != null) {
                    existingStatusCita.setModifyByUser(statusCita.getModifyByUser());
                }
                if (statusCita.getModifiedOn() != null) {
                    existingStatusCita.setModifiedOn(statusCita.getModifiedOn());
                }
                if (statusCita.getAuditStatus() != null) {
                    existingStatusCita.setAuditStatus(statusCita.getAuditStatus());
                }

                return existingStatusCita;
            })
            .map(statusCitaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusCita.getId().toString())
        );
    }

    /**
     * {@code GET  /status-citas} : get all the statusCitas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusCitas in body.
     */
    @GetMapping("/status-citas")
    public List<StatusCita> getAllStatusCitas() {
        log.debug("REST request to get all StatusCitas");
        return statusCitaRepository.findAll();
    }

    /**
     * {@code GET  /status-citas/:id} : get the "id" statusCita.
     *
     * @param id the id of the statusCita to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusCita, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-citas/{id}")
    public ResponseEntity<StatusCita> getStatusCita(@PathVariable Long id) {
        log.debug("REST request to get StatusCita : {}", id);
        Optional<StatusCita> statusCita = statusCitaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(statusCita);
    }

    /**
     * {@code DELETE  /status-citas/:id} : delete the "id" statusCita.
     *
     * @param id the id of the statusCita to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-citas/{id}")
    public ResponseEntity<Void> deleteStatusCita(@PathVariable Long id) {
        log.debug("REST request to delete StatusCita : {}", id);
        statusCitaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
