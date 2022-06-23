package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ListaPedido;
import com.mycompany.myapp.repository.ListaPedidoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ListaPedido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListaPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ListaPedidoResource.class);

    private static final String ENTITY_NAME = "listaPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListaPedidoRepository listaPedidoRepository;

    public ListaPedidoResource(ListaPedidoRepository listaPedidoRepository) {
        this.listaPedidoRepository = listaPedidoRepository;
    }

    /**
     * {@code POST  /lista-pedidos} : Create a new listaPedido.
     *
     * @param listaPedido the listaPedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listaPedido, or with status {@code 400 (Bad Request)} if the listaPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lista-pedidos")
    public ResponseEntity<ListaPedido> createListaPedido(@RequestBody ListaPedido listaPedido) throws URISyntaxException {
        log.debug("REST request to save ListaPedido : {}", listaPedido);
        if (listaPedido.getId() != null) {
            throw new BadRequestAlertException("A new listaPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListaPedido result = listaPedidoRepository.save(listaPedido);
        return ResponseEntity
            .created(new URI("/api/lista-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lista-pedidos/:id} : Updates an existing listaPedido.
     *
     * @param id the id of the listaPedido to save.
     * @param listaPedido the listaPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaPedido,
     * or with status {@code 400 (Bad Request)} if the listaPedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listaPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lista-pedidos/{id}")
    public ResponseEntity<ListaPedido> updateListaPedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListaPedido listaPedido
    ) throws URISyntaxException {
        log.debug("REST request to update ListaPedido : {}, {}", id, listaPedido);
        if (listaPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listaPedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listaPedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListaPedido result = listaPedidoRepository.save(listaPedido);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listaPedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lista-pedidos/:id} : Partial updates given fields of an existing listaPedido, field will ignore if it is null
     *
     * @param id the id of the listaPedido to save.
     * @param listaPedido the listaPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listaPedido,
     * or with status {@code 400 (Bad Request)} if the listaPedido is not valid,
     * or with status {@code 404 (Not Found)} if the listaPedido is not found,
     * or with status {@code 500 (Internal Server Error)} if the listaPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lista-pedidos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ListaPedido> partialUpdateListaPedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListaPedido listaPedido
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListaPedido partially : {}, {}", id, listaPedido);
        if (listaPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listaPedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listaPedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListaPedido> result = listaPedidoRepository
            .findById(listaPedido.getId())
            .map(existingListaPedido -> {
                if (listaPedido.getCantidad() != null) {
                    existingListaPedido.setCantidad(listaPedido.getCantidad());
                }

                return existingListaPedido;
            })
            .map(listaPedidoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listaPedido.getId().toString())
        );
    }

    /**
     * {@code GET  /lista-pedidos} : get all the listaPedidos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listaPedidos in body.
     */
    @GetMapping("/lista-pedidos")
    public List<ListaPedido> getAllListaPedidos() {
        log.debug("REST request to get all ListaPedidos");
        return listaPedidoRepository.findAll();
    }

    /**
     * {@code GET  /lista-pedidos/:id} : get the "id" listaPedido.
     *
     * @param id the id of the listaPedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listaPedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lista-pedidos/{id}")
    public ResponseEntity<ListaPedido> getListaPedido(@PathVariable Long id) {
        log.debug("REST request to get ListaPedido : {}", id);
        Optional<ListaPedido> listaPedido = listaPedidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listaPedido);
    }

    /**
     * {@code DELETE  /lista-pedidos/:id} : delete the "id" listaPedido.
     *
     * @param id the id of the listaPedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lista-pedidos/{id}")
    public ResponseEntity<Void> deleteListaPedido(@PathVariable Long id) {
        log.debug("REST request to delete ListaPedido : {}", id);
        listaPedidoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
