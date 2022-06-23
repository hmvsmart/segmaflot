package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Estante;
import com.mycompany.myapp.repository.EstanteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Estante}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstanteResource {

    private final Logger log = LoggerFactory.getLogger(EstanteResource.class);

    private static final String ENTITY_NAME = "estante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstanteRepository estanteRepository;

    public EstanteResource(EstanteRepository estanteRepository) {
        this.estanteRepository = estanteRepository;
    }

    /**
     * {@code POST  /estantes} : Create a new estante.
     *
     * @param estante the estante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estante, or with status {@code 400 (Bad Request)} if the estante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estantes")
    public ResponseEntity<Estante> createEstante(@RequestBody Estante estante) throws URISyntaxException {
        log.debug("REST request to save Estante : {}", estante);
        if (estante.getId() != null) {
            throw new BadRequestAlertException("A new estante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estante result = estanteRepository.save(estante);
        return ResponseEntity
            .created(new URI("/api/estantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estantes/:id} : Updates an existing estante.
     *
     * @param id the id of the estante to save.
     * @param estante the estante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estante,
     * or with status {@code 400 (Bad Request)} if the estante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estantes/{id}")
    public ResponseEntity<Estante> updateEstante(@PathVariable(value = "id", required = false) final Long id, @RequestBody Estante estante)
        throws URISyntaxException {
        log.debug("REST request to update Estante : {}, {}", id, estante);
        if (estante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Estante result = estanteRepository.save(estante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estantes/:id} : Partial updates given fields of an existing estante, field will ignore if it is null
     *
     * @param id the id of the estante to save.
     * @param estante the estante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estante,
     * or with status {@code 400 (Bad Request)} if the estante is not valid,
     * or with status {@code 404 (Not Found)} if the estante is not found,
     * or with status {@code 500 (Internal Server Error)} if the estante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Estante> partialUpdateEstante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Estante estante
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estante partially : {}, {}", id, estante);
        if (estante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estante> result = estanteRepository
            .findById(estante.getId())
            .map(existingEstante -> {
                if (estante.getNombre() != null) {
                    existingEstante.setNombre(estante.getNombre());
                }
                if (estante.getMaterial() != null) {
                    existingEstante.setMaterial(estante.getMaterial());
                }
                if (estante.getColor() != null) {
                    existingEstante.setColor(estante.getColor());
                }
                if (estante.getDescripcion() != null) {
                    existingEstante.setDescripcion(estante.getDescripcion());
                }
                if (estante.getCreateByUser() != null) {
                    existingEstante.setCreateByUser(estante.getCreateByUser());
                }
                if (estante.getCreatedOn() != null) {
                    existingEstante.setCreatedOn(estante.getCreatedOn());
                }
                if (estante.getModifyByUser() != null) {
                    existingEstante.setModifyByUser(estante.getModifyByUser());
                }
                if (estante.getModifiedOn() != null) {
                    existingEstante.setModifiedOn(estante.getModifiedOn());
                }
                if (estante.getAuditStatus() != null) {
                    existingEstante.setAuditStatus(estante.getAuditStatus());
                }

                return existingEstante;
            })
            .map(estanteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estante.getId().toString())
        );
    }

    /**
     * {@code GET  /estantes} : get all the estantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estantes in body.
     */
    @GetMapping("/estantes")
    public List<Estante> getAllEstantes() {
        log.debug("REST request to get all Estantes");
        return estanteRepository.findAll();
    }

    /**
     * {@code GET  /estantes/:id} : get the "id" estante.
     *
     * @param id the id of the estante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estantes/{id}")
    public ResponseEntity<Estante> getEstante(@PathVariable Long id) {
        log.debug("REST request to get Estante : {}", id);
        Optional<Estante> estante = estanteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estante);
    }

    /**
     * {@code DELETE  /estantes/:id} : delete the "id" estante.
     *
     * @param id the id of the estante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estantes/{id}")
    public ResponseEntity<Void> deleteEstante(@PathVariable Long id) {
        log.debug("REST request to delete Estante : {}", id);
        estanteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
