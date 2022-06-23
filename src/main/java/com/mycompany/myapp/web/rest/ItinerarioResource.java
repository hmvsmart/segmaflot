package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Itinerario;
import com.mycompany.myapp.repository.ItinerarioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Itinerario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItinerarioResource {

    private final Logger log = LoggerFactory.getLogger(ItinerarioResource.class);

    private static final String ENTITY_NAME = "itinerario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItinerarioRepository itinerarioRepository;

    public ItinerarioResource(ItinerarioRepository itinerarioRepository) {
        this.itinerarioRepository = itinerarioRepository;
    }

    /**
     * {@code POST  /itinerarios} : Create a new itinerario.
     *
     * @param itinerario the itinerario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itinerario, or with status {@code 400 (Bad Request)} if the itinerario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/itinerarios")
    public ResponseEntity<Itinerario> createItinerario(@RequestBody Itinerario itinerario) throws URISyntaxException {
        log.debug("REST request to save Itinerario : {}", itinerario);
        if (itinerario.getId() != null) {
            throw new BadRequestAlertException("A new itinerario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Itinerario result = itinerarioRepository.save(itinerario);
        return ResponseEntity
            .created(new URI("/api/itinerarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /itinerarios/:id} : Updates an existing itinerario.
     *
     * @param id the id of the itinerario to save.
     * @param itinerario the itinerario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itinerario,
     * or with status {@code 400 (Bad Request)} if the itinerario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itinerario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/itinerarios/{id}")
    public ResponseEntity<Itinerario> updateItinerario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Itinerario itinerario
    ) throws URISyntaxException {
        log.debug("REST request to update Itinerario : {}, {}", id, itinerario);
        if (itinerario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itinerario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itinerarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Itinerario result = itinerarioRepository.save(itinerario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itinerario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /itinerarios/:id} : Partial updates given fields of an existing itinerario, field will ignore if it is null
     *
     * @param id the id of the itinerario to save.
     * @param itinerario the itinerario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itinerario,
     * or with status {@code 400 (Bad Request)} if the itinerario is not valid,
     * or with status {@code 404 (Not Found)} if the itinerario is not found,
     * or with status {@code 500 (Internal Server Error)} if the itinerario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/itinerarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Itinerario> partialUpdateItinerario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Itinerario itinerario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Itinerario partially : {}, {}", id, itinerario);
        if (itinerario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itinerario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itinerarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Itinerario> result = itinerarioRepository
            .findById(itinerario.getId())
            .map(existingItinerario -> {
                if (itinerario.getAccion() != null) {
                    existingItinerario.setAccion(itinerario.getAccion());
                }
                if (itinerario.getFechaHoraEstimada() != null) {
                    existingItinerario.setFechaHoraEstimada(itinerario.getFechaHoraEstimada());
                }
                if (itinerario.getCreateByUser() != null) {
                    existingItinerario.setCreateByUser(itinerario.getCreateByUser());
                }
                if (itinerario.getCreatedOn() != null) {
                    existingItinerario.setCreatedOn(itinerario.getCreatedOn());
                }
                if (itinerario.getModifyByUser() != null) {
                    existingItinerario.setModifyByUser(itinerario.getModifyByUser());
                }
                if (itinerario.getModifiedOn() != null) {
                    existingItinerario.setModifiedOn(itinerario.getModifiedOn());
                }
                if (itinerario.getAuditStatus() != null) {
                    existingItinerario.setAuditStatus(itinerario.getAuditStatus());
                }

                return existingItinerario;
            })
            .map(itinerarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itinerario.getId().toString())
        );
    }

    /**
     * {@code GET  /itinerarios} : get all the itinerarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itinerarios in body.
     */
    @GetMapping("/itinerarios")
    public List<Itinerario> getAllItinerarios() {
        log.debug("REST request to get all Itinerarios");
        return itinerarioRepository.findAll();
    }

    /**
     * {@code GET  /itinerarios/:id} : get the "id" itinerario.
     *
     * @param id the id of the itinerario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itinerario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/itinerarios/{id}")
    public ResponseEntity<Itinerario> getItinerario(@PathVariable Long id) {
        log.debug("REST request to get Itinerario : {}", id);
        Optional<Itinerario> itinerario = itinerarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itinerario);
    }

    /**
     * {@code DELETE  /itinerarios/:id} : delete the "id" itinerario.
     *
     * @param id the id of the itinerario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/itinerarios/{id}")
    public ResponseEntity<Void> deleteItinerario(@PathVariable Long id) {
        log.debug("REST request to delete Itinerario : {}", id);
        itinerarioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
