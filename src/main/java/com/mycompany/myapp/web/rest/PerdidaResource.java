package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Perdida;
import com.mycompany.myapp.repository.PerdidaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Perdida}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PerdidaResource {

    private final Logger log = LoggerFactory.getLogger(PerdidaResource.class);

    private static final String ENTITY_NAME = "perdida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerdidaRepository perdidaRepository;

    public PerdidaResource(PerdidaRepository perdidaRepository) {
        this.perdidaRepository = perdidaRepository;
    }

    /**
     * {@code POST  /perdidas} : Create a new perdida.
     *
     * @param perdida the perdida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perdida, or with status {@code 400 (Bad Request)} if the perdida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/perdidas")
    public ResponseEntity<Perdida> createPerdida(@RequestBody Perdida perdida) throws URISyntaxException {
        log.debug("REST request to save Perdida : {}", perdida);
        if (perdida.getId() != null) {
            throw new BadRequestAlertException("A new perdida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Perdida result = perdidaRepository.save(perdida);
        return ResponseEntity
            .created(new URI("/api/perdidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /perdidas/:id} : Updates an existing perdida.
     *
     * @param id the id of the perdida to save.
     * @param perdida the perdida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perdida,
     * or with status {@code 400 (Bad Request)} if the perdida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perdida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/perdidas/{id}")
    public ResponseEntity<Perdida> updatePerdida(@PathVariable(value = "id", required = false) final Long id, @RequestBody Perdida perdida)
        throws URISyntaxException {
        log.debug("REST request to update Perdida : {}, {}", id, perdida);
        if (perdida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perdida.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perdidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Perdida result = perdidaRepository.save(perdida);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perdida.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /perdidas/:id} : Partial updates given fields of an existing perdida, field will ignore if it is null
     *
     * @param id the id of the perdida to save.
     * @param perdida the perdida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perdida,
     * or with status {@code 400 (Bad Request)} if the perdida is not valid,
     * or with status {@code 404 (Not Found)} if the perdida is not found,
     * or with status {@code 500 (Internal Server Error)} if the perdida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/perdidas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Perdida> partialUpdatePerdida(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Perdida perdida
    ) throws URISyntaxException {
        log.debug("REST request to partial update Perdida partially : {}, {}", id, perdida);
        if (perdida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perdida.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perdidaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Perdida> result = perdidaRepository
            .findById(perdida.getId())
            .map(existingPerdida -> {
                if (perdida.getFecha() != null) {
                    existingPerdida.setFecha(perdida.getFecha());
                }
                if (perdida.getCantidad() != null) {
                    existingPerdida.setCantidad(perdida.getCantidad());
                }
                if (perdida.getObservaciones() != null) {
                    existingPerdida.setObservaciones(perdida.getObservaciones());
                }
                if (perdida.getObservacionesContentType() != null) {
                    existingPerdida.setObservacionesContentType(perdida.getObservacionesContentType());
                }

                return existingPerdida;
            })
            .map(perdidaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perdida.getId().toString())
        );
    }

    /**
     * {@code GET  /perdidas} : get all the perdidas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perdidas in body.
     */
    @GetMapping("/perdidas")
    public List<Perdida> getAllPerdidas() {
        log.debug("REST request to get all Perdidas");
        return perdidaRepository.findAll();
    }

    /**
     * {@code GET  /perdidas/:id} : get the "id" perdida.
     *
     * @param id the id of the perdida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perdida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/perdidas/{id}")
    public ResponseEntity<Perdida> getPerdida(@PathVariable Long id) {
        log.debug("REST request to get Perdida : {}", id);
        Optional<Perdida> perdida = perdidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(perdida);
    }

    /**
     * {@code DELETE  /perdidas/:id} : delete the "id" perdida.
     *
     * @param id the id of the perdida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/perdidas/{id}")
    public ResponseEntity<Void> deletePerdida(@PathVariable Long id) {
        log.debug("REST request to delete Perdida : {}", id);
        perdidaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
