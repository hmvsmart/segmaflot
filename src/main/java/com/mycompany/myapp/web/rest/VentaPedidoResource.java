package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.VentaPedido;
import com.mycompany.myapp.repository.VentaPedidoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.VentaPedido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VentaPedidoResource {

    private final Logger log = LoggerFactory.getLogger(VentaPedidoResource.class);

    private static final String ENTITY_NAME = "ventaPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentaPedidoRepository ventaPedidoRepository;

    public VentaPedidoResource(VentaPedidoRepository ventaPedidoRepository) {
        this.ventaPedidoRepository = ventaPedidoRepository;
    }

    /**
     * {@code POST  /venta-pedidos} : Create a new ventaPedido.
     *
     * @param ventaPedido the ventaPedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventaPedido, or with status {@code 400 (Bad Request)} if the ventaPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venta-pedidos")
    public ResponseEntity<VentaPedido> createVentaPedido(@RequestBody VentaPedido ventaPedido) throws URISyntaxException {
        log.debug("REST request to save VentaPedido : {}", ventaPedido);
        if (ventaPedido.getId() != null) {
            throw new BadRequestAlertException("A new ventaPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaPedido result = ventaPedidoRepository.save(ventaPedido);
        return ResponseEntity
            .created(new URI("/api/venta-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venta-pedidos/:id} : Updates an existing ventaPedido.
     *
     * @param id the id of the ventaPedido to save.
     * @param ventaPedido the ventaPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaPedido,
     * or with status {@code 400 (Bad Request)} if the ventaPedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ventaPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venta-pedidos/{id}")
    public ResponseEntity<VentaPedido> updateVentaPedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VentaPedido ventaPedido
    ) throws URISyntaxException {
        log.debug("REST request to update VentaPedido : {}, {}", id, ventaPedido);
        if (ventaPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventaPedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaPedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VentaPedido result = ventaPedidoRepository.save(ventaPedido);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaPedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /venta-pedidos/:id} : Partial updates given fields of an existing ventaPedido, field will ignore if it is null
     *
     * @param id the id of the ventaPedido to save.
     * @param ventaPedido the ventaPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventaPedido,
     * or with status {@code 400 (Bad Request)} if the ventaPedido is not valid,
     * or with status {@code 404 (Not Found)} if the ventaPedido is not found,
     * or with status {@code 500 (Internal Server Error)} if the ventaPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/venta-pedidos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VentaPedido> partialUpdateVentaPedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VentaPedido ventaPedido
    ) throws URISyntaxException {
        log.debug("REST request to partial update VentaPedido partially : {}, {}", id, ventaPedido);
        if (ventaPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventaPedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaPedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VentaPedido> result = ventaPedidoRepository
            .findById(ventaPedido.getId())
            .map(existingVentaPedido -> {
                if (ventaPedido.getFecha() != null) {
                    existingVentaPedido.setFecha(ventaPedido.getFecha());
                }

                return existingVentaPedido;
            })
            .map(ventaPedidoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventaPedido.getId().toString())
        );
    }

    /**
     * {@code GET  /venta-pedidos} : get all the ventaPedidos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventaPedidos in body.
     */
    @GetMapping("/venta-pedidos")
    public List<VentaPedido> getAllVentaPedidos() {
        log.debug("REST request to get all VentaPedidos");
        return ventaPedidoRepository.findAll();
    }

    /**
     * {@code GET  /venta-pedidos/:id} : get the "id" ventaPedido.
     *
     * @param id the id of the ventaPedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventaPedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venta-pedidos/{id}")
    public ResponseEntity<VentaPedido> getVentaPedido(@PathVariable Long id) {
        log.debug("REST request to get VentaPedido : {}", id);
        Optional<VentaPedido> ventaPedido = ventaPedidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ventaPedido);
    }

    /**
     * {@code DELETE  /venta-pedidos/:id} : delete the "id" ventaPedido.
     *
     * @param id the id of the ventaPedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venta-pedidos/{id}")
    public ResponseEntity<Void> deleteVentaPedido(@PathVariable Long id) {
        log.debug("REST request to delete VentaPedido : {}", id);
        ventaPedidoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
