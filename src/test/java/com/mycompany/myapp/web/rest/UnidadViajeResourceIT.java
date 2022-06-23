package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UnidadViaje;
import com.mycompany.myapp.repository.UnidadViajeRepository;
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
 * Integration tests for the {@link UnidadViajeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadViajeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/unidad-viajes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnidadViajeRepository unidadViajeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadViajeMockMvc;

    private UnidadViaje unidadViaje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadViaje createEntity(EntityManager em) {
        UnidadViaje unidadViaje = new UnidadViaje()
            .fecha(DEFAULT_FECHA)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return unidadViaje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadViaje createUpdatedEntity(EntityManager em) {
        UnidadViaje unidadViaje = new UnidadViaje()
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return unidadViaje;
    }

    @BeforeEach
    public void initTest() {
        unidadViaje = createEntity(em);
    }

    @Test
    @Transactional
    void createUnidadViaje() throws Exception {
        int databaseSizeBeforeCreate = unidadViajeRepository.findAll().size();
        // Create the UnidadViaje
        restUnidadViajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadViaje)))
            .andExpect(status().isCreated());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadViaje testUnidadViaje = unidadViajeList.get(unidadViajeList.size() - 1);
        assertThat(testUnidadViaje.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUnidadViaje.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUnidadViaje.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testUnidadViaje.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUnidadViaje.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUnidadViaje.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testUnidadViaje.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createUnidadViajeWithExistingId() throws Exception {
        // Create the UnidadViaje with an existing ID
        unidadViaje.setId(1L);

        int databaseSizeBeforeCreate = unidadViajeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadViajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadViaje)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnidadViajes() throws Exception {
        // Initialize the database
        unidadViajeRepository.saveAndFlush(unidadViaje);

        // Get all the unidadViajeList
        restUnidadViajeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadViaje.getId().intValue())))
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
    void getUnidadViaje() throws Exception {
        // Initialize the database
        unidadViajeRepository.saveAndFlush(unidadViaje);

        // Get the unidadViaje
        restUnidadViajeMockMvc
            .perform(get(ENTITY_API_URL_ID, unidadViaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidadViaje.getId().intValue()))
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
    void getNonExistingUnidadViaje() throws Exception {
        // Get the unidadViaje
        restUnidadViajeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnidadViaje() throws Exception {
        // Initialize the database
        unidadViajeRepository.saveAndFlush(unidadViaje);

        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();

        // Update the unidadViaje
        UnidadViaje updatedUnidadViaje = unidadViajeRepository.findById(unidadViaje.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadViaje are not directly saved in db
        em.detach(updatedUnidadViaje);
        updatedUnidadViaje
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadViajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidadViaje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidadViaje))
            )
            .andExpect(status().isOk());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
        UnidadViaje testUnidadViaje = unidadViajeList.get(unidadViajeList.size() - 1);
        assertThat(testUnidadViaje.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidadViaje.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUnidadViaje.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidadViaje.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidadViaje.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUnidadViaje.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidadViaje.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingUnidadViaje() throws Exception {
        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();
        unidadViaje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadViajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidadViaje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadViaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidadViaje() throws Exception {
        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();
        unidadViaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadViajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidadViaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidadViaje() throws Exception {
        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();
        unidadViaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadViajeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidadViaje)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadViajeWithPatch() throws Exception {
        // Initialize the database
        unidadViajeRepository.saveAndFlush(unidadViaje);

        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();

        // Update the unidadViaje using partial update
        UnidadViaje partialUpdatedUnidadViaje = new UnidadViaje();
        partialUpdatedUnidadViaje.setId(unidadViaje.getId());

        partialUpdatedUnidadViaje
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restUnidadViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadViaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadViaje))
            )
            .andExpect(status().isOk());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
        UnidadViaje testUnidadViaje = unidadViajeList.get(unidadViajeList.size() - 1);
        assertThat(testUnidadViaje.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidadViaje.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUnidadViaje.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidadViaje.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidadViaje.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUnidadViaje.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidadViaje.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateUnidadViajeWithPatch() throws Exception {
        // Initialize the database
        unidadViajeRepository.saveAndFlush(unidadViaje);

        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();

        // Update the unidadViaje using partial update
        UnidadViaje partialUpdatedUnidadViaje = new UnidadViaje();
        partialUpdatedUnidadViaje.setId(unidadViaje.getId());

        partialUpdatedUnidadViaje
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidadViaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidadViaje))
            )
            .andExpect(status().isOk());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
        UnidadViaje testUnidadViaje = unidadViajeList.get(unidadViajeList.size() - 1);
        assertThat(testUnidadViaje.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidadViaje.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUnidadViaje.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidadViaje.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidadViaje.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUnidadViaje.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidadViaje.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingUnidadViaje() throws Exception {
        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();
        unidadViaje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidadViaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadViaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidadViaje() throws Exception {
        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();
        unidadViaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidadViaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidadViaje() throws Exception {
        int databaseSizeBeforeUpdate = unidadViajeRepository.findAll().size();
        unidadViaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadViajeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unidadViaje))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UnidadViaje in the database
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidadViaje() throws Exception {
        // Initialize the database
        unidadViajeRepository.saveAndFlush(unidadViaje);

        int databaseSizeBeforeDelete = unidadViajeRepository.findAll().size();

        // Delete the unidadViaje
        restUnidadViajeMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidadViaje.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadViaje> unidadViajeList = unidadViajeRepository.findAll();
        assertThat(unidadViajeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
