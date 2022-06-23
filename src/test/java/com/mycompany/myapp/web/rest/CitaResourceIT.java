package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cita;
import com.mycompany.myapp.repository.CitaRepository;
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
 * Integration tests for the {@link CitaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICADOR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICADOR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/citas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitaMockMvc;

    private Cita cita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createEntity(EntityManager em) {
        Cita cita = new Cita()
            .fecha(DEFAULT_FECHA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFin(DEFAULT_HORA_FIN)
            .area(DEFAULT_AREA)
            .identificador(DEFAULT_IDENTIFICADOR)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return cita;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createUpdatedEntity(EntityManager em) {
        Cita cita = new Cita()
            .fecha(UPDATED_FECHA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFin(UPDATED_HORA_FIN)
            .area(UPDATED_AREA)
            .identificador(UPDATED_IDENTIFICADOR)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return cita;
    }

    @BeforeEach
    public void initTest() {
        cita = createEntity(em);
    }

    @Test
    @Transactional
    void createCita() throws Exception {
        int databaseSizeBeforeCreate = citaRepository.findAll().size();
        // Create the Cita
        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cita)))
            .andExpect(status().isCreated());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeCreate + 1);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCita.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testCita.getHoraFin()).isEqualTo(DEFAULT_HORA_FIN);
        assertThat(testCita.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testCita.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testCita.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testCita.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testCita.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testCita.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testCita.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createCitaWithExistingId() throws Exception {
        // Create the Cita with an existing ID
        cita.setId(1L);

        int databaseSizeBeforeCreate = citaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cita)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCitas() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList
        restCitaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFin").value(hasItem(DEFAULT_HORA_FIN.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get the cita
        restCitaMockMvc
            .perform(get(ENTITY_API_URL_ID, cita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cita.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFin").value(DEFAULT_HORA_FIN.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingCita() throws Exception {
        // Get the cita
        restCitaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita
        Cita updatedCita = citaRepository.findById(cita.getId()).get();
        // Disconnect from session so that the updates on updatedCita are not directly saved in db
        em.detach(updatedCita);
        updatedCita
            .fecha(UPDATED_FECHA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFin(UPDATED_HORA_FIN)
            .area(UPDATED_AREA)
            .identificador(UPDATED_IDENTIFICADOR)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCita.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCita.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testCita.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
        assertThat(testCita.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCita.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testCita.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testCita.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCita.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testCita.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testCita.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cita.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cita))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cita))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cita)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fecha(UPDATED_FECHA)
            .horaFin(UPDATED_HORA_FIN)
            .area(UPDATED_AREA)
            .identificador(UPDATED_IDENTIFICADOR)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCita.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testCita.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
        assertThat(testCita.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCita.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testCita.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testCita.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCita.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testCita.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testCita.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fecha(UPDATED_FECHA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFin(UPDATED_HORA_FIN)
            .area(UPDATED_AREA)
            .identificador(UPDATED_IDENTIFICADOR)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCita.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testCita.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
        assertThat(testCita.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCita.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testCita.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testCita.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testCita.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testCita.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testCita.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cita.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cita))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cita))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();
        cita.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cita)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeDelete = citaRepository.findAll().size();

        // Delete the cita
        restCitaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cita.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
