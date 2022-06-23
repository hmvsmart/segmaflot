package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.EmpleadoTipoVehiculo;
import com.mycompany.myapp.repository.EmpleadoTipoVehiculoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EmpleadoTipoVehiculo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmpleadoTipoVehiculoResource {

    private final Logger log = LoggerFactory.getLogger(EmpleadoTipoVehiculoResource.class);

    private static final String ENTITY_NAME = "empleadoTipoVehiculo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpleadoTipoVehiculoRepository empleadoTipoVehiculoRepository;

    public EmpleadoTipoVehiculoResource(EmpleadoTipoVehiculoRepository empleadoTipoVehiculoRepository) {
        this.empleadoTipoVehiculoRepository = empleadoTipoVehiculoRepository;
    }

    /**
     * {@code POST  /empleado-tipo-vehiculos} : Create a new empleadoTipoVehiculo.
     *
     * @param empleadoTipoVehiculo the empleadoTipoVehiculo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empleadoTipoVehiculo, or with status {@code 400 (Bad Request)} if the empleadoTipoVehiculo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empleado-tipo-vehiculos")
    public ResponseEntity<EmpleadoTipoVehiculo> createEmpleadoTipoVehiculo(@RequestBody EmpleadoTipoVehiculo empleadoTipoVehiculo)
        throws URISyntaxException {
        log.debug("REST request to save EmpleadoTipoVehiculo : {}", empleadoTipoVehiculo);
        if (empleadoTipoVehiculo.getId() != null) {
            throw new BadRequestAlertException("A new empleadoTipoVehiculo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpleadoTipoVehiculo result = empleadoTipoVehiculoRepository.save(empleadoTipoVehiculo);
        return ResponseEntity
            .created(new URI("/api/empleado-tipo-vehiculos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empleado-tipo-vehiculos/:id} : Updates an existing empleadoTipoVehiculo.
     *
     * @param id the id of the empleadoTipoVehiculo to save.
     * @param empleadoTipoVehiculo the empleadoTipoVehiculo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleadoTipoVehiculo,
     * or with status {@code 400 (Bad Request)} if the empleadoTipoVehiculo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empleadoTipoVehiculo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empleado-tipo-vehiculos/{id}")
    public ResponseEntity<EmpleadoTipoVehiculo> updateEmpleadoTipoVehiculo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpleadoTipoVehiculo empleadoTipoVehiculo
    ) throws URISyntaxException {
        log.debug("REST request to update EmpleadoTipoVehiculo : {}, {}", id, empleadoTipoVehiculo);
        if (empleadoTipoVehiculo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleadoTipoVehiculo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadoTipoVehiculoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmpleadoTipoVehiculo result = empleadoTipoVehiculoRepository.save(empleadoTipoVehiculo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadoTipoVehiculo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /empleado-tipo-vehiculos/:id} : Partial updates given fields of an existing empleadoTipoVehiculo, field will ignore if it is null
     *
     * @param id the id of the empleadoTipoVehiculo to save.
     * @param empleadoTipoVehiculo the empleadoTipoVehiculo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleadoTipoVehiculo,
     * or with status {@code 400 (Bad Request)} if the empleadoTipoVehiculo is not valid,
     * or with status {@code 404 (Not Found)} if the empleadoTipoVehiculo is not found,
     * or with status {@code 500 (Internal Server Error)} if the empleadoTipoVehiculo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/empleado-tipo-vehiculos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpleadoTipoVehiculo> partialUpdateEmpleadoTipoVehiculo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpleadoTipoVehiculo empleadoTipoVehiculo
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmpleadoTipoVehiculo partially : {}, {}", id, empleadoTipoVehiculo);
        if (empleadoTipoVehiculo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleadoTipoVehiculo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadoTipoVehiculoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpleadoTipoVehiculo> result = empleadoTipoVehiculoRepository
            .findById(empleadoTipoVehiculo.getId())
            .map(existingEmpleadoTipoVehiculo -> {
                if (empleadoTipoVehiculo.getFecha() != null) {
                    existingEmpleadoTipoVehiculo.setFecha(empleadoTipoVehiculo.getFecha());
                }
                if (empleadoTipoVehiculo.getStatus() != null) {
                    existingEmpleadoTipoVehiculo.setStatus(empleadoTipoVehiculo.getStatus());
                }
                if (empleadoTipoVehiculo.getCreateByUser() != null) {
                    existingEmpleadoTipoVehiculo.setCreateByUser(empleadoTipoVehiculo.getCreateByUser());
                }
                if (empleadoTipoVehiculo.getCreatedOn() != null) {
                    existingEmpleadoTipoVehiculo.setCreatedOn(empleadoTipoVehiculo.getCreatedOn());
                }
                if (empleadoTipoVehiculo.getModifyByUser() != null) {
                    existingEmpleadoTipoVehiculo.setModifyByUser(empleadoTipoVehiculo.getModifyByUser());
                }
                if (empleadoTipoVehiculo.getModifiedOn() != null) {
                    existingEmpleadoTipoVehiculo.setModifiedOn(empleadoTipoVehiculo.getModifiedOn());
                }
                if (empleadoTipoVehiculo.getAuditStatus() != null) {
                    existingEmpleadoTipoVehiculo.setAuditStatus(empleadoTipoVehiculo.getAuditStatus());
                }

                return existingEmpleadoTipoVehiculo;
            })
            .map(empleadoTipoVehiculoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadoTipoVehiculo.getId().toString())
        );
    }

    /**
     * {@code GET  /empleado-tipo-vehiculos} : get all the empleadoTipoVehiculos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empleadoTipoVehiculos in body.
     */
    @GetMapping("/empleado-tipo-vehiculos")
    public List<EmpleadoTipoVehiculo> getAllEmpleadoTipoVehiculos() {
        log.debug("REST request to get all EmpleadoTipoVehiculos");
        return empleadoTipoVehiculoRepository.findAll();
    }

    /**
     * {@code GET  /empleado-tipo-vehiculos/:id} : get the "id" empleadoTipoVehiculo.
     *
     * @param id the id of the empleadoTipoVehiculo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empleadoTipoVehiculo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empleado-tipo-vehiculos/{id}")
    public ResponseEntity<EmpleadoTipoVehiculo> getEmpleadoTipoVehiculo(@PathVariable Long id) {
        log.debug("REST request to get EmpleadoTipoVehiculo : {}", id);
        Optional<EmpleadoTipoVehiculo> empleadoTipoVehiculo = empleadoTipoVehiculoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(empleadoTipoVehiculo);
    }

    /**
     * {@code DELETE  /empleado-tipo-vehiculos/:id} : delete the "id" empleadoTipoVehiculo.
     *
     * @param id the id of the empleadoTipoVehiculo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empleado-tipo-vehiculos/{id}")
    public ResponseEntity<Void> deleteEmpleadoTipoVehiculo(@PathVariable Long id) {
        log.debug("REST request to delete EmpleadoTipoVehiculo : {}", id);
        empleadoTipoVehiculoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
