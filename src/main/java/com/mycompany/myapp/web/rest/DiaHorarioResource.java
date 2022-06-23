package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DiaHorario;
import com.mycompany.myapp.repository.DiaHorarioRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DiaHorario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiaHorarioResource {

    private final Logger log = LoggerFactory.getLogger(DiaHorarioResource.class);

    private static final String ENTITY_NAME = "diaHorario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiaHorarioRepository diaHorarioRepository;

    public DiaHorarioResource(DiaHorarioRepository diaHorarioRepository) {
        this.diaHorarioRepository = diaHorarioRepository;
    }

    /**
     * {@code POST  /dia-horarios} : Create a new diaHorario.
     *
     * @param diaHorario the diaHorario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diaHorario, or with status {@code 400 (Bad Request)} if the diaHorario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dia-horarios")
    public ResponseEntity<DiaHorario> createDiaHorario(@RequestBody DiaHorario diaHorario) throws URISyntaxException {
        log.debug("REST request to save DiaHorario : {}", diaHorario);
        if (diaHorario.getId() != null) {
            throw new BadRequestAlertException("A new diaHorario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaHorario result = diaHorarioRepository.save(diaHorario);
        return ResponseEntity
            .created(new URI("/api/dia-horarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dia-horarios/:id} : Updates an existing diaHorario.
     *
     * @param id the id of the diaHorario to save.
     * @param diaHorario the diaHorario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaHorario,
     * or with status {@code 400 (Bad Request)} if the diaHorario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diaHorario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dia-horarios/{id}")
    public ResponseEntity<DiaHorario> updateDiaHorario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiaHorario diaHorario
    ) throws URISyntaxException {
        log.debug("REST request to update DiaHorario : {}, {}", id, diaHorario);
        if (diaHorario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diaHorario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaHorarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiaHorario result = diaHorarioRepository.save(diaHorario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diaHorario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dia-horarios/:id} : Partial updates given fields of an existing diaHorario, field will ignore if it is null
     *
     * @param id the id of the diaHorario to save.
     * @param diaHorario the diaHorario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diaHorario,
     * or with status {@code 400 (Bad Request)} if the diaHorario is not valid,
     * or with status {@code 404 (Not Found)} if the diaHorario is not found,
     * or with status {@code 500 (Internal Server Error)} if the diaHorario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dia-horarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiaHorario> partialUpdateDiaHorario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiaHorario diaHorario
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiaHorario partially : {}, {}", id, diaHorario);
        if (diaHorario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diaHorario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diaHorarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiaHorario> result = diaHorarioRepository
            .findById(diaHorario.getId())
            .map(existingDiaHorario -> {
                if (diaHorario.getDia() != null) {
                    existingDiaHorario.setDia(diaHorario.getDia());
                }
                if (diaHorario.getHoraEntrada() != null) {
                    existingDiaHorario.setHoraEntrada(diaHorario.getHoraEntrada());
                }
                if (diaHorario.getHoraSalida() != null) {
                    existingDiaHorario.setHoraSalida(diaHorario.getHoraSalida());
                }
                if (diaHorario.getCreateByUser() != null) {
                    existingDiaHorario.setCreateByUser(diaHorario.getCreateByUser());
                }
                if (diaHorario.getCreatedOn() != null) {
                    existingDiaHorario.setCreatedOn(diaHorario.getCreatedOn());
                }
                if (diaHorario.getModifyByUser() != null) {
                    existingDiaHorario.setModifyByUser(diaHorario.getModifyByUser());
                }
                if (diaHorario.getModifiedOn() != null) {
                    existingDiaHorario.setModifiedOn(diaHorario.getModifiedOn());
                }
                if (diaHorario.getAuditStatus() != null) {
                    existingDiaHorario.setAuditStatus(diaHorario.getAuditStatus());
                }

                return existingDiaHorario;
            })
            .map(diaHorarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diaHorario.getId().toString())
        );
    }

    /**
     * {@code GET  /dia-horarios} : get all the diaHorarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diaHorarios in body.
     */
    @GetMapping("/dia-horarios")
    public List<DiaHorario> getAllDiaHorarios() {
        log.debug("REST request to get all DiaHorarios");
        return diaHorarioRepository.findAll();
    }

    /**
     * {@code GET  /dia-horarios/:id} : get the "id" diaHorario.
     *
     * @param id the id of the diaHorario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diaHorario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dia-horarios/{id}")
    public ResponseEntity<DiaHorario> getDiaHorario(@PathVariable Long id) {
        log.debug("REST request to get DiaHorario : {}", id);
        Optional<DiaHorario> diaHorario = diaHorarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diaHorario);
    }

    /**
     * {@code DELETE  /dia-horarios/:id} : delete the "id" diaHorario.
     *
     * @param id the id of the diaHorario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dia-horarios/{id}")
    public ResponseEntity<Void> deleteDiaHorario(@PathVariable Long id) {
        log.debug("REST request to delete DiaHorario : {}", id);
        diaHorarioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
