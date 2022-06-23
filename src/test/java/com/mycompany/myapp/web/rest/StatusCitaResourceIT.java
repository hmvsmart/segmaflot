package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.StatusCita;
import com.mycompany.myapp.domain.enumeration.TipoStatusCita;
import com.mycompany.myapp.repository.StatusCitaRepository;
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
 * Integration tests for the {@link StatusCitaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatusCitaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final TipoStatusCita DEFAULT_STATUS = TipoStatusCita.Activa;
    private static final TipoStatusCita UPDATED_STATUS = TipoStatusCita.Cancelada;

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

    private static final String ENTITY_API_URL = "/api/status-citas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusCitaRepository statusCitaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusCitaMockMvc;

    private StatusCita statusCita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusCita createEntity(EntityManager em) {
        StatusCita statusCita = new StatusCita()
            .fecha(DEFAULT_FECHA)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return statusCita;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusCita createUpdatedEntity(EntityManager em) {
        StatusCita statusCita = new StatusCita()
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return statusCita;
    }

    @BeforeEach
    public void initTest() {
        statusCita = createEntity(em);
    }

    @Test
    @Transactional
    void createStatusCita() throws Exception {
        int databaseSizeBeforeCreate = statusCitaRepository.findAll().size();
        // Create the StatusCita
        restStatusCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusCita)))
            .andExpect(status().isCreated());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeCreate + 1);
        StatusCita testStatusCita = statusCitaList.get(statusCitaList.size() - 1);
        assertThat(testStatusCita.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testStatusCita.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStatusCita.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testStatusCita.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testStatusCita.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testStatusCita.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testStatusCita.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createStatusCitaWithExistingId() throws Exception {
        // Create the StatusCita with an existing ID
        statusCita.setId(1L);

        int databaseSizeBeforeCreate = statusCitaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusCita)))
            .andExpect(status().isBadRequest());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatusCitas() throws Exception {
        // Initialize the database
        statusCitaRepository.saveAndFlush(statusCita);

        // Get all the statusCitaList
        restStatusCitaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusCita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getStatusCita() throws Exception {
        // Initialize the database
        statusCitaRepository.saveAndFlush(statusCita);

        // Get the statusCita
        restStatusCitaMockMvc
            .perform(get(ENTITY_API_URL_ID, statusCita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusCita.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingStatusCita() throws Exception {
        // Get the statusCita
        restStatusCitaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatusCita() throws Exception {
        // Initialize the database
        statusCitaRepository.saveAndFlush(statusCita);

        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();

        // Update the statusCita
        StatusCita updatedStatusCita = statusCitaRepository.findById(statusCita.getId()).get();
        // Disconnect from session so that the updates on updatedStatusCita are not directly saved in db
        em.detach(updatedStatusCita);
        updatedStatusCita
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restStatusCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStatusCita.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStatusCita))
            )
            .andExpect(status().isOk());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
        StatusCita testStatusCita = statusCitaList.get(statusCitaList.size() - 1);
        assertThat(testStatusCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testStatusCita.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStatusCita.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testStatusCita.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testStatusCita.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testStatusCita.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testStatusCita.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingStatusCita() throws Exception {
        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();
        statusCita.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusCita.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusCita))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatusCita() throws Exception {
        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();
        statusCita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusCita))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatusCita() throws Exception {
        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();
        statusCita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusCitaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusCita)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusCitaWithPatch() throws Exception {
        // Initialize the database
        statusCitaRepository.saveAndFlush(statusCita);

        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();

        // Update the statusCita using partial update
        StatusCita partialUpdatedStatusCita = new StatusCita();
        partialUpdatedStatusCita.setId(statusCita.getId());

        partialUpdatedStatusCita.fecha(UPDATED_FECHA).createByUser(UPDATED_CREATE_BY_USER).createdOn(UPDATED_CREATED_ON);

        restStatusCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusCita))
            )
            .andExpect(status().isOk());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
        StatusCita testStatusCita = statusCitaList.get(statusCitaList.size() - 1);
        assertThat(testStatusCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testStatusCita.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStatusCita.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testStatusCita.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testStatusCita.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testStatusCita.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testStatusCita.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateStatusCitaWithPatch() throws Exception {
        // Initialize the database
        statusCitaRepository.saveAndFlush(statusCita);

        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();

        // Update the statusCita using partial update
        StatusCita partialUpdatedStatusCita = new StatusCita();
        partialUpdatedStatusCita.setId(statusCita.getId());

        partialUpdatedStatusCita
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restStatusCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusCita))
            )
            .andExpect(status().isOk());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
        StatusCita testStatusCita = statusCitaList.get(statusCitaList.size() - 1);
        assertThat(testStatusCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testStatusCita.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStatusCita.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testStatusCita.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testStatusCita.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testStatusCita.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testStatusCita.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingStatusCita() throws Exception {
        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();
        statusCita.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusCita))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatusCita() throws Exception {
        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();
        statusCita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusCita))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatusCita() throws Exception {
        int databaseSizeBeforeUpdate = statusCitaRepository.findAll().size();
        statusCita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusCitaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(statusCita))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusCita in the database
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatusCita() throws Exception {
        // Initialize the database
        statusCitaRepository.saveAndFlush(statusCita);

        int databaseSizeBeforeDelete = statusCitaRepository.findAll().size();

        // Delete the statusCita
        restStatusCitaMockMvc
            .perform(delete(ENTITY_API_URL_ID, statusCita.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusCita> statusCitaList = statusCitaRepository.findAll();
        assertThat(statusCitaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
