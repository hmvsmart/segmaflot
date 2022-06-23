package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Viaje;
import com.mycompany.myapp.repository.ViajeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Viaje}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ViajeResource {

    private final Logger log = LoggerFactory.getLogger(ViajeResource.class);

    private static final String ENTITY_NAME = "viaje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViajeRepository viajeRepository;

    public ViajeResource(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
    }

    /**
     * {@code POST  /viajes} : Create a new viaje.
     *
     * @param viaje the viaje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viaje, or with status {@code 400 (Bad Request)} if the viaje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/viajes")
    public ResponseEntity<Viaje> createViaje(@RequestBody Viaje viaje) throws URISyntaxException {
        log.debug("REST request to save Viaje : {}", viaje);
        if (viaje.getId() != null) {
            throw new BadRequestAlertException("A new viaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Viaje result = viajeRepository.save(viaje);
        return ResponseEntity
            .created(new URI("/api/viajes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /viajes/:id} : Updates an existing viaje.
     *
     * @param id the id of the viaje to save.
     * @param viaje the viaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viaje,
     * or with status {@code 400 (Bad Request)} if the viaje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/viajes/{id}")
    public ResponseEntity<Viaje> updateViaje(@PathVariable(value = "id", required = false) final Long id, @RequestBody Viaje viaje)
        throws URISyntaxException {
        log.debug("REST request to update Viaje : {}, {}", id, viaje);
        if (viaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viaje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Viaje result = viajeRepository.save(viaje);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viaje.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /viajes/:id} : Partial updates given fields of an existing viaje, field will ignore if it is null
     *
     * @param id the id of the viaje to save.
     * @param viaje the viaje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viaje,
     * or with status {@code 400 (Bad Request)} if the viaje is not valid,
     * or with status {@code 404 (Not Found)} if the viaje is not found,
     * or with status {@code 500 (Internal Server Error)} if the viaje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/viajes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Viaje> partialUpdateViaje(@PathVariable(value = "id", required = false) final Long id, @RequestBody Viaje viaje)
        throws URISyntaxException {
        log.debug("REST request to partial update Viaje partially : {}, {}", id, viaje);
        if (viaje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viaje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Viaje> result = viajeRepository
            .findById(viaje.getId())
            .map(existingViaje -> {
                if (viaje.getFecha() != null) {
                    existingViaje.setFecha(viaje.getFecha());
                }
                if (viaje.getFechaInicio() != null) {
                    existingViaje.setFechaInicio(viaje.getFechaInicio());
                }
                if (viaje.getFechaFin() != null) {
                    existingViaje.setFechaFin(viaje.getFechaFin());
                }
                if (viaje.getHoraEmbarque() != null) {
                    existingViaje.setHoraEmbarque(viaje.getHoraEmbarque());
                }
                if (viaje.getTipoCarga() != null) {
                    existingViaje.setTipoCarga(viaje.getTipoCarga());
                }
                if (viaje.getPesoNeto() != null) {
                    existingViaje.setPesoNeto(viaje.getPesoNeto());
                }
                if (viaje.getComentarios() != null) {
                    existingViaje.setComentarios(viaje.getComentarios());
                }
                if (viaje.getComentariosContentType() != null) {
                    existingViaje.setComentariosContentType(viaje.getComentariosContentType());
                }
                if (viaje.getStatus() != null) {
                    existingViaje.setStatus(viaje.getStatus());
                }
                if (viaje.getCreateByUser() != null) {
                    existingViaje.setCreateByUser(viaje.getCreateByUser());
                }
                if (viaje.getCreatedOn() != null) {
                    existingViaje.setCreatedOn(viaje.getCreatedOn());
                }
                if (viaje.getModifyByUser() != null) {
                    existingViaje.setModifyByUser(viaje.getModifyByUser());
                }
                if (viaje.getModifiedOn() != null) {
                    existingViaje.setModifiedOn(viaje.getModifiedOn());
                }
                if (viaje.getAuditStatus() != null) {
                    existingViaje.setAuditStatus(viaje.getAuditStatus());
                }

                return existingViaje;
            })
            .map(viajeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viaje.getId().toString())
        );
    }

    /**
     * {@code GET  /viajes} : get all the viajes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viajes in body.
     */
    @GetMapping("/viajes")
    public List<Viaje> getAllViajes() {
        log.debug("REST request to get all Viajes");
        return viajeRepository.findAll();
    }

    /**
     * {@code GET  /viajes/:id} : get the "id" viaje.
     *
     * @param id the id of the viaje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viaje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/viajes/{id}")
    public ResponseEntity<Viaje> getViaje(@PathVariable Long id) {
        log.debug("REST request to get Viaje : {}", id);
        Optional<Viaje> viaje = viajeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(viaje);
    }

    /**
     * {@code DELETE  /viajes/:id} : delete the "id" viaje.
     *
     * @param id the id of the viaje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/viajes/{id}")
    public ResponseEntity<Void> deleteViaje(@PathVariable Long id) {
        log.debug("REST request to delete Viaje : {}", id);
        viajeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
