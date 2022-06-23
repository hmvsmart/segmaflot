package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Colonia;
import com.mycompany.myapp.repository.ColoniaRepository;
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
 * Integration tests for the {@link ColoniaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ColoniaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/colonias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ColoniaRepository coloniaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restColoniaMockMvc;

    private Colonia colonia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colonia createEntity(EntityManager em) {
        Colonia colonia = new Colonia().nombre(DEFAULT_NOMBRE);
        return colonia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colonia createUpdatedEntity(EntityManager em) {
        Colonia colonia = new Colonia().nombre(UPDATED_NOMBRE);
        return colonia;
    }

    @BeforeEach
    public void initTest() {
        colonia = createEntity(em);
    }

    @Test
    @Transactional
    void createColonia() throws Exception {
        int databaseSizeBeforeCreate = coloniaRepository.findAll().size();
        // Create the Colonia
        restColoniaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colonia)))
            .andExpect(status().isCreated());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeCreate + 1);
        Colonia testColonia = coloniaList.get(coloniaList.size() - 1);
        assertThat(testColonia.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createColoniaWithExistingId() throws Exception {
        // Create the Colonia with an existing ID
        colonia.setId(1L);

        int databaseSizeBeforeCreate = coloniaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restColoniaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colonia)))
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllColonias() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get all the coloniaList
        restColoniaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colonia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getColonia() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        // Get the colonia
        restColoniaMockMvc
            .perform(get(ENTITY_API_URL_ID, colonia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(colonia.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingColonia() throws Exception {
        // Get the colonia
        restColoniaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewColonia() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();

        // Update the colonia
        Colonia updatedColonia = coloniaRepository.findById(colonia.getId()).get();
        // Disconnect from session so that the updates on updatedColonia are not directly saved in db
        em.detach(updatedColonia);
        updatedColonia.nombre(UPDATED_NOMBRE);

        restColoniaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedColonia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedColonia))
            )
            .andExpect(status().isOk());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
        Colonia testColonia = coloniaList.get(coloniaList.size() - 1);
        assertThat(testColonia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();
        colonia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColoniaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, colonia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colonia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();
        colonia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColoniaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(colonia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();
        colonia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColoniaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(colonia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateColoniaWithPatch() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();

        // Update the colonia using partial update
        Colonia partialUpdatedColonia = new Colonia();
        partialUpdatedColonia.setId(colonia.getId());

        partialUpdatedColonia.nombre(UPDATED_NOMBRE);

        restColoniaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColonia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColonia))
            )
            .andExpect(status().isOk());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
        Colonia testColonia = coloniaList.get(coloniaList.size() - 1);
        assertThat(testColonia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateColoniaWithPatch() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();

        // Update the colonia using partial update
        Colonia partialUpdatedColonia = new Colonia();
        partialUpdatedColonia.setId(colonia.getId());

        partialUpdatedColonia.nombre(UPDATED_NOMBRE);

        restColoniaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedColonia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedColonia))
            )
            .andExpect(status().isOk());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
        Colonia testColonia = coloniaList.get(coloniaList.size() - 1);
        assertThat(testColonia.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();
        colonia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColoniaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, colonia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colonia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();
        colonia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColoniaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(colonia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamColonia() throws Exception {
        int databaseSizeBeforeUpdate = coloniaRepository.findAll().size();
        colonia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restColoniaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(colonia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Colonia in the database
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteColonia() throws Exception {
        // Initialize the database
        coloniaRepository.saveAndFlush(colonia);

        int databaseSizeBeforeDelete = coloniaRepository.findAll().size();

        // Delete the colonia
        restColoniaMockMvc
            .perform(delete(ENTITY_API_URL_ID, colonia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colonia> coloniaList = coloniaRepository.findAll();
        assertThat(coloniaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
