package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Poliza;
import com.mycompany.myapp.repository.PolizaRepository;
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
 * Integration tests for the {@link PolizaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PolizaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_POLIZA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_POLIZA = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/polizas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PolizaRepository polizaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolizaMockMvc;

    private Poliza poliza;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poliza createEntity(EntityManager em) {
        Poliza poliza = new Poliza()
            .fecha(DEFAULT_FECHA)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .numeroPoliza(DEFAULT_NUMERO_POLIZA)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return poliza;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poliza createUpdatedEntity(EntityManager em) {
        Poliza poliza = new Poliza()
            .fecha(UPDATED_FECHA)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .numeroPoliza(UPDATED_NUMERO_POLIZA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return poliza;
    }

    @BeforeEach
    public void initTest() {
        poliza = createEntity(em);
    }

    @Test
    @Transactional
    void createPoliza() throws Exception {
        int databaseSizeBeforeCreate = polizaRepository.findAll().size();
        // Create the Poliza
        restPolizaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poliza)))
            .andExpect(status().isCreated());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeCreate + 1);
        Poliza testPoliza = polizaList.get(polizaList.size() - 1);
        assertThat(testPoliza.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPoliza.getFechaVencimiento()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO);
        assertThat(testPoliza.getNumeroPoliza()).isEqualTo(DEFAULT_NUMERO_POLIZA);
        assertThat(testPoliza.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPoliza.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPoliza.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPoliza.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPoliza.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createPolizaWithExistingId() throws Exception {
        // Create the Poliza with an existing ID
        poliza.setId(1L);

        int databaseSizeBeforeCreate = polizaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolizaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poliza)))
            .andExpect(status().isBadRequest());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPolizas() throws Exception {
        // Initialize the database
        polizaRepository.saveAndFlush(poliza);

        // Get all the polizaList
        restPolizaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poliza.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].numeroPoliza").value(hasItem(DEFAULT_NUMERO_POLIZA)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getPoliza() throws Exception {
        // Initialize the database
        polizaRepository.saveAndFlush(poliza);

        // Get the poliza
        restPolizaMockMvc
            .perform(get(ENTITY_API_URL_ID, poliza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poliza.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.numeroPoliza").value(DEFAULT_NUMERO_POLIZA))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPoliza() throws Exception {
        // Get the poliza
        restPolizaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPoliza() throws Exception {
        // Initialize the database
        polizaRepository.saveAndFlush(poliza);

        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();

        // Update the poliza
        Poliza updatedPoliza = polizaRepository.findById(poliza.getId()).get();
        // Disconnect from session so that the updates on updatedPoliza are not directly saved in db
        em.detach(updatedPoliza);
        updatedPoliza
            .fecha(UPDATED_FECHA)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .numeroPoliza(UPDATED_NUMERO_POLIZA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPolizaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPoliza.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPoliza))
            )
            .andExpect(status().isOk());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
        Poliza testPoliza = polizaList.get(polizaList.size() - 1);
        assertThat(testPoliza.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPoliza.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testPoliza.getNumeroPoliza()).isEqualTo(UPDATED_NUMERO_POLIZA);
        assertThat(testPoliza.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPoliza.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPoliza.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPoliza.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPoliza.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPoliza() throws Exception {
        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();
        poliza.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolizaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, poliza.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poliza))
            )
            .andExpect(status().isBadRequest());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPoliza() throws Exception {
        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();
        poliza.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolizaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(poliza))
            )
            .andExpect(status().isBadRequest());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPoliza() throws Exception {
        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();
        poliza.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolizaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(poliza)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePolizaWithPatch() throws Exception {
        // Initialize the database
        polizaRepository.saveAndFlush(poliza);

        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();

        // Update the poliza using partial update
        Poliza partialUpdatedPoliza = new Poliza();
        partialUpdatedPoliza.setId(poliza.getId());

        partialUpdatedPoliza.fechaVencimiento(UPDATED_FECHA_VENCIMIENTO).createByUser(UPDATED_CREATE_BY_USER);

        restPolizaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoliza.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoliza))
            )
            .andExpect(status().isOk());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
        Poliza testPoliza = polizaList.get(polizaList.size() - 1);
        assertThat(testPoliza.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPoliza.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testPoliza.getNumeroPoliza()).isEqualTo(DEFAULT_NUMERO_POLIZA);
        assertThat(testPoliza.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPoliza.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPoliza.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPoliza.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPoliza.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePolizaWithPatch() throws Exception {
        // Initialize the database
        polizaRepository.saveAndFlush(poliza);

        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();

        // Update the poliza using partial update
        Poliza partialUpdatedPoliza = new Poliza();
        partialUpdatedPoliza.setId(poliza.getId());

        partialUpdatedPoliza
            .fecha(UPDATED_FECHA)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .numeroPoliza(UPDATED_NUMERO_POLIZA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPolizaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoliza.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoliza))
            )
            .andExpect(status().isOk());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
        Poliza testPoliza = polizaList.get(polizaList.size() - 1);
        assertThat(testPoliza.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPoliza.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testPoliza.getNumeroPoliza()).isEqualTo(UPDATED_NUMERO_POLIZA);
        assertThat(testPoliza.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPoliza.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPoliza.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPoliza.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPoliza.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPoliza() throws Exception {
        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();
        poliza.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolizaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, poliza.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poliza))
            )
            .andExpect(status().isBadRequest());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPoliza() throws Exception {
        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();
        poliza.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolizaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(poliza))
            )
            .andExpect(status().isBadRequest());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPoliza() throws Exception {
        int databaseSizeBeforeUpdate = polizaRepository.findAll().size();
        poliza.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolizaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(poliza)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Poliza in the database
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePoliza() throws Exception {
        // Initialize the database
        polizaRepository.saveAndFlush(poliza);

        int databaseSizeBeforeDelete = polizaRepository.findAll().size();

        // Delete the poliza
        restPolizaMockMvc
            .perform(delete(ENTITY_API_URL_ID, poliza.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poliza> polizaList = polizaRepository.findAll();
        assertThat(polizaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
