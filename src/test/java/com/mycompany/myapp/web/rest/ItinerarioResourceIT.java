package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Itinerario;
import com.mycompany.myapp.repository.ItinerarioRepository;
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
 * Integration tests for the {@link ItinerarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItinerarioResourceIT {

    private static final String DEFAULT_ACCION = "AAAAAAAAAA";
    private static final String UPDATED_ACCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_HORA_ESTIMADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA_ESTIMADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/itinerarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItinerarioMockMvc;

    private Itinerario itinerario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Itinerario createEntity(EntityManager em) {
        Itinerario itinerario = new Itinerario()
            .accion(DEFAULT_ACCION)
            .fechaHoraEstimada(DEFAULT_FECHA_HORA_ESTIMADA)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return itinerario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Itinerario createUpdatedEntity(EntityManager em) {
        Itinerario itinerario = new Itinerario()
            .accion(UPDATED_ACCION)
            .fechaHoraEstimada(UPDATED_FECHA_HORA_ESTIMADA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return itinerario;
    }

    @BeforeEach
    public void initTest() {
        itinerario = createEntity(em);
    }

    @Test
    @Transactional
    void createItinerario() throws Exception {
        int databaseSizeBeforeCreate = itinerarioRepository.findAll().size();
        // Create the Itinerario
        restItinerarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itinerario)))
            .andExpect(status().isCreated());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeCreate + 1);
        Itinerario testItinerario = itinerarioList.get(itinerarioList.size() - 1);
        assertThat(testItinerario.getAccion()).isEqualTo(DEFAULT_ACCION);
        assertThat(testItinerario.getFechaHoraEstimada()).isEqualTo(DEFAULT_FECHA_HORA_ESTIMADA);
        assertThat(testItinerario.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testItinerario.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testItinerario.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testItinerario.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testItinerario.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createItinerarioWithExistingId() throws Exception {
        // Create the Itinerario with an existing ID
        itinerario.setId(1L);

        int databaseSizeBeforeCreate = itinerarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItinerarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itinerario)))
            .andExpect(status().isBadRequest());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItinerarios() throws Exception {
        // Initialize the database
        itinerarioRepository.saveAndFlush(itinerario);

        // Get all the itinerarioList
        restItinerarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itinerario.getId().intValue())))
            .andExpect(jsonPath("$.[*].accion").value(hasItem(DEFAULT_ACCION)))
            .andExpect(jsonPath("$.[*].fechaHoraEstimada").value(hasItem(DEFAULT_FECHA_HORA_ESTIMADA.toString())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getItinerario() throws Exception {
        // Initialize the database
        itinerarioRepository.saveAndFlush(itinerario);

        // Get the itinerario
        restItinerarioMockMvc
            .perform(get(ENTITY_API_URL_ID, itinerario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itinerario.getId().intValue()))
            .andExpect(jsonPath("$.accion").value(DEFAULT_ACCION))
            .andExpect(jsonPath("$.fechaHoraEstimada").value(DEFAULT_FECHA_HORA_ESTIMADA.toString()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingItinerario() throws Exception {
        // Get the itinerario
        restItinerarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItinerario() throws Exception {
        // Initialize the database
        itinerarioRepository.saveAndFlush(itinerario);

        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();

        // Update the itinerario
        Itinerario updatedItinerario = itinerarioRepository.findById(itinerario.getId()).get();
        // Disconnect from session so that the updates on updatedItinerario are not directly saved in db
        em.detach(updatedItinerario);
        updatedItinerario
            .accion(UPDATED_ACCION)
            .fechaHoraEstimada(UPDATED_FECHA_HORA_ESTIMADA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restItinerarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItinerario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItinerario))
            )
            .andExpect(status().isOk());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
        Itinerario testItinerario = itinerarioList.get(itinerarioList.size() - 1);
        assertThat(testItinerario.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testItinerario.getFechaHoraEstimada()).isEqualTo(UPDATED_FECHA_HORA_ESTIMADA);
        assertThat(testItinerario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testItinerario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testItinerario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testItinerario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testItinerario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingItinerario() throws Exception {
        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();
        itinerario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItinerarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itinerario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itinerario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItinerario() throws Exception {
        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();
        itinerario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItinerarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itinerario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItinerario() throws Exception {
        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();
        itinerario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItinerarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itinerario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItinerarioWithPatch() throws Exception {
        // Initialize the database
        itinerarioRepository.saveAndFlush(itinerario);

        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();

        // Update the itinerario using partial update
        Itinerario partialUpdatedItinerario = new Itinerario();
        partialUpdatedItinerario.setId(itinerario.getId());

        partialUpdatedItinerario
            .accion(UPDATED_ACCION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restItinerarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItinerario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItinerario))
            )
            .andExpect(status().isOk());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
        Itinerario testItinerario = itinerarioList.get(itinerarioList.size() - 1);
        assertThat(testItinerario.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testItinerario.getFechaHoraEstimada()).isEqualTo(DEFAULT_FECHA_HORA_ESTIMADA);
        assertThat(testItinerario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testItinerario.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testItinerario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testItinerario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testItinerario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateItinerarioWithPatch() throws Exception {
        // Initialize the database
        itinerarioRepository.saveAndFlush(itinerario);

        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();

        // Update the itinerario using partial update
        Itinerario partialUpdatedItinerario = new Itinerario();
        partialUpdatedItinerario.setId(itinerario.getId());

        partialUpdatedItinerario
            .accion(UPDATED_ACCION)
            .fechaHoraEstimada(UPDATED_FECHA_HORA_ESTIMADA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restItinerarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItinerario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItinerario))
            )
            .andExpect(status().isOk());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
        Itinerario testItinerario = itinerarioList.get(itinerarioList.size() - 1);
        assertThat(testItinerario.getAccion()).isEqualTo(UPDATED_ACCION);
        assertThat(testItinerario.getFechaHoraEstimada()).isEqualTo(UPDATED_FECHA_HORA_ESTIMADA);
        assertThat(testItinerario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testItinerario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testItinerario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testItinerario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testItinerario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingItinerario() throws Exception {
        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();
        itinerario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItinerarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itinerario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itinerario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItinerario() throws Exception {
        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();
        itinerario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItinerarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itinerario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItinerario() throws Exception {
        int databaseSizeBeforeUpdate = itinerarioRepository.findAll().size();
        itinerario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItinerarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itinerario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Itinerario in the database
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItinerario() throws Exception {
        // Initialize the database
        itinerarioRepository.saveAndFlush(itinerario);

        int databaseSizeBeforeDelete = itinerarioRepository.findAll().size();

        // Delete the itinerario
        restItinerarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, itinerario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Itinerario> itinerarioList = itinerarioRepository.findAll();
        assertThat(itinerarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
