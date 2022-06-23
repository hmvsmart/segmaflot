package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Seccion;
import com.mycompany.myapp.repository.SeccionRepository;
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
 * Integration tests for the {@link SeccionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeccionResourceIT {

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

    private static final String ENTITY_API_URL = "/api/seccions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeccionMockMvc;

    private Seccion seccion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seccion createEntity(EntityManager em) {
        Seccion seccion = new Seccion()
            .nombre(DEFAULT_NOMBRE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return seccion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seccion createUpdatedEntity(EntityManager em) {
        Seccion seccion = new Seccion()
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return seccion;
    }

    @BeforeEach
    public void initTest() {
        seccion = createEntity(em);
    }

    @Test
    @Transactional
    void createSeccion() throws Exception {
        int databaseSizeBeforeCreate = seccionRepository.findAll().size();
        // Create the Seccion
        restSeccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isCreated());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeCreate + 1);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSeccion.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testSeccion.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSeccion.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testSeccion.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testSeccion.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createSeccionWithExistingId() throws Exception {
        // Create the Seccion with an existing ID
        seccion.setId(1L);

        int databaseSizeBeforeCreate = seccionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeccionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeccions() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get all the seccionList
        restSeccionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        // Get the seccion
        restSeccionMockMvc
            .perform(get(ENTITY_API_URL_ID, seccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seccion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingSeccion() throws Exception {
        // Get the seccion
        restSeccionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Update the seccion
        Seccion updatedSeccion = seccionRepository.findById(seccion.getId()).get();
        // Disconnect from session so that the updates on updatedSeccion are not directly saved in db
        em.detach(updatedSeccion);
        updatedSeccion
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restSeccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSeccion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSeccion))
            )
            .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSeccion.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testSeccion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSeccion.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testSeccion.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testSeccion.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();
        seccion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seccion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();
        seccion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();
        seccion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeccionWithPatch() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Update the seccion using partial update
        Seccion partialUpdatedSeccion = new Seccion();
        partialUpdatedSeccion.setId(seccion.getId());

        partialUpdatedSeccion
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restSeccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccion))
            )
            .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSeccion.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testSeccion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSeccion.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testSeccion.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testSeccion.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSeccionWithPatch() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();

        // Update the seccion using partial update
        Seccion partialUpdatedSeccion = new Seccion();
        partialUpdatedSeccion.setId(seccion.getId());

        partialUpdatedSeccion
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restSeccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeccion))
            )
            .andExpect(status().isOk());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
        Seccion testSeccion = seccionList.get(seccionList.size() - 1);
        assertThat(testSeccion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSeccion.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testSeccion.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSeccion.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testSeccion.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testSeccion.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();
        seccion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seccion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();
        seccion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seccion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeccion() throws Exception {
        int databaseSizeBeforeUpdate = seccionRepository.findAll().size();
        seccion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeccionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seccion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seccion in the database
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeccion() throws Exception {
        // Initialize the database
        seccionRepository.saveAndFlush(seccion);

        int databaseSizeBeforeDelete = seccionRepository.findAll().size();

        // Delete the seccion
        restSeccionMockMvc
            .perform(delete(ENTITY_API_URL_ID, seccion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seccion> seccionList = seccionRepository.findAll();
        assertThat(seccionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
