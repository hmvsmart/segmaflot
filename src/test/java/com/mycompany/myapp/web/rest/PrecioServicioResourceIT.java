package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PrecioServicio;
import com.mycompany.myapp.repository.PrecioServicioRepository;
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

/**
 * Integration tests for the {@link PrecioServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrecioServicioResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_COSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_COSTO = new BigDecimal(2);

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

    private static final String ENTITY_API_URL = "/api/precio-servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrecioServicioRepository precioServicioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrecioServicioMockMvc;

    private PrecioServicio precioServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrecioServicio createEntity(EntityManager em) {
        PrecioServicio precioServicio = new PrecioServicio()
            .fecha(DEFAULT_FECHA)
            .costo(DEFAULT_COSTO)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return precioServicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrecioServicio createUpdatedEntity(EntityManager em) {
        PrecioServicio precioServicio = new PrecioServicio()
            .fecha(UPDATED_FECHA)
            .costo(UPDATED_COSTO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return precioServicio;
    }

    @BeforeEach
    public void initTest() {
        precioServicio = createEntity(em);
    }

    @Test
    @Transactional
    void createPrecioServicio() throws Exception {
        int databaseSizeBeforeCreate = precioServicioRepository.findAll().size();
        // Create the PrecioServicio
        restPrecioServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isCreated());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeCreate + 1);
        PrecioServicio testPrecioServicio = precioServicioList.get(precioServicioList.size() - 1);
        assertThat(testPrecioServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPrecioServicio.getCosto()).isEqualByComparingTo(DEFAULT_COSTO);
        assertThat(testPrecioServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPrecioServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPrecioServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPrecioServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPrecioServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createPrecioServicioWithExistingId() throws Exception {
        // Create the PrecioServicio with an existing ID
        precioServicio.setId(1L);

        int databaseSizeBeforeCreate = precioServicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecioServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrecioServicios() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        // Get all the precioServicioList
        restPrecioServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precioServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(sameNumber(DEFAULT_COSTO))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getPrecioServicio() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        // Get the precioServicio
        restPrecioServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, precioServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(precioServicio.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.costo").value(sameNumber(DEFAULT_COSTO)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPrecioServicio() throws Exception {
        // Get the precioServicio
        restPrecioServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPrecioServicio() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();

        // Update the precioServicio
        PrecioServicio updatedPrecioServicio = precioServicioRepository.findById(precioServicio.getId()).get();
        // Disconnect from session so that the updates on updatedPrecioServicio are not directly saved in db
        em.detach(updatedPrecioServicio);
        updatedPrecioServicio
            .fecha(UPDATED_FECHA)
            .costo(UPDATED_COSTO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPrecioServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrecioServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrecioServicio))
            )
            .andExpect(status().isOk());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
        PrecioServicio testPrecioServicio = precioServicioList.get(precioServicioList.size() - 1);
        assertThat(testPrecioServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPrecioServicio.getCosto()).isEqualByComparingTo(UPDATED_COSTO);
        assertThat(testPrecioServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPrecioServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPrecioServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPrecioServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPrecioServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();
        precioServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precioServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();
        precioServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();
        precioServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioServicio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrecioServicioWithPatch() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();

        // Update the precioServicio using partial update
        PrecioServicio partialUpdatedPrecioServicio = new PrecioServicio();
        partialUpdatedPrecioServicio.setId(precioServicio.getId());

        partialUpdatedPrecioServicio
            .createByUser(UPDATED_CREATE_BY_USER)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPrecioServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecioServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecioServicio))
            )
            .andExpect(status().isOk());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
        PrecioServicio testPrecioServicio = precioServicioList.get(precioServicioList.size() - 1);
        assertThat(testPrecioServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPrecioServicio.getCosto()).isEqualByComparingTo(DEFAULT_COSTO);
        assertThat(testPrecioServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPrecioServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPrecioServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPrecioServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPrecioServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePrecioServicioWithPatch() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();

        // Update the precioServicio using partial update
        PrecioServicio partialUpdatedPrecioServicio = new PrecioServicio();
        partialUpdatedPrecioServicio.setId(precioServicio.getId());

        partialUpdatedPrecioServicio
            .fecha(UPDATED_FECHA)
            .costo(UPDATED_COSTO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPrecioServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecioServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecioServicio))
            )
            .andExpect(status().isOk());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
        PrecioServicio testPrecioServicio = precioServicioList.get(precioServicioList.size() - 1);
        assertThat(testPrecioServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPrecioServicio.getCosto()).isEqualByComparingTo(UPDATED_COSTO);
        assertThat(testPrecioServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPrecioServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPrecioServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPrecioServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPrecioServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();
        precioServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, precioServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();
        precioServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();
        precioServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(precioServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrecioServicio() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        int databaseSizeBeforeDelete = precioServicioRepository.findAll().size();

        // Delete the precioServicio
        restPrecioServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, precioServicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
