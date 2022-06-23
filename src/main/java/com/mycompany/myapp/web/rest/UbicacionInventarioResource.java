package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.UbicacionInventario;
import com.mycompany.myapp.repository.UbicacionInventarioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UbicacionInventario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UbicacionInventarioResource {

    private final Logger log = LoggerFactory.getLogger(UbicacionInventarioResource.class);

    private static final String ENTITY_NAME = "ubicacionInventario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UbicacionInventarioRepository ubicacionInventarioRepository;

    public UbicacionInventarioResource(UbicacionInventarioRepository ubicacionInventarioRepository) {
        this.ubicacionInventarioRepository = ubicacionInventarioRepository;
    }

    /**
     * {@code POST  /ubicacion-inventarios} : Create a new ubicacionInventario.
     *
     * @param ubicacionInventario the ubicacionInventario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ubicacionInventario, or with status {@code 400 (Bad Request)} if the ubicacionInventario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ubicacion-inventarios")
    public ResponseEntity<UbicacionInventario> createUbicacionInventario(@RequestBody UbicacionInventario ubicacionInventario)
        throws URISyntaxException {
        log.debug("REST request to save UbicacionInventario : {}", ubicacionInventario);
        if (ubicacionInventario.getId() != null) {
            throw new BadRequestAlertException("A new ubicacionInventario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UbicacionInventario result = ubicacionInventarioRepository.save(ubicacionInventario);
        return ResponseEntity
            .created(new URI("/api/ubicacion-inventarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ubicacion-inventarios/:id} : Updates an existing ubicacionInventario.
     *
     * @param id the id of the ubicacionInventario to save.
     * @param ubicacionInventario the ubicacionInventario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ubicacionInventario,
     * or with status {@code 400 (Bad Request)} if the ubicacionInventario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ubicacionInventario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ubicacion-inventarios/{id}")
    public ResponseEntity<UbicacionInventario> updateUbicacionInventario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UbicacionInventario ubicacionInventario
    ) throws URISyntaxException {
        log.debug("REST request to update UbicacionInventario : {}, {}", id, ubicacionInventario);
        if (ubicacionInventario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ubicacionInventario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ubicacionInventarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UbicacionInventario result = ubicacionInventarioRepository.save(ubicacionInventario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ubicacionInventario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ubicacion-inventarios/:id} : Partial updates given fields of an existing ubicacionInventario, field will ignore if it is null
     *
     * @param id the id of the ubicacionInventario to save.
     * @param ubicacionInventario the ubicacionInventario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ubicacionInventario,
     * or with status {@code 400 (Bad Request)} if the ubicacionInventario is not valid,
     * or with status {@code 404 (Not Found)} if the ubicacionInventario is not found,
     * or with status {@code 500 (Internal Server Error)} if the ubicacionInventario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ubicacion-inventarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UbicacionInventario> partialUpdateUbicacionInventario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UbicacionInventario ubicacionInventario
    ) throws URISyntaxException {
        log.debug("REST request to partial update UbicacionInventario partially : {}, {}", id, ubicacionInventario);
        if (ubicacionInventario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ubicacionInventario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ubicacionInventarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UbicacionInventario> result = ubicacionInventarioRepository
            .findById(ubicacionInventario.getId())
            .map(existingUbicacionInventario -> {
                if (ubicacionInventario.getStatus() != null) {
                    existingUbicacionInventario.setStatus(ubicacionInventario.getStatus());
                }
                if (ubicacionInventario.getCreateByUser() != null) {
                    existingUbicacionInventario.setCreateByUser(ubicacionInventario.getCreateByUser());
                }
                if (ubicacionInventario.getCreatedOn() != null) {
                    existingUbicacionInventario.setCreatedOn(ubicacionInventario.getCreatedOn());
                }
                if (ubicacionInventario.getModifyByUser() != null) {
                    existingUbicacionInventario.setModifyByUser(ubicacionInventario.getModifyByUser());
                }
                if (ubicacionInventario.getModifiedOn() != null) {
                    existingUbicacionInventario.setModifiedOn(ubicacionInventario.getModifiedOn());
                }
                if (ubicacionInventario.getAuditStatus() != null) {
                    existingUbicacionInventario.setAuditStatus(ubicacionInventario.getAuditStatus());
                }

                return existingUbicacionInventario;
            })
            .map(ubicacionInventarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ubicacionInventario.getId().toString())
        );
    }

    /**
     * {@code GET  /ubicacion-inventarios} : get all the ubicacionInventarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ubicacionInventarios in body.
     */
    @GetMapping("/ubicacion-inventarios")
    public List<UbicacionInventario> getAllUbicacionInventarios() {
        log.debug("REST request to get all UbicacionInventarios");
        return ubicacionInventarioRepository.findAll();
    }

    /**
     * {@code GET  /ubicacion-inventarios/:id} : get the "id" ubicacionInventario.
     *
     * @param id the id of the ubicacionInventario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ubicacionInventario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ubicacion-inventarios/{id}")
    public ResponseEntity<UbicacionInventario> getUbicacionInventario(@PathVariable Long id) {
        log.debug("REST request to get UbicacionInventario : {}", id);
        Optional<UbicacionInventario> ubicacionInventario = ubicacionInventarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ubicacionInventario);
    }

    /**
     * {@code DELETE  /ubicacion-inventarios/:id} : delete the "id" ubicacionInventario.
     *
     * @param id the id of the ubicacionInventario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ubicacion-inventarios/{id}")
    public ResponseEntity<Void> deleteUbicacionInventario(@PathVariable Long id) {
        log.debug("REST request to delete UbicacionInventario : {}", id);
        ubicacionInventarioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
