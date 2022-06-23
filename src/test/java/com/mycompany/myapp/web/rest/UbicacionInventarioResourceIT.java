package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.UbicacionInventario;
import com.mycompany.myapp.repository.UbicacionInventarioRepository;
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
 * Integration tests for the {@link UbicacionInventarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UbicacionInventarioResourceIT {

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

    private static final String ENTITY_API_URL = "/api/ubicacion-inventarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UbicacionInventarioRepository ubicacionInventarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUbicacionInventarioMockMvc;

    private UbicacionInventario ubicacionInventario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UbicacionInventario createEntity(EntityManager em) {
        UbicacionInventario ubicacionInventario = new UbicacionInventario()
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return ubicacionInventario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UbicacionInventario createUpdatedEntity(EntityManager em) {
        UbicacionInventario ubicacionInventario = new UbicacionInventario()
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return ubicacionInventario;
    }

    @BeforeEach
    public void initTest() {
        ubicacionInventario = createEntity(em);
    }

    @Test
    @Transactional
    void createUbicacionInventario() throws Exception {
        int databaseSizeBeforeCreate = ubicacionInventarioRepository.findAll().size();
        // Create the UbicacionInventario
        restUbicacionInventarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isCreated());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeCreate + 1);
        UbicacionInventario testUbicacionInventario = ubicacionInventarioList.get(ubicacionInventarioList.size() - 1);
        assertThat(testUbicacionInventario.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUbicacionInventario.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testUbicacionInventario.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUbicacionInventario.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUbicacionInventario.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testUbicacionInventario.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createUbicacionInventarioWithExistingId() throws Exception {
        // Create the UbicacionInventario with an existing ID
        ubicacionInventario.setId(1L);

        int databaseSizeBeforeCreate = ubicacionInventarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUbicacionInventarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUbicacionInventarios() throws Exception {
        // Initialize the database
        ubicacionInventarioRepository.saveAndFlush(ubicacionInventario);

        // Get all the ubicacionInventarioList
        restUbicacionInventarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ubicacionInventario.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getUbicacionInventario() throws Exception {
        // Initialize the database
        ubicacionInventarioRepository.saveAndFlush(ubicacionInventario);

        // Get the ubicacionInventario
        restUbicacionInventarioMockMvc
            .perform(get(ENTITY_API_URL_ID, ubicacionInventario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ubicacionInventario.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingUbicacionInventario() throws Exception {
        // Get the ubicacionInventario
        restUbicacionInventarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUbicacionInventario() throws Exception {
        // Initialize the database
        ubicacionInventarioRepository.saveAndFlush(ubicacionInventario);

        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();

        // Update the ubicacionInventario
        UbicacionInventario updatedUbicacionInventario = ubicacionInventarioRepository.findById(ubicacionInventario.getId()).get();
        // Disconnect from session so that the updates on updatedUbicacionInventario are not directly saved in db
        em.detach(updatedUbicacionInventario);
        updatedUbicacionInventario
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUbicacionInventarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUbicacionInventario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUbicacionInventario))
            )
            .andExpect(status().isOk());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
        UbicacionInventario testUbicacionInventario = ubicacionInventarioList.get(ubicacionInventarioList.size() - 1);
        assertThat(testUbicacionInventario.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUbicacionInventario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUbicacionInventario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUbicacionInventario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUbicacionInventario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUbicacionInventario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingUbicacionInventario() throws Exception {
        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();
        ubicacionInventario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacionInventarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ubicacionInventario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUbicacionInventario() throws Exception {
        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();
        ubicacionInventario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionInventarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUbicacionInventario() throws Exception {
        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();
        ubicacionInventario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionInventarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUbicacionInventarioWithPatch() throws Exception {
        // Initialize the database
        ubicacionInventarioRepository.saveAndFlush(ubicacionInventario);

        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();

        // Update the ubicacionInventario using partial update
        UbicacionInventario partialUpdatedUbicacionInventario = new UbicacionInventario();
        partialUpdatedUbicacionInventario.setId(ubicacionInventario.getId());

        partialUpdatedUbicacionInventario.modifyByUser(UPDATED_MODIFY_BY_USER).auditStatus(UPDATED_AUDIT_STATUS);

        restUbicacionInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUbicacionInventario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUbicacionInventario))
            )
            .andExpect(status().isOk());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
        UbicacionInventario testUbicacionInventario = ubicacionInventarioList.get(ubicacionInventarioList.size() - 1);
        assertThat(testUbicacionInventario.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUbicacionInventario.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testUbicacionInventario.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUbicacionInventario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUbicacionInventario.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testUbicacionInventario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateUbicacionInventarioWithPatch() throws Exception {
        // Initialize the database
        ubicacionInventarioRepository.saveAndFlush(ubicacionInventario);

        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();

        // Update the ubicacionInventario using partial update
        UbicacionInventario partialUpdatedUbicacionInventario = new UbicacionInventario();
        partialUpdatedUbicacionInventario.setId(ubicacionInventario.getId());

        partialUpdatedUbicacionInventario
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUbicacionInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUbicacionInventario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUbicacionInventario))
            )
            .andExpect(status().isOk());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
        UbicacionInventario testUbicacionInventario = ubicacionInventarioList.get(ubicacionInventarioList.size() - 1);
        assertThat(testUbicacionInventario.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUbicacionInventario.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUbicacionInventario.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUbicacionInventario.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUbicacionInventario.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUbicacionInventario.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingUbicacionInventario() throws Exception {
        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();
        ubicacionInventario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUbicacionInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ubicacionInventario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUbicacionInventario() throws Exception {
        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();
        ubicacionInventario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUbicacionInventario() throws Exception {
        int databaseSizeBeforeUpdate = ubicacionInventarioRepository.findAll().size();
        ubicacionInventario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUbicacionInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ubicacionInventario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UbicacionInventario in the database
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUbicacionInventario() throws Exception {
        // Initialize the database
        ubicacionInventarioRepository.saveAndFlush(ubicacionInventario);

        int databaseSizeBeforeDelete = ubicacionInventarioRepository.findAll().size();

        // Delete the ubicacionInventario
        restUbicacionInventarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, ubicacionInventario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UbicacionInventario> ubicacionInventarioList = ubicacionInventarioRepository.findAll();
        assertThat(ubicacionInventarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
