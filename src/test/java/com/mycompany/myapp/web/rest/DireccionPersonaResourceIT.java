package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DireccionPersona;
import com.mycompany.myapp.repository.DireccionPersonaRepository;
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
 * Integration tests for the {@link DireccionPersonaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DireccionPersonaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/direccion-personas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DireccionPersonaRepository direccionPersonaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDireccionPersonaMockMvc;

    private DireccionPersona direccionPersona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DireccionPersona createEntity(EntityManager em) {
        DireccionPersona direccionPersona = new DireccionPersona()
            .fecha(DEFAULT_FECHA)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return direccionPersona;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DireccionPersona createUpdatedEntity(EntityManager em) {
        DireccionPersona direccionPersona = new DireccionPersona()
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return direccionPersona;
    }

    @BeforeEach
    public void initTest() {
        direccionPersona = createEntity(em);
    }

    @Test
    @Transactional
    void createDireccionPersona() throws Exception {
        int databaseSizeBeforeCreate = direccionPersonaRepository.findAll().size();
        // Create the DireccionPersona
        restDireccionPersonaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isCreated());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeCreate + 1);
        DireccionPersona testDireccionPersona = direccionPersonaList.get(direccionPersonaList.size() - 1);
        assertThat(testDireccionPersona.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testDireccionPersona.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDireccionPersona.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testDireccionPersona.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDireccionPersona.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testDireccionPersona.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testDireccionPersona.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createDireccionPersonaWithExistingId() throws Exception {
        // Create the DireccionPersona with an existing ID
        direccionPersona.setId(1L);

        int databaseSizeBeforeCreate = direccionPersonaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDireccionPersonaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isBadRequest());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDireccionPersonas() throws Exception {
        // Initialize the database
        direccionPersonaRepository.saveAndFlush(direccionPersona);

        // Get all the direccionPersonaList
        restDireccionPersonaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(direccionPersona.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getDireccionPersona() throws Exception {
        // Initialize the database
        direccionPersonaRepository.saveAndFlush(direccionPersona);

        // Get the direccionPersona
        restDireccionPersonaMockMvc
            .perform(get(ENTITY_API_URL_ID, direccionPersona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(direccionPersona.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingDireccionPersona() throws Exception {
        // Get the direccionPersona
        restDireccionPersonaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDireccionPersona() throws Exception {
        // Initialize the database
        direccionPersonaRepository.saveAndFlush(direccionPersona);

        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();

        // Update the direccionPersona
        DireccionPersona updatedDireccionPersona = direccionPersonaRepository.findById(direccionPersona.getId()).get();
        // Disconnect from session so that the updates on updatedDireccionPersona are not directly saved in db
        em.detach(updatedDireccionPersona);
        updatedDireccionPersona
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restDireccionPersonaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDireccionPersona.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDireccionPersona))
            )
            .andExpect(status().isOk());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
        DireccionPersona testDireccionPersona = direccionPersonaList.get(direccionPersonaList.size() - 1);
        assertThat(testDireccionPersona.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testDireccionPersona.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDireccionPersona.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testDireccionPersona.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDireccionPersona.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testDireccionPersona.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testDireccionPersona.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDireccionPersona() throws Exception {
        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();
        direccionPersona.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireccionPersonaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, direccionPersona.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isBadRequest());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDireccionPersona() throws Exception {
        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();
        direccionPersona.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionPersonaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isBadRequest());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDireccionPersona() throws Exception {
        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();
        direccionPersona.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionPersonaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDireccionPersonaWithPatch() throws Exception {
        // Initialize the database
        direccionPersonaRepository.saveAndFlush(direccionPersona);

        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();

        // Update the direccionPersona using partial update
        DireccionPersona partialUpdatedDireccionPersona = new DireccionPersona();
        partialUpdatedDireccionPersona.setId(direccionPersona.getId());

        partialUpdatedDireccionPersona.status(UPDATED_STATUS).auditStatus(UPDATED_AUDIT_STATUS);

        restDireccionPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDireccionPersona.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDireccionPersona))
            )
            .andExpect(status().isOk());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
        DireccionPersona testDireccionPersona = direccionPersonaList.get(direccionPersonaList.size() - 1);
        assertThat(testDireccionPersona.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testDireccionPersona.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDireccionPersona.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testDireccionPersona.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDireccionPersona.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testDireccionPersona.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testDireccionPersona.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDireccionPersonaWithPatch() throws Exception {
        // Initialize the database
        direccionPersonaRepository.saveAndFlush(direccionPersona);

        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();

        // Update the direccionPersona using partial update
        DireccionPersona partialUpdatedDireccionPersona = new DireccionPersona();
        partialUpdatedDireccionPersona.setId(direccionPersona.getId());

        partialUpdatedDireccionPersona
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restDireccionPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDireccionPersona.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDireccionPersona))
            )
            .andExpect(status().isOk());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
        DireccionPersona testDireccionPersona = direccionPersonaList.get(direccionPersonaList.size() - 1);
        assertThat(testDireccionPersona.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testDireccionPersona.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDireccionPersona.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testDireccionPersona.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDireccionPersona.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testDireccionPersona.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testDireccionPersona.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDireccionPersona() throws Exception {
        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();
        direccionPersona.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDireccionPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, direccionPersona.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isBadRequest());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDireccionPersona() throws Exception {
        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();
        direccionPersona.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isBadRequest());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDireccionPersona() throws Exception {
        int databaseSizeBeforeUpdate = direccionPersonaRepository.findAll().size();
        direccionPersona.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDireccionPersonaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(direccionPersona))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DireccionPersona in the database
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDireccionPersona() throws Exception {
        // Initialize the database
        direccionPersonaRepository.saveAndFlush(direccionPersona);

        int databaseSizeBeforeDelete = direccionPersonaRepository.findAll().size();

        // Delete the direccionPersona
        restDireccionPersonaMockMvc
            .perform(delete(ENTITY_API_URL_ID, direccionPersona.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DireccionPersona> direccionPersonaList = direccionPersonaRepository.findAll();
        assertThat(direccionPersonaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
