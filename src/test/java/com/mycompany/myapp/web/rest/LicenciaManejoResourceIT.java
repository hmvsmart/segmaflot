package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.LicenciaManejo;
import com.mycompany.myapp.repository.LicenciaManejoRepository;
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
 * Integration tests for the {@link LicenciaManejoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LicenciaManejoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final LocalDate DEFAULT_FECHA_EXPIRACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EXPIRACION = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/licencia-manejos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LicenciaManejoRepository licenciaManejoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenciaManejoMockMvc;

    private LicenciaManejo licenciaManejo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenciaManejo createEntity(EntityManager em) {
        LicenciaManejo licenciaManejo = new LicenciaManejo()
            .fecha(DEFAULT_FECHA)
            .tipo(DEFAULT_TIPO)
            .status(DEFAULT_STATUS)
            .fechaExpiracion(DEFAULT_FECHA_EXPIRACION)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return licenciaManejo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenciaManejo createUpdatedEntity(EntityManager em) {
        LicenciaManejo licenciaManejo = new LicenciaManejo()
            .fecha(UPDATED_FECHA)
            .tipo(UPDATED_TIPO)
            .status(UPDATED_STATUS)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return licenciaManejo;
    }

    @BeforeEach
    public void initTest() {
        licenciaManejo = createEntity(em);
    }

    @Test
    @Transactional
    void createLicenciaManejo() throws Exception {
        int databaseSizeBeforeCreate = licenciaManejoRepository.findAll().size();
        // Create the LicenciaManejo
        restLicenciaManejoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isCreated());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeCreate + 1);
        LicenciaManejo testLicenciaManejo = licenciaManejoList.get(licenciaManejoList.size() - 1);
        assertThat(testLicenciaManejo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testLicenciaManejo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testLicenciaManejo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLicenciaManejo.getFechaExpiracion()).isEqualTo(DEFAULT_FECHA_EXPIRACION);
        assertThat(testLicenciaManejo.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testLicenciaManejo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLicenciaManejo.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testLicenciaManejo.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testLicenciaManejo.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createLicenciaManejoWithExistingId() throws Exception {
        // Create the LicenciaManejo with an existing ID
        licenciaManejo.setId(1L);

        int databaseSizeBeforeCreate = licenciaManejoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenciaManejoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLicenciaManejos() throws Exception {
        // Initialize the database
        licenciaManejoRepository.saveAndFlush(licenciaManejo);

        // Get all the licenciaManejoList
        restLicenciaManejoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenciaManejo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaExpiracion").value(hasItem(DEFAULT_FECHA_EXPIRACION.toString())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getLicenciaManejo() throws Exception {
        // Initialize the database
        licenciaManejoRepository.saveAndFlush(licenciaManejo);

        // Get the licenciaManejo
        restLicenciaManejoMockMvc
            .perform(get(ENTITY_API_URL_ID, licenciaManejo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licenciaManejo.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.fechaExpiracion").value(DEFAULT_FECHA_EXPIRACION.toString()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingLicenciaManejo() throws Exception {
        // Get the licenciaManejo
        restLicenciaManejoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLicenciaManejo() throws Exception {
        // Initialize the database
        licenciaManejoRepository.saveAndFlush(licenciaManejo);

        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();

        // Update the licenciaManejo
        LicenciaManejo updatedLicenciaManejo = licenciaManejoRepository.findById(licenciaManejo.getId()).get();
        // Disconnect from session so that the updates on updatedLicenciaManejo are not directly saved in db
        em.detach(updatedLicenciaManejo);
        updatedLicenciaManejo
            .fecha(UPDATED_FECHA)
            .tipo(UPDATED_TIPO)
            .status(UPDATED_STATUS)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restLicenciaManejoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLicenciaManejo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLicenciaManejo))
            )
            .andExpect(status().isOk());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
        LicenciaManejo testLicenciaManejo = licenciaManejoList.get(licenciaManejoList.size() - 1);
        assertThat(testLicenciaManejo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testLicenciaManejo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLicenciaManejo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLicenciaManejo.getFechaExpiracion()).isEqualTo(UPDATED_FECHA_EXPIRACION);
        assertThat(testLicenciaManejo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testLicenciaManejo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLicenciaManejo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testLicenciaManejo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testLicenciaManejo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingLicenciaManejo() throws Exception {
        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();
        licenciaManejo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenciaManejoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenciaManejo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLicenciaManejo() throws Exception {
        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();
        licenciaManejo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenciaManejoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLicenciaManejo() throws Exception {
        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();
        licenciaManejo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenciaManejoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenciaManejo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLicenciaManejoWithPatch() throws Exception {
        // Initialize the database
        licenciaManejoRepository.saveAndFlush(licenciaManejo);

        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();

        // Update the licenciaManejo using partial update
        LicenciaManejo partialUpdatedLicenciaManejo = new LicenciaManejo();
        partialUpdatedLicenciaManejo.setId(licenciaManejo.getId());

        partialUpdatedLicenciaManejo
            .tipo(UPDATED_TIPO)
            .status(UPDATED_STATUS)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restLicenciaManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicenciaManejo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicenciaManejo))
            )
            .andExpect(status().isOk());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
        LicenciaManejo testLicenciaManejo = licenciaManejoList.get(licenciaManejoList.size() - 1);
        assertThat(testLicenciaManejo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testLicenciaManejo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLicenciaManejo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLicenciaManejo.getFechaExpiracion()).isEqualTo(UPDATED_FECHA_EXPIRACION);
        assertThat(testLicenciaManejo.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testLicenciaManejo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLicenciaManejo.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testLicenciaManejo.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testLicenciaManejo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateLicenciaManejoWithPatch() throws Exception {
        // Initialize the database
        licenciaManejoRepository.saveAndFlush(licenciaManejo);

        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();

        // Update the licenciaManejo using partial update
        LicenciaManejo partialUpdatedLicenciaManejo = new LicenciaManejo();
        partialUpdatedLicenciaManejo.setId(licenciaManejo.getId());

        partialUpdatedLicenciaManejo
            .fecha(UPDATED_FECHA)
            .tipo(UPDATED_TIPO)
            .status(UPDATED_STATUS)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restLicenciaManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicenciaManejo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicenciaManejo))
            )
            .andExpect(status().isOk());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
        LicenciaManejo testLicenciaManejo = licenciaManejoList.get(licenciaManejoList.size() - 1);
        assertThat(testLicenciaManejo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testLicenciaManejo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLicenciaManejo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLicenciaManejo.getFechaExpiracion()).isEqualTo(UPDATED_FECHA_EXPIRACION);
        assertThat(testLicenciaManejo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testLicenciaManejo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLicenciaManejo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testLicenciaManejo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testLicenciaManejo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingLicenciaManejo() throws Exception {
        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();
        licenciaManejo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenciaManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, licenciaManejo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLicenciaManejo() throws Exception {
        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();
        licenciaManejo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenciaManejoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLicenciaManejo() throws Exception {
        int databaseSizeBeforeUpdate = licenciaManejoRepository.findAll().size();
        licenciaManejo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenciaManejoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(licenciaManejo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LicenciaManejo in the database
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLicenciaManejo() throws Exception {
        // Initialize the database
        licenciaManejoRepository.saveAndFlush(licenciaManejo);

        int databaseSizeBeforeDelete = licenciaManejoRepository.findAll().size();

        // Delete the licenciaManejo
        restLicenciaManejoMockMvc
            .perform(delete(ENTITY_API_URL_ID, licenciaManejo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LicenciaManejo> licenciaManejoList = licenciaManejoRepository.findAll();
        assertThat(licenciaManejoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
