package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Cita;
import com.mycompany.myapp.repository.CitaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Cita}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CitaResource {

    private final Logger log = LoggerFactory.getLogger(CitaResource.class);

    private static final String ENTITY_NAME = "cita";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitaRepository citaRepository;

    public CitaResource(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    /**
     * {@code POST  /citas} : Create a new cita.
     *
     * @param cita the cita to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cita, or with status {@code 400 (Bad Request)} if the cita has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citas")
    public ResponseEntity<Cita> createCita(@RequestBody Cita cita) throws URISyntaxException {
        log.debug("REST request to save Cita : {}", cita);
        if (cita.getId() != null) {
            throw new BadRequestAlertException("A new cita cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cita result = citaRepository.save(cita);
        return ResponseEntity
            .created(new URI("/api/citas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citas/:id} : Updates an existing cita.
     *
     * @param id the id of the cita to save.
     * @param cita the cita to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cita,
     * or with status {@code 400 (Bad Request)} if the cita is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cita couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citas/{id}")
    public ResponseEntity<Cita> updateCita(@PathVariable(value = "id", required = false) final Long id, @RequestBody Cita cita)
        throws URISyntaxException {
        log.debug("REST request to update Cita : {}, {}", id, cita);
        if (cita.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cita.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cita result = citaRepository.save(cita);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cita.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /citas/:id} : Partial updates given fields of an existing cita, field will ignore if it is null
     *
     * @param id the id of the cita to save.
     * @param cita the cita to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cita,
     * or with status {@code 400 (Bad Request)} if the cita is not valid,
     * or with status {@code 404 (Not Found)} if the cita is not found,
     * or with status {@code 500 (Internal Server Error)} if the cita couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/citas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cita> partialUpdateCita(@PathVariable(value = "id", required = false) final Long id, @RequestBody Cita cita)
        throws URISyntaxException {
        log.debug("REST request to partial update Cita partially : {}, {}", id, cita);
        if (cita.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cita.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cita> result = citaRepository
            .findById(cita.getId())
            .map(existingCita -> {
                if (cita.getFecha() != null) {
                    existingCita.setFecha(cita.getFecha());
                }
                if (cita.getHoraInicio() != null) {
                    existingCita.setHoraInicio(cita.getHoraInicio());
                }
                if (cita.getHoraFin() != null) {
                    existingCita.setHoraFin(cita.getHoraFin());
                }
                if (cita.getArea() != null) {
                    existingCita.setArea(cita.getArea());
                }
                if (cita.getIdentificador() != null) {
                    existingCita.setIdentificador(cita.getIdentificador());
                }
                if (cita.getCreateByUser() != null) {
                    existingCita.setCreateByUser(cita.getCreateByUser());
                }
                if (cita.getCreatedOn() != null) {
                    existingCita.setCreatedOn(cita.getCreatedOn());
                }
                if (cita.getModifyByUser() != null) {
                    existingCita.setModifyByUser(cita.getModifyByUser());
                }
                if (cita.getModifiedOn() != null) {
                    existingCita.setModifiedOn(cita.getModifiedOn());
                }
                if (cita.getAuditStatus() != null) {
                    existingCita.setAuditStatus(cita.getAuditStatus());
                }

                return existingCita;
            })
            .map(citaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cita.getId().toString())
        );
    }

    /**
     * {@code GET  /citas} : get all the citas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citas in body.
     */
    @GetMapping("/citas")
    public List<Cita> getAllCitas() {
        log.debug("REST request to get all Citas");
        return citaRepository.findAll();
    }

    /**
     * {@code GET  /citas/:id} : get the "id" cita.
     *
     * @param id the id of the cita to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cita, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citas/{id}")
    public ResponseEntity<Cita> getCita(@PathVariable Long id) {
        log.debug("REST request to get Cita : {}", id);
        Optional<Cita> cita = citaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cita);
    }

    /**
     * {@code DELETE  /citas/:id} : delete the "id" cita.
     *
     * @param id the id of the cita to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citas/{id}")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        log.debug("REST request to delete Cita : {}", id);
        citaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
