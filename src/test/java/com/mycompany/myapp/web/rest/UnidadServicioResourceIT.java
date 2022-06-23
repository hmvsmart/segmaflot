package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UnidadServicio;
import com.mycompany.myapp.repository.UnidadServicioRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link UnidadServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadServicioResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL_ESTIMADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_ESTIMADO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_REAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_REAL = new BigDecimal(2);

    private static final byte[] DEFAULT_OBSERVACIONES_GENERALES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OBSERVACIONES_GENERALES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OBSERVACIONES_GENERALES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATE_BY_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFY_BY_USER = "AAAAAAAAAA";
    private static final String UPDATED_MODIFY_BY_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AUDIT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_AUDIT_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/unidad-servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnidadServicioRepository unidadServicioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadServicioMockMvc;

    private UnidadServicio unidadServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadServicio createEntity(EntityManager em) {
        UnidadServicio unidadServicio = new UnidadServicio()
            .fecha(DEFAULT_FECHA)
            .totalEstimado(DEFAULT_TOTAL_ESTIMADO)
            .totalReal(DEFAULT_TOTAL_REAL)
            .observacionesGenerales(DEFAULT_OBSERVACIONES_GENERALES)
            .observacionesGeneralesContentType(DEFAULT_OBSERVACIONES_GENERALES_CONTENT_TYPE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return unidadServicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadServicio createUpdatedEntity(EntityManager em) {
        UnidadServicio unidadServicio = new UnidadServicio()
            .fecha(UPDATED_FECHA)
            .totalEstimado(UPDATED_TOTAL_ESTIMADO)
            .totalReal(UPDATED_TOTAL_REAL)
            .observacionesGenerales(UPDATED_OBSERVACIONES_GENERALES)
            .observacionesGeneralesContentType(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return unidadServicio;
    }

    @BeforeEach
    public void initTest() {
        unidadServicio = createEntity(em);
    }

    @Test
    @Transactional
    void createUnidadServicio() throws Exception {
        int databaseSizeBeforeCreate = unidadServicioRepository.findAll().size();
        // Create the UnidadServicio
        restUnidadServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isCreated());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUnidadServicio.getTotalEstimado()).isEqualByComparingTo(DEFAULT_TOTAL_ESTIMADO);
        assertThat(testUnidadServicio.getTotalReal()).isEqualByComparingTo(DEFAULT_TOTAL_REAL);
        assertThat(testUnidadServicio.getObservacionesGenerales()).isEqualTo(DEFAULT_OBSERVACIONES_GENERALES);
        assertThat(testUnidadServicio.getObservacionesGeneralesContentType()).isEqualTo(DEFAULT_OBSERVACIONES_GENERALES_CONTENT_TYPE);
        assertThat(testUnidadServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testUnidadServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUnidadServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUnidadServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testUnidadServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createUnidadServicioWithExistingId() throws Exception {
        // Create the UnidadServicio with an existing ID
        unidadServicio.setId(1L);

        int databaseSizeBeforeCreate = unidadServicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnidadServicios() throws Exception {
        // Initialize the database
        unidadServicioRepository.saveAndFlush(unidadServicio);

        // Get all the unidadServicioList
        restUnidadServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].totalEstimado").value(hasItem(sameNumber(DEFAULT_TOTAL_ESTIMADO))))
            .andExpect(jsonPath("$.[*].totalReal").value(hasItem(sameNumber(DEFAULT_TOTAL_REAL))))
            .andExpect(jsonPath("$.[*].observacionesGeneralesContentType").value(hasItem(DEFAULT_OBSERVACIONES_GENERALES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].observacionesGenerales").value(hasItem(Base64Utils.encodeToString(DEFAULT_OBSERVACIONES_GENERALES))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getUnidadServicio() throws Exception {
        // Initialize the database
        unidadServicioRepository.saveAndFlush(unidadServicio);

        // Get the unidadServicio
        restUnidadServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadServicio.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.totalEstimado").value(sameNumber(DEFAULT_TOTAL_ESTIMADO)))
            .andExpect(jsonPath("$.totalReal").value(sameNumber(DEFAULT_TOTAL_REAL)))
            .andExpect(jsonPath("$.observacionesGeneralesContentType").value(DEFAULT_OBSERVACIONES_GENERALES_CONTENT_TYPE))
            .andExpect(jsonPath("$.observacionesGenerales").value(Base64Utils.encodeToString(DEFAULT_OBSERVACIONES_GENERALES)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingUnidadServicio() throws Exception {
        // Get the unidadServicio
        restUnidadServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnidadServicio() throws Exception {
        // Initialize the database
        unidadServicioRepository.saveAndFlush(unidadServicio);

        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();

        // Update the unidadServicio
        UnidadServicio updatedUnidadServicio = unidadServicioRepository.findById(unidadServicio.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadServicio are not directly saved in db
        em.detach(updatedUnidadServicio);
        updatedUnidadServicio
            .fecha(UPDATED_FECHA)
            .totalEstimado(UPDATED_TOTAL_ESTIMADO)
            .totalReal(UPDATED_TOTAL_REAL)
            .observacionesGenerales(UPDATED_OBSERVACIONES_GENERALES)
            .observacionesGeneralesContentType(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidadServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidadServicio))
            )
            .andExpect(status().isOk());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidadServicio.getTotalEstimado()).isEqualByComparingTo(UPDATED_TOTAL_ESTIMADO);
        assertThat(testUnidadServicio.getTotalReal()).isEqualByComparingTo(UPDATED_TOTAL_REAL);
        assertThat(testUnidadServicio.getObservacionesGenerales()).isEqualTo(UPDATED_OBSERVACIONES_GENERALES);
        assertThat(testUnidadServicio.getObservacionesGeneralesContentType()).isEqualTo(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE);
        assertThat(testUnidadServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidadServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidadServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUnidadServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidadServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadServicio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadServicioWithPatch() throws Exception {
        // Initialize the database
        unidadServicioRepository.saveAndFlush(unidadServicio);

        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();

        // Update the unidadServicio using partial update
        UnidadServicio partialUpdatedUnidadServicio = new UnidadServicio();
        partialUpdatedUnidadServicio.setId(unidadServicio.getId());

        partialUpdatedUnidadServicio
            .observacionesGenerales(UPDATED_OBSERVACIONES_GENERALES)
            .observacionesGeneralesContentType(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadServicio))
            )
            .andExpect(status().isOk());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUnidadServicio.getTotalEstimado()).isEqualByComparingTo(DEFAULT_TOTAL_ESTIMADO);
        assertThat(testUnidadServicio.getTotalReal()).isEqualByComparingTo(DEFAULT_TOTAL_REAL);
        assertThat(testUnidadServicio.getObservacionesGenerales()).isEqualTo(UPDATED_OBSERVACIONES_GENERALES);
        assertThat(testUnidadServicio.getObservacionesGeneralesContentType()).isEqualTo(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE);
        assertThat(testUnidadServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidadServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUnidadServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUnidadServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testUnidadServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateUnidadServicioWithPatch() throws Exception {
        // Initialize the database
        unidadServicioRepository.saveAndFlush(unidadServicio);

        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();

        // Update the unidadServicio using partial update
        UnidadServicio partialUpdatedUnidadServicio = new UnidadServicio();
        partialUpdatedUnidadServicio.setId(unidadServicio.getId());

        partialUpdatedUnidadServicio
            .fecha(UPDATED_FECHA)
            .totalEstimado(UPDATED_TOTAL_ESTIMADO)
            .totalReal(UPDATED_TOTAL_REAL)
            .observacionesGenerales(UPDATED_OBSERVACIONES_GENERALES)
            .observacionesGeneralesContentType(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadServicio))
            )
            .andExpect(status().isOk());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
        UnidadServicio testUnidadServicio = unidadServicioList.get(unidadServicioList.size() - 1);
        assertThat(testUnidadServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidadServicio.getTotalEstimado()).isEqualByComparingTo(UPDATED_TOTAL_ESTIMADO);
        assertThat(testUnidadServicio.getTotalReal()).isEqualByComparingTo(UPDATED_TOTAL_REAL);
        assertThat(testUnidadServicio.getObservacionesGenerales()).isEqualTo(UPDATED_OBSERVACIONES_GENERALES);
        assertThat(testUnidadServicio.getObservacionesGeneralesContentType()).isEqualTo(UPDATED_OBSERVACIONES_GENERALES_CONTENT_TYPE);
        assertThat(testUnidadServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidadServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidadServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUnidadServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidadServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidadServicio() throws Exception {
        int databaseSizeBeforeUpdate = unidadServicioRepository.findAll().size();
        unidadServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadServicioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unidadServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadServicio in the database
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidadServicio() throws Exception {
        // Initialize the database
        unidadServicioRepository.saveAndFlush(unidadServicio);

        int databaseSizeBeforeDelete = unidadServicioRepository.findAll().size();

        // Delete the unidadServicio
        restUnidadServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadServicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadServicio> unidadServicioList = unidadServicioRepository.findAll();
        assertThat(unidadServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
