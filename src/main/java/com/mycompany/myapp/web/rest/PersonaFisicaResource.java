package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PersonaFisica;
import com.mycompany.myapp.repository.PersonaFisicaRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PersonaFisica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonaFisicaResource {

    private final Logger log = LoggerFactory.getLogger(PersonaFisicaResource.class);

    private static final String ENTITY_NAME = "personaFisica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonaFisicaRepository personaFisicaRepository;

    public PersonaFisicaResource(PersonaFisicaRepository personaFisicaRepository) {
        this.personaFisicaRepository = personaFisicaRepository;
    }

    /**
     * {@code POST  /persona-fisicas} : Create a new personaFisica.
     *
     * @param personaFisica the personaFisica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personaFisica, or with status {@code 400 (Bad Request)} if the personaFisica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/persona-fisicas")
    public ResponseEntity<PersonaFisica> createPersonaFisica(@RequestBody PersonaFisica personaFisica) throws URISyntaxException {
        log.debug("REST request to save PersonaFisica : {}", personaFisica);
        if (personaFisica.getId() != null) {
            throw new BadRequestAlertException("A new personaFisica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonaFisica result = personaFisicaRepository.save(personaFisica);
        return ResponseEntity
            .created(new URI("/api/persona-fisicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /persona-fisicas/:id} : Updates an existing personaFisica.
     *
     * @param id the id of the personaFisica to save.
     * @param personaFisica the personaFisica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaFisica,
     * or with status {@code 400 (Bad Request)} if the personaFisica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personaFisica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/persona-fisicas/{id}")
    public ResponseEntity<PersonaFisica> updatePersonaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonaFisica personaFisica
    ) throws URISyntaxException {
        log.debug("REST request to update PersonaFisica : {}, {}", id, personaFisica);
        if (personaFisica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaFisica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonaFisica result = personaFisicaRepository.save(personaFisica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personaFisica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /persona-fisicas/:id} : Partial updates given fields of an existing personaFisica, field will ignore if it is null
     *
     * @param id the id of the personaFisica to save.
     * @param personaFisica the personaFisica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaFisica,
     * or with status {@code 400 (Bad Request)} if the personaFisica is not valid,
     * or with status {@code 404 (Not Found)} if the personaFisica is not found,
     * or with status {@code 500 (Internal Server Error)} if the personaFisica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/persona-fisicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonaFisica> partialUpdatePersonaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonaFisica personaFisica
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonaFisica partially : {}, {}", id, personaFisica);
        if (personaFisica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaFisica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonaFisica> result = personaFisicaRepository
            .findById(personaFisica.getId())
            .map(existingPersonaFisica -> {
                if (personaFisica.getNombre() != null) {
                    existingPersonaFisica.setNombre(personaFisica.getNombre());
                }
                if (personaFisica.getApaterno() != null) {
                    existingPersonaFisica.setApaterno(personaFisica.getApaterno());
                }
                if (personaFisica.getAmaterno() != null) {
                    existingPersonaFisica.setAmaterno(personaFisica.getAmaterno());
                }
                if (personaFisica.getFechaNacimiento() != null) {
                    existingPersonaFisica.setFechaNacimiento(personaFisica.getFechaNacimiento());
                }
                if (personaFisica.getCurp() != null) {
                    existingPersonaFisica.setCurp(personaFisica.getCurp());
                }
                if (personaFisica.getCreateByUser() != null) {
                    existingPersonaFisica.setCreateByUser(personaFisica.getCreateByUser());
                }
                if (personaFisica.getCreatedOn() != null) {
                    existingPersonaFisica.setCreatedOn(personaFisica.getCreatedOn());
                }
                if (personaFisica.getModifyByUser() != null) {
                    existingPersonaFisica.setModifyByUser(personaFisica.getModifyByUser());
                }
                if (personaFisica.getModifiedOn() != null) {
                    existingPersonaFisica.setModifiedOn(personaFisica.getModifiedOn());
                }
                if (personaFisica.getAuditStatus() != null) {
                    existingPersonaFisica.setAuditStatus(personaFisica.getAuditStatus());
                }

                return existingPersonaFisica;
            })
            .map(personaFisicaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personaFisica.getId().toString())
        );
    }

    /**
     * {@code GET  /persona-fisicas} : get all the personaFisicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personaFisicas in body.
     */
    @GetMapping("/persona-fisicas")
    public List<PersonaFisica> getAllPersonaFisicas() {
        log.debug("REST request to get all PersonaFisicas");
        return personaFisicaRepository.findAll();
    }

    /**
     * {@code GET  /persona-fisicas/:id} : get the "id" personaFisica.
     *
     * @param id the id of the personaFisica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personaFisica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/persona-fisicas/{id}")
    public ResponseEntity<PersonaFisica> getPersonaFisica(@PathVariable Long id) {
        log.debug("REST request to get PersonaFisica : {}", id);
        Optional<PersonaFisica> personaFisica = personaFisicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personaFisica);
    }

    /**
     * {@code DELETE  /persona-fisicas/:id} : delete the "id" personaFisica.
     *
     * @param id the id of the personaFisica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/persona-fisicas/{id}")
    public ResponseEntity<Void> deletePersonaFisica(@PathVariable Long id) {
        log.debug("REST request to delete PersonaFisica : {}", id);
        personaFisicaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
