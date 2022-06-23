package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PersonaMoral;
import com.mycompany.myapp.repository.PersonaMoralRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PersonaMoral}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonaMoralResource {

    private final Logger log = LoggerFactory.getLogger(PersonaMoralResource.class);

    private static final String ENTITY_NAME = "personaMoral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonaMoralRepository personaMoralRepository;

    public PersonaMoralResource(PersonaMoralRepository personaMoralRepository) {
        this.personaMoralRepository = personaMoralRepository;
    }

    /**
     * {@code POST  /persona-morals} : Create a new personaMoral.
     *
     * @param personaMoral the personaMoral to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personaMoral, or with status {@code 400 (Bad Request)} if the personaMoral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/persona-morals")
    public ResponseEntity<PersonaMoral> createPersonaMoral(@RequestBody PersonaMoral personaMoral) throws URISyntaxException {
        log.debug("REST request to save PersonaMoral : {}", personaMoral);
        if (personaMoral.getId() != null) {
            throw new BadRequestAlertException("A new personaMoral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonaMoral result = personaMoralRepository.save(personaMoral);
        return ResponseEntity
            .created(new URI("/api/persona-morals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /persona-morals/:id} : Updates an existing personaMoral.
     *
     * @param id the id of the personaMoral to save.
     * @param personaMoral the personaMoral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaMoral,
     * or with status {@code 400 (Bad Request)} if the personaMoral is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personaMoral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/persona-morals/{id}")
    public ResponseEntity<PersonaMoral> updatePersonaMoral(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonaMoral personaMoral
    ) throws URISyntaxException {
        log.debug("REST request to update PersonaMoral : {}, {}", id, personaMoral);
        if (personaMoral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaMoral.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaMoralRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonaMoral result = personaMoralRepository.save(personaMoral);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personaMoral.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /persona-morals/:id} : Partial updates given fields of an existing personaMoral, field will ignore if it is null
     *
     * @param id the id of the personaMoral to save.
     * @param personaMoral the personaMoral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personaMoral,
     * or with status {@code 400 (Bad Request)} if the personaMoral is not valid,
     * or with status {@code 404 (Not Found)} if the personaMoral is not found,
     * or with status {@code 500 (Internal Server Error)} if the personaMoral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/persona-morals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonaMoral> partialUpdatePersonaMoral(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonaMoral personaMoral
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonaMoral partially : {}, {}", id, personaMoral);
        if (personaMoral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personaMoral.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personaMoralRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonaMoral> result = personaMoralRepository
            .findById(personaMoral.getId())
            .map(existingPersonaMoral -> {
                if (personaMoral.getRazonSocial() != null) {
                    existingPersonaMoral.setRazonSocial(personaMoral.getRazonSocial());
                }
                if (personaMoral.getRfc() != null) {
                    existingPersonaMoral.setRfc(personaMoral.getRfc());
                }
                if (personaMoral.getCreateByUser() != null) {
                    existingPersonaMoral.setCreateByUser(personaMoral.getCreateByUser());
                }
                if (personaMoral.getCreatedOn() != null) {
                    existingPersonaMoral.setCreatedOn(personaMoral.getCreatedOn());
                }
                if (personaMoral.getModifyByUser() != null) {
                    existingPersonaMoral.setModifyByUser(personaMoral.getModifyByUser());
                }
                if (personaMoral.getModifiedOn() != null) {
                    existingPersonaMoral.setModifiedOn(personaMoral.getModifiedOn());
                }
                if (personaMoral.getAuditStatus() != null) {
                    existingPersonaMoral.setAuditStatus(personaMoral.getAuditStatus());
                }

                return existingPersonaMoral;
            })
            .map(personaMoralRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personaMoral.getId().toString())
        );
    }

    /**
     * {@code GET  /persona-morals} : get all the personaMorals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personaMorals in body.
     */
    @GetMapping("/persona-morals")
    public List<PersonaMoral> getAllPersonaMorals() {
        log.debug("REST request to get all PersonaMorals");
        return personaMoralRepository.findAll();
    }

    /**
     * {@code GET  /persona-morals/:id} : get the "id" personaMoral.
     *
     * @param id the id of the personaMoral to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personaMoral, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/persona-morals/{id}")
    public ResponseEntity<PersonaMoral> getPersonaMoral(@PathVariable Long id) {
        log.debug("REST request to get PersonaMoral : {}", id);
        Optional<PersonaMoral> personaMoral = personaMoralRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personaMoral);
    }

    /**
     * {@code DELETE  /persona-morals/:id} : delete the "id" personaMoral.
     *
     * @param id the id of the personaMoral to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/persona-morals/{id}")
    public ResponseEntity<Void> deletePersonaMoral(@PathVariable Long id) {
        log.debug("REST request to delete PersonaMoral : {}", id);
        personaMoralRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
