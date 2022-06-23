package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ListaServicio;
import com.mycompany.myapp.repository.ListaServicioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ListaServicio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListaServicioResource {

    private final Logger log = LoggerFactory.getLogger(ListaServicioResource.class);

    private static final String ENTITY_NAME = "listaServicio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListaServicioRepository listaServicioRepository;

    public ListaServicioResource(ListaServicioRepository listaServicioRepository) {
        this.listaServicioRepository = listaServicioRepository;
    }

    /**
     * {@code POST  /lista-servicios} : Create a new listaServicio.
     *
     * @param listaServicio the listaServicio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listaServicio, or with status {@code 400 (Bad Request)} if the listaServicio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lista-servicios")
    public ResponseEntity<ListaServicio> createListaServicio(@RequestBody ListaServicio listaServicio) throws URISyntaxException {
        log.debug("REST request to save ListaServicio : {}", listaServicio);
        if (listaServicio.getId() != null) {
            throw new BadRequestAlertException("A new listaServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListaServicio result = listaServicioRepository.save(listaServicio);
        return ResponseEntity
            .created(new URI("/api/lista-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lista-servicios/:id} : Updates an existing listaServicio.
     *
     * @param id the id of the listaServicio to save.
     * @param listaServicio the listaServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaServicio,
     * or with status {@code 400 (Bad Request)} if the listaServicio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listaServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lista-servicios/{id}")
    public ResponseEntity<ListaServicio> updateListaServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListaServicio listaServicio
    ) throws URISyntaxException {
        log.debug("REST request to update ListaServicio : {}, {}", id, listaServicio);
        if (listaServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listaServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listaServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListaServicio result = listaServicioRepository.save(listaServicio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listaServicio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lista-servicios/:id} : Partial updates given fields of an existing listaServicio, field will ignore if it is null
     *
     * @param id the id of the listaServicio to save.
     * @param listaServicio the listaServicio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaServicio,
     * or with status {@code 400 (Bad Request)} if the listaServicio is not valid,
     * or with status {@code 404 (Not Found)} if the listaServicio is not found,
     * or with status {@code 500 (Internal Server Error)} if the listaServicio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lista-servicios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ListaServicio> partialUpdateListaServicio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListaServicio listaServicio
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListaServicio partially : {}, {}", id, listaServicio);
        if (listaServicio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listaServicio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listaServicioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListaServicio> result = listaServicioRepository
            .findById(listaServicio.getId())
            .map(existingListaServicio -> {
                if (listaServicio.getCantidad() != null) {
                    existingListaServicio.setCantidad(listaServicio.getCantidad());
                }
                if (listaServicio.getCreateByUser() != null) {
                    existingListaServicio.setCreateByUser(listaServicio.getCreateByUser());
                }
                if (listaServicio.getCreatedOn() != null) {
                    existingListaServicio.setCreatedOn(listaServicio.getCreatedOn());
                }
                if (listaServicio.getModifyByUser() != null) {
                    existingListaServicio.setModifyByUser(listaServicio.getModifyByUser());
                }
                if (listaServicio.getModifiedOn() != null) {
                    existingListaServicio.setModifiedOn(listaServicio.getModifiedOn());
                }
                if (listaServicio.getAuditStatus() != null) {
                    existingListaServicio.setAuditStatus(listaServicio.getAuditStatus());
                }

                return existingListaServicio;
            })
            .map(listaServicioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listaServicio.getId().toString())
        );
    }

    /**
     * {@code GET  /lista-servicios} : get all the listaServicios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listaServicios in body.
     */
    @GetMapping("/lista-servicios")
    public List<ListaServicio> getAllListaServicios() {
        log.debug("REST request to get all ListaServicios");
        return listaServicioRepository.findAll();
    }

    /**
     * {@code GET  /lista-servicios/:id} : get the "id" listaServicio.
     *
     * @param id the id of the listaServicio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listaServicio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lista-servicios/{id}")
    public ResponseEntity<ListaServicio> getListaServicio(@PathVariable Long id) {
        log.debug("REST request to get ListaServicio : {}", id);
        Optional<ListaServicio> listaServicio = listaServicioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listaServicio);
    }

    /**
     * {@code DELETE  /lista-servicios/:id} : delete the "id" listaServicio.
     *
     * @param id the id of the listaServicio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lista-servicios/{id}")
    public ResponseEntity<Void> deleteListaServicio(@PathVariable Long id) {
        log.debug("REST request to delete ListaServicio : {}", id);
        listaServicioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
