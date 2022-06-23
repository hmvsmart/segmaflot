package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CP;
import com.mycompany.myapp.repository.CPRepository;
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
 * Integration tests for the {@link CPResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CPResourceIT {

    private static final Integer DEFAULT_CP = 1;
    private static final Integer UPDATED_CP = 2;

    private static final String ENTITY_API_URL = "/api/cps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CPRepository cPRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPMockMvc;

    private CP cP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CP createEntity(EntityManager em) {
        CP cP = new CP().cp(DEFAULT_CP);
        return cP;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CP createUpdatedEntity(EntityManager em) {
        CP cP = new CP().cp(UPDATED_CP);
        return cP;
    }

    @BeforeEach
    public void initTest() {
        cP = createEntity(em);
    }

    @Test
    @Transactional
    void createCP() throws Exception {
        int databaseSizeBeforeCreate = cPRepository.findAll().size();
        // Create the CP
        restCPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cP)))
            .andExpect(status().isCreated());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeCreate + 1);
        CP testCP = cPList.get(cPList.size() - 1);
        assertThat(testCP.getCp()).isEqualTo(DEFAULT_CP);
    }

    @Test
    @Transactional
    void createCPWithExistingId() throws Exception {
        // Create the CP with an existing ID
        cP.setId(1L);

        int databaseSizeBeforeCreate = cPRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cP)))
            .andExpect(status().isBadRequest());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCPS() throws Exception {
        // Initialize the database
        cPRepository.saveAndFlush(cP);

        // Get all the cPList
        restCPMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cP.getId().intValue())))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)));
    }

    @Test
    @Transactional
    void getCP() throws Exception {
        // Initialize the database
        cPRepository.saveAndFlush(cP);

        // Get the cP
        restCPMockMvc
            .perform(get(ENTITY_API_URL_ID, cP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cP.getId().intValue()))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP));
    }

    @Test
    @Transactional
    void getNonExistingCP() throws Exception {
        // Get the cP
        restCPMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCP() throws Exception {
        // Initialize the database
        cPRepository.saveAndFlush(cP);

        int databaseSizeBeforeUpdate = cPRepository.findAll().size();

        // Update the cP
        CP updatedCP = cPRepository.findById(cP.getId()).get();
        // Disconnect from session so that the updates on updatedCP are not directly saved in db
        em.detach(updatedCP);
        updatedCP.cp(UPDATED_CP);

        restCPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCP.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCP))
            )
            .andExpect(status().isOk());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
        CP testCP = cPList.get(cPList.size() - 1);
        assertThat(testCP.getCp()).isEqualTo(UPDATED_CP);
    }

    @Test
    @Transactional
    void putNonExistingCP() throws Exception {
        int databaseSizeBeforeUpdate = cPRepository.findAll().size();
        cP.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cP.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCP() throws Exception {
        int databaseSizeBeforeUpdate = cPRepository.findAll().size();
        cP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCP() throws Exception {
        int databaseSizeBeforeUpdate = cPRepository.findAll().size();
        cP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCPMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cP)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCPWithPatch() throws Exception {
        // Initialize the database
        cPRepository.saveAndFlush(cP);

        int databaseSizeBeforeUpdate = cPRepository.findAll().size();

        // Update the cP using partial update
        CP partialUpdatedCP = new CP();
        partialUpdatedCP.setId(cP.getId());

        restCPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCP))
            )
            .andExpect(status().isOk());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
        CP testCP = cPList.get(cPList.size() - 1);
        assertThat(testCP.getCp()).isEqualTo(DEFAULT_CP);
    }

    @Test
    @Transactional
    void fullUpdateCPWithPatch() throws Exception {
        // Initialize the database
        cPRepository.saveAndFlush(cP);

        int databaseSizeBeforeUpdate = cPRepository.findAll().size();

        // Update the cP using partial update
        CP partialUpdatedCP = new CP();
        partialUpdatedCP.setId(cP.getId());

        partialUpdatedCP.cp(UPDATED_CP);

        restCPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCP))
            )
            .andExpect(status().isOk());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
        CP testCP = cPList.get(cPList.size() - 1);
        assertThat(testCP.getCp()).isEqualTo(UPDATED_CP);
    }

    @Test
    @Transactional
    void patchNonExistingCP() throws Exception {
        int databaseSizeBeforeUpdate = cPRepository.findAll().size();
        cP.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCP() throws Exception {
        int databaseSizeBeforeUpdate = cPRepository.findAll().size();
        cP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cP))
            )
            .andExpect(status().isBadRequest());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCP() throws Exception {
        int databaseSizeBeforeUpdate = cPRepository.findAll().size();
        cP.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCPMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cP)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CP in the database
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCP() throws Exception {
        // Initialize the database
        cPRepository.saveAndFlush(cP);

        int databaseSizeBeforeDelete = cPRepository.findAll().size();

        // Delete the cP
        restCPMockMvc.perform(delete(ENTITY_API_URL_ID, cP.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CP> cPList = cPRepository.findAll();
        assertThat(cPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
