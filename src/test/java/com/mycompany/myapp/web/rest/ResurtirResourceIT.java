package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Resurtir;
import com.mycompany.myapp.repository.ResurtirRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ResurtirResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResurtirResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

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

    private static final String ENTITY_API_URL = "/api/resurtirs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResurtirRepository resurtirRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResurtirMockMvc;

    private Resurtir resurtir;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resurtir createEntity(EntityManager em) {
        Resurtir resurtir = new Resurtir()
            .fecha(DEFAULT_FECHA)
            .total(DEFAULT_TOTAL)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return resurtir;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resurtir createUpdatedEntity(EntityManager em) {
        Resurtir resurtir = new Resurtir()
            .fecha(UPDATED_FECHA)
            .total(UPDATED_TOTAL)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return resurtir;
    }

    @BeforeEach
    public void initTest() {
        resurtir = createEntity(em);
    }

    @Test
    @Transactional
    void createResurtir() throws Exception {
        int databaseSizeBeforeCreate = resurtirRepository.findAll().size();
        // Create the Resurtir
        restResurtirMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resurtir)))
            .andExpect(status().isCreated());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeCreate + 1);
        Resurtir testResurtir = resurtirList.get(resurtirList.size() - 1);
        assertThat(testResurtir.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testResurtir.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);
        assertThat(testResurtir.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testResurtir.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testResurtir.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testResurtir.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testResurtir.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createResurtirWithExistingId() throws Exception {
        // Create the Resurtir with an existing ID
        resurtir.setId(1L);

        int databaseSizeBeforeCreate = resurtirRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResurtirMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resurtir)))
            .andExpect(status().isBadRequest());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResurtirs() throws Exception {
        // Initialize the database
        resurtirRepository.saveAndFlush(resurtir);

        // Get all the resurtirList
        restResurtirMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resurtir.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getResurtir() throws Exception {
        // Initialize the database
        resurtirRepository.saveAndFlush(resurtir);

        // Get the resurtir
        restResurtirMockMvc
            .perform(get(ENTITY_API_URL_ID, resurtir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resurtir.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingResurtir() throws Exception {
        // Get the resurtir
        restResurtirMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResurtir() throws Exception {
        // Initialize the database
        resurtirRepository.saveAndFlush(resurtir);

        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();

        // Update the resurtir
        Resurtir updatedResurtir = resurtirRepository.findById(resurtir.getId()).get();
        // Disconnect from session so that the updates on updatedResurtir are not directly saved in db
        em.detach(updatedResurtir);
        updatedResurtir
            .fecha(UPDATED_FECHA)
            .total(UPDATED_TOTAL)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restResurtirMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResurtir.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResurtir))
            )
            .andExpect(status().isOk());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
        Resurtir testResurtir = resurtirList.get(resurtirList.size() - 1);
        assertThat(testResurtir.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testResurtir.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testResurtir.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testResurtir.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testResurtir.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testResurtir.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testResurtir.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingResurtir() throws Exception {
        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();
        resurtir.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResurtirMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resurtir.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResurtir() throws Exception {
        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();
        resurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResurtirMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResurtir() throws Exception {
        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();
        resurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResurtirMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resurtir)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResurtirWithPatch() throws Exception {
        // Initialize the database
        resurtirRepository.saveAndFlush(resurtir);

        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();

        // Update the resurtir using partial update
        Resurtir partialUpdatedResurtir = new Resurtir();
        partialUpdatedResurtir.setId(resurtir.getId());

        partialUpdatedResurtir.fecha(UPDATED_FECHA).total(UPDATED_TOTAL);

        restResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResurtir.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResurtir))
            )
            .andExpect(status().isOk());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
        Resurtir testResurtir = resurtirList.get(resurtirList.size() - 1);
        assertThat(testResurtir.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testResurtir.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testResurtir.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testResurtir.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testResurtir.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testResurtir.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testResurtir.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateResurtirWithPatch() throws Exception {
        // Initialize the database
        resurtirRepository.saveAndFlush(resurtir);

        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();

        // Update the resurtir using partial update
        Resurtir partialUpdatedResurtir = new Resurtir();
        partialUpdatedResurtir.setId(resurtir.getId());

        partialUpdatedResurtir
            .fecha(UPDATED_FECHA)
            .total(UPDATED_TOTAL)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResurtir.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResurtir))
            )
            .andExpect(status().isOk());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
        Resurtir testResurtir = resurtirList.get(resurtirList.size() - 1);
        assertThat(testResurtir.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testResurtir.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
        assertThat(testResurtir.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testResurtir.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testResurtir.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testResurtir.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testResurtir.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingResurtir() throws Exception {
        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();
        resurtir.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resurtir.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResurtir() throws Exception {
        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();
        resurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResurtir() throws Exception {
        int databaseSizeBeforeUpdate = resurtirRepository.findAll().size();
        resurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResurtirMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resurtir)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resurtir in the database
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResurtir() throws Exception {
        // Initialize the database
        resurtirRepository.saveAndFlush(resurtir);

        int databaseSizeBeforeDelete = resurtirRepository.findAll().size();

        // Delete the resurtir
        restResurtirMockMvc
            .perform(delete(ENTITY_API_URL_ID, resurtir.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resurtir> resurtirList = resurtirRepository.findAll();
        assertThat(resurtirList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
