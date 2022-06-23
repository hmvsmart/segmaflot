package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MaterialServicio;
import com.mycompany.myapp.repository.MaterialServicioRepository;
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
 * Integration tests for the {@link MaterialServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialServicioResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

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

    private static final String ENTITY_API_URL = "/api/material-servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialServicioRepository materialServicioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialServicioMockMvc;

    private MaterialServicio materialServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialServicio createEntity(EntityManager em) {
        MaterialServicio materialServicio = new MaterialServicio()
            .fecha(DEFAULT_FECHA)
            .cantidad(DEFAULT_CANTIDAD)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return materialServicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MaterialServicio createUpdatedEntity(EntityManager em) {
        MaterialServicio materialServicio = new MaterialServicio()
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return materialServicio;
    }

    @BeforeEach
    public void initTest() {
        materialServicio = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterialServicio() throws Exception {
        int databaseSizeBeforeCreate = materialServicioRepository.findAll().size();
        // Create the MaterialServicio
        restMaterialServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isCreated());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeCreate + 1);
        MaterialServicio testMaterialServicio = materialServicioList.get(materialServicioList.size() - 1);
        assertThat(testMaterialServicio.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testMaterialServicio.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testMaterialServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testMaterialServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMaterialServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testMaterialServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testMaterialServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createMaterialServicioWithExistingId() throws Exception {
        // Create the MaterialServicio with an existing ID
        materialServicio.setId(1L);

        int databaseSizeBeforeCreate = materialServicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialServicioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterialServicios() throws Exception {
        // Initialize the database
        materialServicioRepository.saveAndFlush(materialServicio);

        // Get all the materialServicioList
        restMaterialServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materialServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getMaterialServicio() throws Exception {
        // Initialize the database
        materialServicioRepository.saveAndFlush(materialServicio);

        // Get the materialServicio
        restMaterialServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, materialServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materialServicio.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingMaterialServicio() throws Exception {
        // Get the materialServicio
        restMaterialServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMaterialServicio() throws Exception {
        // Initialize the database
        materialServicioRepository.saveAndFlush(materialServicio);

        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();

        // Update the materialServicio
        MaterialServicio updatedMaterialServicio = materialServicioRepository.findById(materialServicio.getId()).get();
        // Disconnect from session so that the updates on updatedMaterialServicio are not directly saved in db
        em.detach(updatedMaterialServicio);
        updatedMaterialServicio
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restMaterialServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterialServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterialServicio))
            )
            .andExpect(status().isOk());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
        MaterialServicio testMaterialServicio = materialServicioList.get(materialServicioList.size() - 1);
        assertThat(testMaterialServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMaterialServicio.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testMaterialServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testMaterialServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMaterialServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testMaterialServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testMaterialServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingMaterialServicio() throws Exception {
        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();
        materialServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materialServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterialServicio() throws Exception {
        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();
        materialServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterialServicio() throws Exception {
        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();
        materialServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialServicioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialServicioWithPatch() throws Exception {
        // Initialize the database
        materialServicioRepository.saveAndFlush(materialServicio);

        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();

        // Update the materialServicio using partial update
        MaterialServicio partialUpdatedMaterialServicio = new MaterialServicio();
        partialUpdatedMaterialServicio.setId(materialServicio.getId());

        partialUpdatedMaterialServicio.fecha(UPDATED_FECHA);

        restMaterialServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialServicio))
            )
            .andExpect(status().isOk());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
        MaterialServicio testMaterialServicio = materialServicioList.get(materialServicioList.size() - 1);
        assertThat(testMaterialServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMaterialServicio.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testMaterialServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testMaterialServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMaterialServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testMaterialServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testMaterialServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateMaterialServicioWithPatch() throws Exception {
        // Initialize the database
        materialServicioRepository.saveAndFlush(materialServicio);

        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();

        // Update the materialServicio using partial update
        MaterialServicio partialUpdatedMaterialServicio = new MaterialServicio();
        partialUpdatedMaterialServicio.setId(materialServicio.getId());

        partialUpdatedMaterialServicio
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restMaterialServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterialServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterialServicio))
            )
            .andExpect(status().isOk());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
        MaterialServicio testMaterialServicio = materialServicioList.get(materialServicioList.size() - 1);
        assertThat(testMaterialServicio.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMaterialServicio.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testMaterialServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testMaterialServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMaterialServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testMaterialServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testMaterialServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingMaterialServicio() throws Exception {
        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();
        materialServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materialServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterialServicio() throws Exception {
        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();
        materialServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterialServicio() throws Exception {
        int databaseSizeBeforeUpdate = materialServicioRepository.findAll().size();
        materialServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialServicioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materialServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MaterialServicio in the database
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterialServicio() throws Exception {
        // Initialize the database
        materialServicioRepository.saveAndFlush(materialServicio);

        int databaseSizeBeforeDelete = materialServicioRepository.findAll().size();

        // Delete the materialServicio
        restMaterialServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, materialServicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MaterialServicio> materialServicioList = materialServicioRepository.findAll();
        assertThat(materialServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
