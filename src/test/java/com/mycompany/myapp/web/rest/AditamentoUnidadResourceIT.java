package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AditamentoUnidad;
import com.mycompany.myapp.repository.AditamentoUnidadRepository;
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
 * Integration tests for the {@link AditamentoUnidadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AditamentoUnidadResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/aditamento-unidads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AditamentoUnidadRepository aditamentoUnidadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAditamentoUnidadMockMvc;

    private AditamentoUnidad aditamentoUnidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AditamentoUnidad createEntity(EntityManager em) {
        AditamentoUnidad aditamentoUnidad = new AditamentoUnidad()
            .fecha(DEFAULT_FECHA)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return aditamentoUnidad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AditamentoUnidad createUpdatedEntity(EntityManager em) {
        AditamentoUnidad aditamentoUnidad = new AditamentoUnidad()
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return aditamentoUnidad;
    }

    @BeforeEach
    public void initTest() {
        aditamentoUnidad = createEntity(em);
    }

    @Test
    @Transactional
    void createAditamentoUnidad() throws Exception {
        int databaseSizeBeforeCreate = aditamentoUnidadRepository.findAll().size();
        // Create the AditamentoUnidad
        restAditamentoUnidadMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isCreated());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeCreate + 1);
        AditamentoUnidad testAditamentoUnidad = aditamentoUnidadList.get(aditamentoUnidadList.size() - 1);
        assertThat(testAditamentoUnidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testAditamentoUnidad.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testAditamentoUnidad.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testAditamentoUnidad.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAditamentoUnidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testAditamentoUnidad.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testAditamentoUnidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createAditamentoUnidadWithExistingId() throws Exception {
        // Create the AditamentoUnidad with an existing ID
        aditamentoUnidad.setId(1L);

        int databaseSizeBeforeCreate = aditamentoUnidadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAditamentoUnidadMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAditamentoUnidads() throws Exception {
        // Initialize the database
        aditamentoUnidadRepository.saveAndFlush(aditamentoUnidad);

        // Get all the aditamentoUnidadList
        restAditamentoUnidadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aditamentoUnidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getAditamentoUnidad() throws Exception {
        // Initialize the database
        aditamentoUnidadRepository.saveAndFlush(aditamentoUnidad);

        // Get the aditamentoUnidad
        restAditamentoUnidadMockMvc
            .perform(get(ENTITY_API_URL_ID, aditamentoUnidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aditamentoUnidad.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingAditamentoUnidad() throws Exception {
        // Get the aditamentoUnidad
        restAditamentoUnidadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAditamentoUnidad() throws Exception {
        // Initialize the database
        aditamentoUnidadRepository.saveAndFlush(aditamentoUnidad);

        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();

        // Update the aditamentoUnidad
        AditamentoUnidad updatedAditamentoUnidad = aditamentoUnidadRepository.findById(aditamentoUnidad.getId()).get();
        // Disconnect from session so that the updates on updatedAditamentoUnidad are not directly saved in db
        em.detach(updatedAditamentoUnidad);
        updatedAditamentoUnidad
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restAditamentoUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAditamentoUnidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAditamentoUnidad))
            )
            .andExpect(status().isOk());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
        AditamentoUnidad testAditamentoUnidad = aditamentoUnidadList.get(aditamentoUnidadList.size() - 1);
        assertThat(testAditamentoUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testAditamentoUnidad.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testAditamentoUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testAditamentoUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAditamentoUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testAditamentoUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testAditamentoUnidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAditamentoUnidad() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();
        aditamentoUnidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAditamentoUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aditamentoUnidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAditamentoUnidad() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();
        aditamentoUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAditamentoUnidad() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();
        aditamentoUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoUnidadMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAditamentoUnidadWithPatch() throws Exception {
        // Initialize the database
        aditamentoUnidadRepository.saveAndFlush(aditamentoUnidad);

        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();

        // Update the aditamentoUnidad using partial update
        AditamentoUnidad partialUpdatedAditamentoUnidad = new AditamentoUnidad();
        partialUpdatedAditamentoUnidad.setId(aditamentoUnidad.getId());

        partialUpdatedAditamentoUnidad
            .fecha(UPDATED_FECHA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER);

        restAditamentoUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAditamentoUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAditamentoUnidad))
            )
            .andExpect(status().isOk());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
        AditamentoUnidad testAditamentoUnidad = aditamentoUnidadList.get(aditamentoUnidadList.size() - 1);
        assertThat(testAditamentoUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testAditamentoUnidad.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testAditamentoUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testAditamentoUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAditamentoUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testAditamentoUnidad.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testAditamentoUnidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAditamentoUnidadWithPatch() throws Exception {
        // Initialize the database
        aditamentoUnidadRepository.saveAndFlush(aditamentoUnidad);

        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();

        // Update the aditamentoUnidad using partial update
        AditamentoUnidad partialUpdatedAditamentoUnidad = new AditamentoUnidad();
        partialUpdatedAditamentoUnidad.setId(aditamentoUnidad.getId());

        partialUpdatedAditamentoUnidad
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restAditamentoUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAditamentoUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAditamentoUnidad))
            )
            .andExpect(status().isOk());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
        AditamentoUnidad testAditamentoUnidad = aditamentoUnidadList.get(aditamentoUnidadList.size() - 1);
        assertThat(testAditamentoUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testAditamentoUnidad.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testAditamentoUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testAditamentoUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAditamentoUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testAditamentoUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testAditamentoUnidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAditamentoUnidad() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();
        aditamentoUnidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAditamentoUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aditamentoUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAditamentoUnidad() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();
        aditamentoUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAditamentoUnidad() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoUnidadRepository.findAll().size();
        aditamentoUnidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aditamentoUnidad))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AditamentoUnidad in the database
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAditamentoUnidad() throws Exception {
        // Initialize the database
        aditamentoUnidadRepository.saveAndFlush(aditamentoUnidad);

        int databaseSizeBeforeDelete = aditamentoUnidadRepository.findAll().size();

        // Delete the aditamentoUnidad
        restAditamentoUnidadMockMvc
            .perform(delete(ENTITY_API_URL_ID, aditamentoUnidad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AditamentoUnidad> aditamentoUnidadList = aditamentoUnidadRepository.findAll();
        assertThat(aditamentoUnidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
