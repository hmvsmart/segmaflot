package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.OperadorUnidad;
import com.mycompany.myapp.repository.OperadorUnidadRepository;
import java.time.Instant;
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
 * Integration tests for the {@link OperadorUnidadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperadorUnidadResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

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

    private static final String ENTITY_API_URL = "/api/operador-unidads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperadorUnidadRepository operadorUnidadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperadorUnidadMockMvc;

    private OperadorUnidad operadorUnidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperadorUnidad createEntity(EntityManager em) {
        OperadorUnidad operadorUnidad = new OperadorUnidad()
            .fecha(DEFAULT_FECHA)
            .rol(DEFAULT_ROL)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return operadorUnidad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperadorUnidad createUpdatedEntity(EntityManager em) {
        OperadorUnidad operadorUnidad = new OperadorUnidad()
            .fecha(UPDATED_FECHA)
            .rol(UPDATED_ROL)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return operadorUnidad;
    }

    @BeforeEach
    public void initTest() {
        operadorUnidad = createEntity(em);
    }

    @Test
    @Transactional
    void createOperadorUnidad() throws Exception {
        int databaseSizeBeforeCreate = operadorUnidadRepository.findAll().size();
        // Create the OperadorUnidad
        restOperadorUnidadMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isCreated());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeCreate + 1);
        OperadorUnidad testOperadorUnidad = operadorUnidadList.get(operadorUnidadList.size() - 1);
        assertThat(testOperadorUnidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOperadorUnidad.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testOperadorUnidad.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOperadorUnidad.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testOperadorUnidad.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOperadorUnidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testOperadorUnidad.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testOperadorUnidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createOperadorUnidadWithExistingId() throws Exception {
        // Create the OperadorUnidad with an existing ID
        operadorUnidad.setId(1L);

        int databaseSizeBeforeCreate = operadorUnidadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperadorUnidadMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperadorUnidads() throws Exception {
        // Initialize the database
        operadorUnidadRepository.saveAndFlush(operadorUnidad);

        // Get all the operadorUnidadList
        restOperadorUnidadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operadorUnidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getOperadorUnidad() throws Exception {
        // Initialize the database
        operadorUnidadRepository.saveAndFlush(operadorUnidad);

        // Get the operadorUnidad
        restOperadorUnidadMockMvc
            .perform(get(ENTITY_API_URL_ID, operadorUnidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operadorUnidad.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingOperadorUnidad() throws Exception {
        // Get the operadorUnidad
        restOperadorUnidadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOperadorUnidad() throws Exception {
        // Initialize the database
        operadorUnidadRepository.saveAndFlush(operadorUnidad);

        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();

        // Update the operadorUnidad
        OperadorUnidad updatedOperadorUnidad = operadorUnidadRepository.findById(operadorUnidad.getId()).get();
        // Disconnect from session so that the updates on updatedOperadorUnidad are not directly saved in db
        em.detach(updatedOperadorUnidad);
        updatedOperadorUnidad
            .fecha(UPDATED_FECHA)
            .rol(UPDATED_ROL)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restOperadorUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOperadorUnidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOperadorUnidad))
            )
            .andExpect(status().isOk());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
        OperadorUnidad testOperadorUnidad = operadorUnidadList.get(operadorUnidadList.size() - 1);
        assertThat(testOperadorUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOperadorUnidad.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testOperadorUnidad.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOperadorUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testOperadorUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOperadorUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testOperadorUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testOperadorUnidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOperadorUnidad() throws Exception {
        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();
        operadorUnidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperadorUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operadorUnidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperadorUnidad() throws Exception {
        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();
        operadorUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperadorUnidad() throws Exception {
        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();
        operadorUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorUnidadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operadorUnidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperadorUnidadWithPatch() throws Exception {
        // Initialize the database
        operadorUnidadRepository.saveAndFlush(operadorUnidad);

        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();

        // Update the operadorUnidad using partial update
        OperadorUnidad partialUpdatedOperadorUnidad = new OperadorUnidad();
        partialUpdatedOperadorUnidad.setId(operadorUnidad.getId());

        partialUpdatedOperadorUnidad
            .rol(UPDATED_ROL)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON);

        restOperadorUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperadorUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperadorUnidad))
            )
            .andExpect(status().isOk());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
        OperadorUnidad testOperadorUnidad = operadorUnidadList.get(operadorUnidadList.size() - 1);
        assertThat(testOperadorUnidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOperadorUnidad.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testOperadorUnidad.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOperadorUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testOperadorUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOperadorUnidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testOperadorUnidad.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testOperadorUnidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOperadorUnidadWithPatch() throws Exception {
        // Initialize the database
        operadorUnidadRepository.saveAndFlush(operadorUnidad);

        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();

        // Update the operadorUnidad using partial update
        OperadorUnidad partialUpdatedOperadorUnidad = new OperadorUnidad();
        partialUpdatedOperadorUnidad.setId(operadorUnidad.getId());

        partialUpdatedOperadorUnidad
            .fecha(UPDATED_FECHA)
            .rol(UPDATED_ROL)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restOperadorUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperadorUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperadorUnidad))
            )
            .andExpect(status().isOk());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
        OperadorUnidad testOperadorUnidad = operadorUnidadList.get(operadorUnidadList.size() - 1);
        assertThat(testOperadorUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOperadorUnidad.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testOperadorUnidad.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOperadorUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testOperadorUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOperadorUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testOperadorUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testOperadorUnidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOperadorUnidad() throws Exception {
        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();
        operadorUnidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperadorUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operadorUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperadorUnidad() throws Exception {
        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();
        operadorUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperadorUnidad() throws Exception {
        int databaseSizeBeforeUpdate = operadorUnidadRepository.findAll().size();
        operadorUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperadorUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(operadorUnidad))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperadorUnidad in the database
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperadorUnidad() throws Exception {
        // Initialize the database
        operadorUnidadRepository.saveAndFlush(operadorUnidad);

        int databaseSizeBeforeDelete = operadorUnidadRepository.findAll().size();

        // Delete the operadorUnidad
        restOperadorUnidadMockMvc
            .perform(delete(ENTITY_API_URL_ID, operadorUnidad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperadorUnidad> operadorUnidadList = operadorUnidadRepository.findAll();
        assertThat(operadorUnidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
