package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AditamentoUnidad;
import com.mycompany.myapp.repository.AditamentoUnidadRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AditamentoUnidad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AditamentoUnidadResource {

    private final Logger log = LoggerFactory.getLogger(AditamentoUnidadResource.class);

    private static final String ENTITY_NAME = "aditamentoUnidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AditamentoUnidadRepository aditamentoUnidadRepository;

    public AditamentoUnidadResource(AditamentoUnidadRepository aditamentoUnidadRepository) {
        this.aditamentoUnidadRepository = aditamentoUnidadRepository;
    }

    /**
     * {@code POST  /aditamento-unidads} : Create a new aditamentoUnidad.
     *
     * @param aditamentoUnidad the aditamentoUnidad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aditamentoUnidad, or with status {@code 400 (Bad Request)} if the aditamentoUnidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aditamento-unidads")
    public ResponseEntity<AditamentoUnidad> createAditamentoUnidad(@RequestBody AditamentoUnidad aditamentoUnidad)
        throws URISyntaxException {
        log.debug("REST request to save AditamentoUnidad : {}", aditamentoUnidad);
        if (aditamentoUnidad.getId() != null) {
            throw new BadRequestAlertException("A new aditamentoUnidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AditamentoUnidad result = aditamentoUnidadRepository.save(aditamentoUnidad);
        return ResponseEntity
            .created(new URI("/api/aditamento-unidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aditamento-unidads/:id} : Updates an existing aditamentoUnidad.
     *
     * @param id the id of the aditamentoUnidad to save.
     * @param aditamentoUnidad the aditamentoUnidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aditamentoUnidad,
     * or with status {@code 400 (Bad Request)} if the aditamentoUnidad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aditamentoUnidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aditamento-unidads/{id}")
    public ResponseEntity<AditamentoUnidad> updateAditamentoUnidad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AditamentoUnidad aditamentoUnidad
    ) throws URISyntaxException {
        log.debug("REST request to update AditamentoUnidad : {}, {}", id, aditamentoUnidad);
        if (aditamentoUnidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aditamentoUnidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aditamentoUnidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AditamentoUnidad result = aditamentoUnidadRepository.save(aditamentoUnidad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aditamentoUnidad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aditamento-unidads/:id} : Partial updates given fields of an existing aditamentoUnidad, field will ignore if it is null
     *
     * @param id the id of the aditamentoUnidad to save.
     * @param aditamentoUnidad the aditamentoUnidad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aditamentoUnidad,
     * or with status {@code 400 (Bad Request)} if the aditamentoUnidad is not valid,
     * or with status {@code 404 (Not Found)} if the aditamentoUnidad is not found,
     * or with status {@code 500 (Internal Server Error)} if the aditamentoUnidad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aditamento-unidads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AditamentoUnidad> partialUpdateAditamentoUnidad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AditamentoUnidad aditamentoUnidad
    ) throws URISyntaxException {
        log.debug("REST request to partial update AditamentoUnidad partially : {}, {}", id, aditamentoUnidad);
        if (aditamentoUnidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aditamentoUnidad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aditamentoUnidadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AditamentoUnidad> result = aditamentoUnidadRepository
            .findById(aditamentoUnidad.getId())
            .map(existingAditamentoUnidad -> {
                if (aditamentoUnidad.getFecha() != null) {
                    existingAditamentoUnidad.setFecha(aditamentoUnidad.getFecha());
                }
                if (aditamentoUnidad.getNumeroSerie() != null) {
                    existingAditamentoUnidad.setNumeroSerie(aditamentoUnidad.getNumeroSerie());
                }
                if (aditamentoUnidad.getCreateByUser() != null) {
                    existingAditamentoUnidad.setCreateByUser(aditamentoUnidad.getCreateByUser());
                }
                if (aditamentoUnidad.getCreatedOn() != null) {
                    existingAditamentoUnidad.setCreatedOn(aditamentoUnidad.getCreatedOn());
                }
                if (aditamentoUnidad.getModifyByUser() != null) {
                    existingAditamentoUnidad.setModifyByUser(aditamentoUnidad.getModifyByUser());
                }
                if (aditamentoUnidad.getModifiedOn() != null) {
                    existingAditamentoUnidad.setModifiedOn(aditamentoUnidad.getModifiedOn());
                }
                if (aditamentoUnidad.getAuditStatus() != null) {
                    existingAditamentoUnidad.setAuditStatus(aditamentoUnidad.getAuditStatus());
                }

                return existingAditamentoUnidad;
            })
            .map(aditamentoUnidadRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aditamentoUnidad.getId().toString())
        );
    }

    /**
     * {@code GET  /aditamento-unidads} : get all the aditamentoUnidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aditamentoUnidads in body.
     */
    @GetMapping("/aditamento-unidads")
    public List<AditamentoUnidad> getAllAditamentoUnidads() {
        log.debug("REST request to get all AditamentoUnidads");
        return aditamentoUnidadRepository.findAll();
    }

    /**
     * {@code GET  /aditamento-unidads/:id} : get the "id" aditamentoUnidad.
     *
     * @param id the id of the aditamentoUnidad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aditamentoUnidad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aditamento-unidads/{id}")
    public ResponseEntity<AditamentoUnidad> getAditamentoUnidad(@PathVariable Long id) {
        log.debug("REST request to get AditamentoUnidad : {}", id);
        Optional<AditamentoUnidad> aditamentoUnidad = aditamentoUnidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aditamentoUnidad);
    }

    /**
     * {@code DELETE  /aditamento-unidads/:id} : delete the "id" aditamentoUnidad.
     *
     * @param id the id of the aditamentoUnidad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aditamento-unidads/{id}")
    public ResponseEntity<Void> deleteAditamentoUnidad(@PathVariable Long id) {
        log.debug("REST request to delete AditamentoUnidad : {}", id);
        aditamentoUnidadRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
