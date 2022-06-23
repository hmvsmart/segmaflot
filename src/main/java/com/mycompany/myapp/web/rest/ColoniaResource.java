package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Colonia;
import com.mycompany.myapp.repository.ColoniaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Colonia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ColoniaResource {

    private final Logger log = LoggerFactory.getLogger(ColoniaResource.class);

    private static final String ENTITY_NAME = "colonia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColoniaRepository coloniaRepository;

    public ColoniaResource(ColoniaRepository coloniaRepository) {
        this.coloniaRepository = coloniaRepository;
    }

    /**
     * {@code POST  /colonias} : Create a new colonia.
     *
     * @param colonia the colonia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colonia, or with status {@code 400 (Bad Request)} if the colonia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colonias")
    public ResponseEntity<Colonia> createColonia(@RequestBody Colonia colonia) throws URISyntaxException {
        log.debug("REST request to save Colonia : {}", colonia);
        if (colonia.getId() != null) {
            throw new BadRequestAlertException("A new colonia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Colonia result = coloniaRepository.save(colonia);
        return ResponseEntity
            .created(new URI("/api/colonias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colonias/:id} : Updates an existing colonia.
     *
     * @param id the id of the colonia to save.
     * @param colonia the colonia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colonia,
     * or with status {@code 400 (Bad Request)} if the colonia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colonia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colonias/{id}")
    public ResponseEntity<Colonia> updateColonia(@PathVariable(value = "id", required = false) final Long id, @RequestBody Colonia colonia)
        throws URISyntaxException {
        log.debug("REST request to update Colonia : {}, {}", id, colonia);
        if (colonia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colonia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coloniaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Colonia result = coloniaRepository.save(colonia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colonia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colonias/:id} : Partial updates given fields of an existing colonia, field will ignore if it is null
     *
     * @param id the id of the colonia to save.
     * @param colonia the colonia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colonia,
     * or with status {@code 400 (Bad Request)} if the colonia is not valid,
     * or with status {@code 404 (Not Found)} if the colonia is not found,
     * or with status {@code 500 (Internal Server Error)} if the colonia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/colonias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Colonia> partialUpdateColonia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Colonia colonia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Colonia partially : {}, {}", id, colonia);
        if (colonia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, colonia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coloniaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Colonia> result = coloniaRepository
            .findById(colonia.getId())
            .map(existingColonia -> {
                if (colonia.getNombre() != null) {
                    existingColonia.setNombre(colonia.getNombre());
                }

                return existingColonia;
            })
            .map(coloniaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colonia.getId().toString())
        );
    }

    /**
     * {@code GET  /colonias} : get all the colonias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colonias in body.
     */
    @GetMapping("/colonias")
    public List<Colonia> getAllColonias() {
        log.debug("REST request to get all Colonias");
        return coloniaRepository.findAll();
    }

    /**
     * {@code GET  /colonias/:id} : get the "id" colonia.
     *
     * @param id the id of the colonia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colonia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colonias/{id}")
    public ResponseEntity<Colonia> getColonia(@PathVariable Long id) {
        log.debug("REST request to get Colonia : {}", id);
        Optional<Colonia> colonia = coloniaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(colonia);
    }

    /**
     * {@code DELETE  /colonias/:id} : delete the "id" colonia.
     *
     * @param id the id of the colonia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colonias/{id}")
    public ResponseEntity<Void> deleteColonia(@PathVariable Long id) {
        log.debug("REST request to delete Colonia : {}", id);
        coloniaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
