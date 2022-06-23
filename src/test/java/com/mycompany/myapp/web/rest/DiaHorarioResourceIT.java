package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DiaHorario;
import com.mycompany.myapp.repository.DiaHorarioRepository;
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
 * Integration tests for the {@link DiaHorarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiaHorarioResourceIT {

    private static final Integer DEFAULT_DIA = 1;
    private static final Integer UPDATED_DIA = 2;

    private static final Instant DEFAULT_HORA_ENTRADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_ENTRADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_SALIDA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_SALIDA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/dia-horarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiaHorarioRepository diaHorarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiaHorarioMockMvc;

    private DiaHorario diaHorario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaHorario createEntity(EntityManager em) {
        DiaHorario diaHorario = new DiaHorario()
            .dia(DEFAULT_DIA)
            .horaEntrada(DEFAULT_HORA_ENTRADA)
            .horaSalida(DEFAULT_HORA_SALIDA)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return diaHorario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiaHorario createUpdatedEntity(EntityManager em) {
        DiaHorario diaHorario = new DiaHorario()
            .dia(UPDATED_DIA)
            .horaEntrada(UPDATED_HORA_ENTRADA)
            .horaSalida(UPDATED_HORA_SALIDA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return diaHorario;
    }

    @BeforeEach
    public void initTest() {
        diaHorario = createEntity(em);
    }

    @Test
    @Transactional
    void createDiaHorario() throws Exception {
        int databaseSizeBeforeCreate = diaHorarioRepository.findAll().size();
        // Create the DiaHorario
        restDiaHorarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaHorario)))
            .andExpect(status().isCreated());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeCreate + 1);
        DiaHorario testDiaHorario = diaHorarioList.get(diaHorarioList.size() - 1);
        assertThat(testDiaHorario.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testDiaHorario.getHoraEntrada()).isEqualTo(DEFAULT_HORA_ENTRADA);
        assertThat(testDiaHorario.getHoraSalida()).isEqualTo(DEFAULT_HORA_SALIDA);
        assertThat(testDiaHorario.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testDiaHorario.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDiaHorario.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testDiaHorario.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testDiaHorario.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createDiaHorarioWithExistingId() throws Exception {
        // Create the DiaHorario with an existing ID
        diaHorario.setId(1L);

        int databaseSizeBeforeCreate = diaHorarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaHorarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaHorario)))
            .andExpect(status().isBadRequest());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiaHorarios() throws Exception {
        // Initialize the database
        diaHorarioRepository.saveAndFlush(diaHorario);

        // Get all the diaHorarioList
        restDiaHorarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaHorario.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA)))
            .andExpect(jsonPath("$.[*].horaEntrada").value(hasItem(DEFAULT_HORA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].horaSalida").value(hasItem(DEFAULT_HORA_SALIDA.toString())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getDiaHorario() throws Exception {
        // Initialize the database
        diaHorarioRepository.saveAndFlush(diaHorario);

        // Get the diaHorario
        restDiaHorarioMockMvc
            .perform(get(ENTITY_API_URL_ID, diaHorario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diaHorario.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA))
            .andExpect(jsonPath("$.horaEntrada").value(DEFAULT_HORA_ENTRADA.toString()))
            .andExpect(jsonPath("$.horaSalida").value(DEFAULT_HORA_SALIDA.toString()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingDiaHorario() throws Exception {
        // Get the diaHorario
        restDiaHorarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiaHorario() throws Exception {
        // Initialize the database
        diaHorarioRepository.saveAndFlush(diaHorario);

        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();

        // Update the diaHorario
        DiaHorario updatedDiaHorario = diaHorarioRepository.findById(diaHorario.getId()).get();
        // Disconnect from session so that the updates on updatedDiaHorario are not directly saved in db
        em.detach(updatedDiaHorario);
        updatedDiaHorario
            .dia(UPDATED_DIA)
            .horaEntrada(UPDATED_HORA_ENTRADA)
            .horaSalida(UPDATED_HORA_SALIDA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restDiaHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiaHorario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiaHorario))
            )
            .andExpect(status().isOk());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
        DiaHorario testDiaHorario = diaHorarioList.get(diaHorarioList.size() - 1);
        assertThat(testDiaHorario.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testDiaHorario.getHoraEntrada()).isEqualTo(UPDATED_HORA_ENTRADA);
        assertThat(testDiaHorario.getHoraSalida()).isEqualTo(UPDATED_HORA_SALIDA);
        assertThat(testDiaHorario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testDiaHorario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDiaHorario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testDiaHorario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testDiaHorario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDiaHorario() throws Exception {
        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();
        diaHorario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diaHorario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaHorario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiaHorario() throws Exception {
        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();
        diaHorario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaHorarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diaHorario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiaHorario() throws Exception {
        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();
        diaHorario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaHorarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diaHorario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiaHorarioWithPatch() throws Exception {
        // Initialize the database
        diaHorarioRepository.saveAndFlush(diaHorario);

        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();

        // Update the diaHorario using partial update
        DiaHorario partialUpdatedDiaHorario = new DiaHorario();
        partialUpdatedDiaHorario.setId(diaHorario.getId());

        partialUpdatedDiaHorario
            .horaEntrada(UPDATED_HORA_ENTRADA)
            .horaSalida(UPDATED_HORA_SALIDA)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restDiaHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiaHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaHorario))
            )
            .andExpect(status().isOk());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
        DiaHorario testDiaHorario = diaHorarioList.get(diaHorarioList.size() - 1);
        assertThat(testDiaHorario.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testDiaHorario.getHoraEntrada()).isEqualTo(UPDATED_HORA_ENTRADA);
        assertThat(testDiaHorario.getHoraSalida()).isEqualTo(UPDATED_HORA_SALIDA);
        assertThat(testDiaHorario.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testDiaHorario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDiaHorario.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testDiaHorario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testDiaHorario.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDiaHorarioWithPatch() throws Exception {
        // Initialize the database
        diaHorarioRepository.saveAndFlush(diaHorario);

        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();

        // Update the diaHorario using partial update
        DiaHorario partialUpdatedDiaHorario = new DiaHorario();
        partialUpdatedDiaHorario.setId(diaHorario.getId());

        partialUpdatedDiaHorario
            .dia(UPDATED_DIA)
            .horaEntrada(UPDATED_HORA_ENTRADA)
            .horaSalida(UPDATED_HORA_SALIDA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restDiaHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiaHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaHorario))
            )
            .andExpect(status().isOk());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
        DiaHorario testDiaHorario = diaHorarioList.get(diaHorarioList.size() - 1);
        assertThat(testDiaHorario.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testDiaHorario.getHoraEntrada()).isEqualTo(UPDATED_HORA_ENTRADA);
        assertThat(testDiaHorario.getHoraSalida()).isEqualTo(UPDATED_HORA_SALIDA);
        assertThat(testDiaHorario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testDiaHorario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDiaHorario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testDiaHorario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testDiaHorario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDiaHorario() throws Exception {
        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();
        diaHorario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diaHorario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diaHorario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiaHorario() throws Exception {
        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();
        diaHorario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diaHorario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiaHorario() throws Exception {
        int databaseSizeBeforeUpdate = diaHorarioRepository.findAll().size();
        diaHorario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiaHorarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diaHorario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiaHorario in the database
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiaHorario() throws Exception {
        // Initialize the database
        diaHorarioRepository.saveAndFlush(diaHorario);

        int databaseSizeBeforeDelete = diaHorarioRepository.findAll().size();

        // Delete the diaHorario
        restDiaHorarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, diaHorario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiaHorario> diaHorarioList = diaHorarioRepository.findAll();
        assertThat(diaHorarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
