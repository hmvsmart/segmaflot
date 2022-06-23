package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Nivel;
import com.mycompany.myapp.repository.NivelRepository;
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
 * Integration tests for the {@link NivelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NivelResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/nivels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNivelMockMvc;

    private Nivel nivel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nivel createEntity(EntityManager em) {
        Nivel nivel = new Nivel()
            .nombre(DEFAULT_NOMBRE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return nivel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nivel createUpdatedEntity(EntityManager em) {
        Nivel nivel = new Nivel()
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return nivel;
    }

    @BeforeEach
    public void initTest() {
        nivel = createEntity(em);
    }

    @Test
    @Transactional
    void createNivel() throws Exception {
        int databaseSizeBeforeCreate = nivelRepository.findAll().size();
        // Create the Nivel
        restNivelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isCreated());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeCreate + 1);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testNivel.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testNivel.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNivel.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testNivel.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testNivel.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createNivelWithExistingId() throws Exception {
        // Create the Nivel with an existing ID
        nivel.setId(1L);

        int databaseSizeBeforeCreate = nivelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNivels() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get all the nivelList
        restNivelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get the nivel
        restNivelMockMvc
            .perform(get(ENTITY_API_URL_ID, nivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nivel.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingNivel() throws Exception {
        // Get the nivel
        restNivelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel
        Nivel updatedNivel = nivelRepository.findById(nivel.getId()).get();
        // Disconnect from session so that the updates on updatedNivel are not directly saved in db
        em.detach(updatedNivel);
        updatedNivel
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restNivelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNivel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNivel))
            )
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNivel.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testNivel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNivel.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testNivel.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testNivel.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nivel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNivelWithPatch() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel using partial update
        Nivel partialUpdatedNivel = new Nivel();
        partialUpdatedNivel.setId(nivel.getId());

        partialUpdatedNivel.nombre(UPDATED_NOMBRE).modifyByUser(UPDATED_MODIFY_BY_USER).modifiedOn(UPDATED_MODIFIED_ON);

        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNivel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNivel))
            )
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNivel.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testNivel.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNivel.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testNivel.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testNivel.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNivelWithPatch() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel using partial update
        Nivel partialUpdatedNivel = new Nivel();
        partialUpdatedNivel.setId(nivel.getId());

        partialUpdatedNivel
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNivel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNivel))
            )
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testNivel.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testNivel.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNivel.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testNivel.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testNivel.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nivel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nivel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nivel))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nivel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeDelete = nivelRepository.findAll().size();

        // Delete the nivel
        restNivelMockMvc
            .perform(delete(ENTITY_API_URL_ID, nivel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
