package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Aditamento;
import com.mycompany.myapp.repository.AditamentoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Aditamento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AditamentoResource {

    private final Logger log = LoggerFactory.getLogger(AditamentoResource.class);

    private static final String ENTITY_NAME = "aditamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AditamentoRepository aditamentoRepository;

    public AditamentoResource(AditamentoRepository aditamentoRepository) {
        this.aditamentoRepository = aditamentoRepository;
    }

    /**
     * {@code POST  /aditamentos} : Create a new aditamento.
     *
     * @param aditamento the aditamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aditamento, or with status {@code 400 (Bad Request)} if the aditamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aditamentos")
    public ResponseEntity<Aditamento> createAditamento(@RequestBody Aditamento aditamento) throws URISyntaxException {
        log.debug("REST request to save Aditamento : {}", aditamento);
        if (aditamento.getId() != null) {
            throw new BadRequestAlertException("A new aditamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aditamento result = aditamentoRepository.save(aditamento);
        return ResponseEntity
            .created(new URI("/api/aditamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aditamentos/:id} : Updates an existing aditamento.
     *
     * @param id the id of the aditamento to save.
     * @param aditamento the aditamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aditamento,
     * or with status {@code 400 (Bad Request)} if the aditamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aditamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aditamentos/{id}")
    public ResponseEntity<Aditamento> updateAditamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aditamento aditamento
    ) throws URISyntaxException {
        log.debug("REST request to update Aditamento : {}, {}", id, aditamento);
        if (aditamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aditamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aditamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Aditamento result = aditamentoRepository.save(aditamento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aditamento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /aditamentos/:id} : Partial updates given fields of an existing aditamento, field will ignore if it is null
     *
     * @param id the id of the aditamento to save.
     * @param aditamento the aditamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aditamento,
     * or with status {@code 400 (Bad Request)} if the aditamento is not valid,
     * or with status {@code 404 (Not Found)} if the aditamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the aditamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/aditamentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Aditamento> partialUpdateAditamento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Aditamento aditamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Aditamento partially : {}, {}", id, aditamento);
        if (aditamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aditamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aditamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Aditamento> result = aditamentoRepository
            .findById(aditamento.getId())
            .map(existingAditamento -> {
                if (aditamento.getNombre() != null) {
                    existingAditamento.setNombre(aditamento.getNombre());
                }
                if (aditamento.getNumeroSerie() != null) {
                    existingAditamento.setNumeroSerie(aditamento.getNumeroSerie());
                }
                if (aditamento.getDescripcion() != null) {
                    existingAditamento.setDescripcion(aditamento.getDescripcion());
                }
                if (aditamento.getDescripcionContentType() != null) {
                    existingAditamento.setDescripcionContentType(aditamento.getDescripcionContentType());
                }
                if (aditamento.getCreateByUser() != null) {
                    existingAditamento.setCreateByUser(aditamento.getCreateByUser());
                }
                if (aditamento.getCreatedOn() != null) {
                    existingAditamento.setCreatedOn(aditamento.getCreatedOn());
                }
                if (aditamento.getModifyByUser() != null) {
                    existingAditamento.setModifyByUser(aditamento.getModifyByUser());
                }
                if (aditamento.getModifiedOn() != null) {
                    existingAditamento.setModifiedOn(aditamento.getModifiedOn());
                }
                if (aditamento.getAuditStatus() != null) {
                    existingAditamento.setAuditStatus(aditamento.getAuditStatus());
                }

                return existingAditamento;
            })
            .map(aditamentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aditamento.getId().toString())
        );
    }

    /**
     * {@code GET  /aditamentos} : get all the aditamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aditamentos in body.
     */
    @GetMapping("/aditamentos")
    public List<Aditamento> getAllAditamentos() {
        log.debug("REST request to get all Aditamentos");
        return aditamentoRepository.findAll();
    }

    /**
     * {@code GET  /aditamentos/:id} : get the "id" aditamento.
     *
     * @param id the id of the aditamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aditamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aditamentos/{id}")
    public ResponseEntity<Aditamento> getAditamento(@PathVariable Long id) {
        log.debug("REST request to get Aditamento : {}", id);
        Optional<Aditamento> aditamento = aditamentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aditamento);
    }

    /**
     * {@code DELETE  /aditamentos/:id} : delete the "id" aditamento.
     *
     * @param id the id of the aditamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aditamentos/{id}")
    public ResponseEntity<Void> deleteAditamento(@PathVariable Long id) {
        log.debug("REST request to delete Aditamento : {}", id);
        aditamentoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
