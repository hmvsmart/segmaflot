package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Contacto;
import com.mycompany.myapp.repository.ContactoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Contacto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContactoResource {

    private final Logger log = LoggerFactory.getLogger(ContactoResource.class);

    private static final String ENTITY_NAME = "contacto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoRepository contactoRepository;

    public ContactoResource(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    /**
     * {@code POST  /contactos} : Create a new contacto.
     *
     * @param contacto the contacto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contacto, or with status {@code 400 (Bad Request)} if the contacto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contactos")
    public ResponseEntity<Contacto> createContacto(@RequestBody Contacto contacto) throws URISyntaxException {
        log.debug("REST request to save Contacto : {}", contacto);
        if (contacto.getId() != null) {
            throw new BadRequestAlertException("A new contacto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contacto result = contactoRepository.save(contacto);
        return ResponseEntity
            .created(new URI("/api/contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contactos/:id} : Updates an existing contacto.
     *
     * @param id the id of the contacto to save.
     * @param contacto the contacto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contacto,
     * or with status {@code 400 (Bad Request)} if the contacto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contacto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contactos/{id}")
    public ResponseEntity<Contacto> updateContacto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Contacto contacto
    ) throws URISyntaxException {
        log.debug("REST request to update Contacto : {}, {}", id, contacto);
        if (contacto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contacto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Contacto result = contactoRepository.save(contacto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contacto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contactos/:id} : Partial updates given fields of an existing contacto, field will ignore if it is null
     *
     * @param id the id of the contacto to save.
     * @param contacto the contacto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contacto,
     * or with status {@code 400 (Bad Request)} if the contacto is not valid,
     * or with status {@code 404 (Not Found)} if the contacto is not found,
     * or with status {@code 500 (Internal Server Error)} if the contacto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contactos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Contacto> partialUpdateContacto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Contacto contacto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contacto partially : {}, {}", id, contacto);
        if (contacto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contacto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Contacto> result = contactoRepository
            .findById(contacto.getId())
            .map(existingContacto -> {
                if (contacto.getValor() != null) {
                    existingContacto.setValor(contacto.getValor());
                }
                if (contacto.getEtiqueta() != null) {
                    existingContacto.setEtiqueta(contacto.getEtiqueta());
                }
                if (contacto.getString() != null) {
                    existingContacto.setString(contacto.getString());
                }
                if (contacto.getCreateByUser() != null) {
                    existingContacto.setCreateByUser(contacto.getCreateByUser());
                }
                if (contacto.getCreatedOn() != null) {
                    existingContacto.setCreatedOn(contacto.getCreatedOn());
                }
                if (contacto.getModifyByUser() != null) {
                    existingContacto.setModifyByUser(contacto.getModifyByUser());
                }
                if (contacto.getModifiedOn() != null) {
                    existingContacto.setModifiedOn(contacto.getModifiedOn());
                }
                if (contacto.getAuditStatus() != null) {
                    existingContacto.setAuditStatus(contacto.getAuditStatus());
                }

                return existingContacto;
            })
            .map(contactoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contacto.getId().toString())
        );
    }

    /**
     * {@code GET  /contactos} : get all the contactos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactos in body.
     */
    @GetMapping("/contactos")
    public List<Contacto> getAllContactos() {
        log.debug("REST request to get all Contactos");
        return contactoRepository.findAll();
    }

    /**
     * {@code GET  /contactos/:id} : get the "id" contacto.
     *
     * @param id the id of the contacto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contacto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contactos/{id}")
    public ResponseEntity<Contacto> getContacto(@PathVariable Long id) {
        log.debug("REST request to get Contacto : {}", id);
        Optional<Contacto> contacto = contactoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contacto);
    }

    /**
     * {@code DELETE  /contactos/:id} : delete the "id" contacto.
     *
     * @param id the id of the contacto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contactos/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable Long id) {
        log.debug("REST request to delete Contacto : {}", id);
        contactoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
