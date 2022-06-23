package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.VehiculoCliente;
import com.mycompany.myapp.repository.VehiculoClienteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.VehiculoCliente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VehiculoClienteResource {

    private final Logger log = LoggerFactory.getLogger(VehiculoClienteResource.class);

    private static final String ENTITY_NAME = "vehiculoCliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehiculoClienteRepository vehiculoClienteRepository;

    public VehiculoClienteResource(VehiculoClienteRepository vehiculoClienteRepository) {
        this.vehiculoClienteRepository = vehiculoClienteRepository;
    }

    /**
     * {@code POST  /vehiculo-clientes} : Create a new vehiculoCliente.
     *
     * @param vehiculoCliente the vehiculoCliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehiculoCliente, or with status {@code 400 (Bad Request)} if the vehiculoCliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehiculo-clientes")
    public ResponseEntity<VehiculoCliente> createVehiculoCliente(@RequestBody VehiculoCliente vehiculoCliente) throws URISyntaxException {
        log.debug("REST request to save VehiculoCliente : {}", vehiculoCliente);
        if (vehiculoCliente.getId() != null) {
            throw new BadRequestAlertException("A new vehiculoCliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehiculoCliente result = vehiculoClienteRepository.save(vehiculoCliente);
        return ResponseEntity
            .created(new URI("/api/vehiculo-clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehiculo-clientes/:id} : Updates an existing vehiculoCliente.
     *
     * @param id the id of the vehiculoCliente to save.
     * @param vehiculoCliente the vehiculoCliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehiculoCliente,
     * or with status {@code 400 (Bad Request)} if the vehiculoCliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehiculoCliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehiculo-clientes/{id}")
    public ResponseEntity<VehiculoCliente> updateVehiculoCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VehiculoCliente vehiculoCliente
    ) throws URISyntaxException {
        log.debug("REST request to update VehiculoCliente : {}, {}", id, vehiculoCliente);
        if (vehiculoCliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehiculoCliente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehiculoClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehiculoCliente result = vehiculoClienteRepository.save(vehiculoCliente);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehiculoCliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehiculo-clientes/:id} : Partial updates given fields of an existing vehiculoCliente, field will ignore if it is null
     *
     * @param id the id of the vehiculoCliente to save.
     * @param vehiculoCliente the vehiculoCliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehiculoCliente,
     * or with status {@code 400 (Bad Request)} if the vehiculoCliente is not valid,
     * or with status {@code 404 (Not Found)} if the vehiculoCliente is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehiculoCliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehiculo-clientes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VehiculoCliente> partialUpdateVehiculoCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VehiculoCliente vehiculoCliente
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehiculoCliente partially : {}, {}", id, vehiculoCliente);
        if (vehiculoCliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehiculoCliente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehiculoClienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehiculoCliente> result = vehiculoClienteRepository
            .findById(vehiculoCliente.getId())
            .map(existingVehiculoCliente -> {
                if (vehiculoCliente.getFecha() != null) {
                    existingVehiculoCliente.setFecha(vehiculoCliente.getFecha());
                }
                if (vehiculoCliente.getNumeroSerie() != null) {
                    existingVehiculoCliente.setNumeroSerie(vehiculoCliente.getNumeroSerie());
                }
                if (vehiculoCliente.getColor() != null) {
                    existingVehiculoCliente.setColor(vehiculoCliente.getColor());
                }
                if (vehiculoCliente.getStatus() != null) {
                    existingVehiculoCliente.setStatus(vehiculoCliente.getStatus());
                }
                if (vehiculoCliente.getCreateByUser() != null) {
                    existingVehiculoCliente.setCreateByUser(vehiculoCliente.getCreateByUser());
                }
                if (vehiculoCliente.getCreatedOn() != null) {
                    existingVehiculoCliente.setCreatedOn(vehiculoCliente.getCreatedOn());
                }
                if (vehiculoCliente.getModifyByUser() != null) {
                    existingVehiculoCliente.setModifyByUser(vehiculoCliente.getModifyByUser());
                }
                if (vehiculoCliente.getModifiedOn() != null) {
                    existingVehiculoCliente.setModifiedOn(vehiculoCliente.getModifiedOn());
                }
                if (vehiculoCliente.getAuditStatus() != null) {
                    existingVehiculoCliente.setAuditStatus(vehiculoCliente.getAuditStatus());
                }

                return existingVehiculoCliente;
            })
            .map(vehiculoClienteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehiculoCliente.getId().toString())
        );
    }

    /**
     * {@code GET  /vehiculo-clientes} : get all the vehiculoClientes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehiculoClientes in body.
     */
    @GetMapping("/vehiculo-clientes")
    public List<VehiculoCliente> getAllVehiculoClientes() {
        log.debug("REST request to get all VehiculoClientes");
        return vehiculoClienteRepository.findAll();
    }

    /**
     * {@code GET  /vehiculo-clientes/:id} : get the "id" vehiculoCliente.
     *
     * @param id the id of the vehiculoCliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehiculoCliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehiculo-clientes/{id}")
    public ResponseEntity<VehiculoCliente> getVehiculoCliente(@PathVariable Long id) {
        log.debug("REST request to get VehiculoCliente : {}", id);
        Optional<VehiculoCliente> vehiculoCliente = vehiculoClienteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vehiculoCliente);
    }

    /**
     * {@code DELETE  /vehiculo-clientes/:id} : delete the "id" vehiculoCliente.
     *
     * @param id the id of the vehiculoCliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehiculo-clientes/{id}")
    public ResponseEntity<Void> deleteVehiculoCliente(@PathVariable Long id) {
        log.debug("REST request to delete VehiculoCliente : {}", id);
        vehiculoClienteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
