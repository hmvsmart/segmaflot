package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cotizacion;
import com.mycompany.myapp.repository.CotizacionRepository;
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
 * Integration tests for the {@link CotizacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CotizacionResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/cotizacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCotizacionMockMvc;

    private Cotizacion cotizacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cotizacion createEntity(EntityManager em) {
        Cotizacion cotizacion = new Cotizacion()
            .fecha(DEFAULT_FECHA)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return cotizacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cotizacion createUpdatedEntity(EntityManager em) {
        Cotizacion cotizacion = new Cotizacion()
            .fecha(UPDATED_FECHA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return cotizacion;
    }

    @BeforeEach
    public void initTest() {
        cotizacion = createEntity(em);
    }

    @Test
    @Transactional
    void createCotizacion() throws Exception {
        int databaseSizeBeforeCreate = cotizacionRepository.findAll().size();
        // Create the Cotizacion
        restCotizacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isCreated());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeCreate + 1);
        Cotizacion testCotizacion = cotizacionList.get(cotizacionList.size() - 1);
        assertThat(testCotizacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCotizacion.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testCotizacion.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCotizacion.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testCotizacion.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testCotizacion.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createCotizacionWithExistingId() throws Exception {
        // Create the Cotizacion with an existing ID
        cotizacion.setId(1L);

        int databaseSizeBeforeCreate = cotizacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCotizacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCotizacions() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        // Get all the cotizacionList
        restCotizacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cotizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        // Get the cotizacion
        restCotizacionMockMvc
            .perform(get(ENTITY_API_URL_ID, cotizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cotizacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingCotizacion() throws Exception {
        // Get the cotizacion
        restCotizacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();

        // Update the cotizacion
        Cotizacion updatedCotizacion = cotizacionRepository.findById(cotizacion.getId()).get();
        // Disconnect from session so that the updates on updatedCotizacion are not directly saved in db
        em.detach(updatedCotizacion);
        updatedCotizacion
            .fecha(UPDATED_FECHA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restCotizacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCotizacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCotizacion))
            )
            .andExpect(status().isOk());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
        Cotizacion testCotizacion = cotizacionList.get(cotizacionList.size() - 1);
        assertThat(testCotizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCotizacion.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testCotizacion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCotizacion.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testCotizacion.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testCotizacion.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();
        cotizacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCotizacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cotizacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cotizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();
        cotizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCotizacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cotizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();
        cotizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCotizacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cotizacion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCotizacionWithPatch() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();

        // Update the cotizacion using partial update
        Cotizacion partialUpdatedCotizacion = new Cotizacion();
        partialUpdatedCotizacion.setId(cotizacion.getId());

        partialUpdatedCotizacion
            .fecha(UPDATED_FECHA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restCotizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCotizacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCotizacion))
            )
            .andExpect(status().isOk());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
        Cotizacion testCotizacion = cotizacionList.get(cotizacionList.size() - 1);
        assertThat(testCotizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCotizacion.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testCotizacion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCotizacion.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testCotizacion.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testCotizacion.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCotizacionWithPatch() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();

        // Update the cotizacion using partial update
        Cotizacion partialUpdatedCotizacion = new Cotizacion();
        partialUpdatedCotizacion.setId(cotizacion.getId());

        partialUpdatedCotizacion
            .fecha(UPDATED_FECHA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restCotizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCotizacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCotizacion))
            )
            .andExpect(status().isOk());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
        Cotizacion testCotizacion = cotizacionList.get(cotizacionList.size() - 1);
        assertThat(testCotizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCotizacion.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testCotizacion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCotizacion.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testCotizacion.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testCotizacion.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();
        cotizacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCotizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cotizacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cotizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();
        cotizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCotizacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cotizacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCotizacion() throws Exception {
        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();
        cotizacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCotizacionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cotizacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        int databaseSizeBeforeDelete = cotizacionRepository.findAll().size();

        // Delete the cotizacion
        restCotizacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, cotizacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cotizacion> cotizacionList = cotizacionRepository.findAll();
        assertThat(cotizacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
