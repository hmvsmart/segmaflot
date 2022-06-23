package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Reporte;
import com.mycompany.myapp.repository.ReporteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Reporte}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReporteResource {

    private final Logger log = LoggerFactory.getLogger(ReporteResource.class);

    private static final String ENTITY_NAME = "reporte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReporteRepository reporteRepository;

    public ReporteResource(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    /**
     * {@code POST  /reportes} : Create a new reporte.
     *
     * @param reporte the reporte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reporte, or with status {@code 400 (Bad Request)} if the reporte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reportes")
    public ResponseEntity<Reporte> createReporte(@RequestBody Reporte reporte) throws URISyntaxException {
        log.debug("REST request to save Reporte : {}", reporte);
        if (reporte.getId() != null) {
            throw new BadRequestAlertException("A new reporte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reporte result = reporteRepository.save(reporte);
        return ResponseEntity
            .created(new URI("/api/reportes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reportes/:id} : Updates an existing reporte.
     *
     * @param id the id of the reporte to save.
     * @param reporte the reporte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reporte,
     * or with status {@code 400 (Bad Request)} if the reporte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reporte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reportes/{id}")
    public ResponseEntity<Reporte> updateReporte(@PathVariable(value = "id", required = false) final Long id, @RequestBody Reporte reporte)
        throws URISyntaxException {
        log.debug("REST request to update Reporte : {}, {}", id, reporte);
        if (reporte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reporte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reporteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Reporte result = reporteRepository.save(reporte);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reporte.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reportes/:id} : Partial updates given fields of an existing reporte, field will ignore if it is null
     *
     * @param id the id of the reporte to save.
     * @param reporte the reporte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reporte,
     * or with status {@code 400 (Bad Request)} if the reporte is not valid,
     * or with status {@code 404 (Not Found)} if the reporte is not found,
     * or with status {@code 500 (Internal Server Error)} if the reporte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reportes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reporte> partialUpdateReporte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reporte reporte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reporte partially : {}, {}", id, reporte);
        if (reporte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reporte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reporteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reporte> result = reporteRepository
            .findById(reporte.getId())
            .map(existingReporte -> {
                if (reporte.getFecha() != null) {
                    existingReporte.setFecha(reporte.getFecha());
                }
                if (reporte.getNombre() != null) {
                    existingReporte.setNombre(reporte.getNombre());
                }
                if (reporte.getDocumento() != null) {
                    existingReporte.setDocumento(reporte.getDocumento());
                }
                if (reporte.getDocumentoContentType() != null) {
                    existingReporte.setDocumentoContentType(reporte.getDocumentoContentType());
                }
                if (reporte.getCreateByUser() != null) {
                    existingReporte.setCreateByUser(reporte.getCreateByUser());
                }
                if (reporte.getCreatedOn() != null) {
                    existingReporte.setCreatedOn(reporte.getCreatedOn());
                }
                if (reporte.getModifyByUser() != null) {
                    existingReporte.setModifyByUser(reporte.getModifyByUser());
                }
                if (reporte.getModifiedOn() != null) {
                    existingReporte.setModifiedOn(reporte.getModifiedOn());
                }
                if (reporte.getAuditStatus() != null) {
                    existingReporte.setAuditStatus(reporte.getAuditStatus());
                }

                return existingReporte;
            })
            .map(reporteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reporte.getId().toString())
        );
    }

    /**
     * {@code GET  /reportes} : get all the reportes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportes in body.
     */
    @GetMapping("/reportes")
    public List<Reporte> getAllReportes() {
        log.debug("REST request to get all Reportes");
        return reporteRepository.findAll();
    }

    /**
     * {@code GET  /reportes/:id} : get the "id" reporte.
     *
     * @param id the id of the reporte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reporte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reportes/{id}")
    public ResponseEntity<Reporte> getReporte(@PathVariable Long id) {
        log.debug("REST request to get Reporte : {}", id);
        Optional<Reporte> reporte = reporteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reporte);
    }

    /**
     * {@code DELETE  /reportes/:id} : delete the "id" reporte.
     *
     * @param id the id of the reporte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reportes/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        log.debug("REST request to delete Reporte : {}", id);
        reporteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
