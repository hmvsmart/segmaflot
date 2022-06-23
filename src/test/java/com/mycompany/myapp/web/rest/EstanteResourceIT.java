package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Estante;
import com.mycompany.myapp.repository.EstanteRepository;
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
 * Integration tests for the {@link EstanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstanteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/estantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstanteRepository estanteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstanteMockMvc;

    private Estante estante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estante createEntity(EntityManager em) {
        Estante estante = new Estante()
            .nombre(DEFAULT_NOMBRE)
            .material(DEFAULT_MATERIAL)
            .color(DEFAULT_COLOR)
            .descripcion(DEFAULT_DESCRIPCION)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return estante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estante createUpdatedEntity(EntityManager em) {
        Estante estante = new Estante()
            .nombre(UPDATED_NOMBRE)
            .material(UPDATED_MATERIAL)
            .color(UPDATED_COLOR)
            .descripcion(UPDATED_DESCRIPCION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return estante;
    }

    @BeforeEach
    public void initTest() {
        estante = createEntity(em);
    }

    @Test
    @Transactional
    void createEstante() throws Exception {
        int databaseSizeBeforeCreate = estanteRepository.findAll().size();
        // Create the Estante
        restEstanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estante)))
            .andExpect(status().isCreated());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeCreate + 1);
        Estante testEstante = estanteList.get(estanteList.size() - 1);
        assertThat(testEstante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstante.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testEstante.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testEstante.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEstante.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testEstante.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEstante.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testEstante.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testEstante.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createEstanteWithExistingId() throws Exception {
        // Create the Estante with an existing ID
        estante.setId(1L);

        int databaseSizeBeforeCreate = estanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstanteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estante)))
            .andExpect(status().isBadRequest());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstantes() throws Exception {
        // Initialize the database
        estanteRepository.saveAndFlush(estante);

        // Get all the estanteList
        restEstanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getEstante() throws Exception {
        // Initialize the database
        estanteRepository.saveAndFlush(estante);

        // Get the estante
        restEstanteMockMvc
            .perform(get(ENTITY_API_URL_ID, estante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estante.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingEstante() throws Exception {
        // Get the estante
        restEstanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstante() throws Exception {
        // Initialize the database
        estanteRepository.saveAndFlush(estante);

        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();

        // Update the estante
        Estante updatedEstante = estanteRepository.findById(estante.getId()).get();
        // Disconnect from session so that the updates on updatedEstante are not directly saved in db
        em.detach(updatedEstante);
        updatedEstante
            .nombre(UPDATED_NOMBRE)
            .material(UPDATED_MATERIAL)
            .color(UPDATED_COLOR)
            .descripcion(UPDATED_DESCRIPCION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEstanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstante))
            )
            .andExpect(status().isOk());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
        Estante testEstante = estanteList.get(estanteList.size() - 1);
        assertThat(testEstante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstante.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testEstante.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testEstante.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEstante.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEstante.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEstante.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEstante.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEstante.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingEstante() throws Exception {
        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();
        estante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstante() throws Exception {
        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();
        estante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstante() throws Exception {
        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();
        estante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstanteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstanteWithPatch() throws Exception {
        // Initialize the database
        estanteRepository.saveAndFlush(estante);

        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();

        // Update the estante using partial update
        Estante partialUpdatedEstante = new Estante();
        partialUpdatedEstante.setId(estante.getId());

        partialUpdatedEstante
            .nombre(UPDATED_NOMBRE)
            .color(UPDATED_COLOR)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restEstanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstante))
            )
            .andExpect(status().isOk());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
        Estante testEstante = estanteList.get(estanteList.size() - 1);
        assertThat(testEstante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstante.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testEstante.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testEstante.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEstante.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEstante.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEstante.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEstante.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEstante.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateEstanteWithPatch() throws Exception {
        // Initialize the database
        estanteRepository.saveAndFlush(estante);

        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();

        // Update the estante using partial update
        Estante partialUpdatedEstante = new Estante();
        partialUpdatedEstante.setId(estante.getId());

        partialUpdatedEstante
            .nombre(UPDATED_NOMBRE)
            .material(UPDATED_MATERIAL)
            .color(UPDATED_COLOR)
            .descripcion(UPDATED_DESCRIPCION)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEstanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstante))
            )
            .andExpect(status().isOk());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
        Estante testEstante = estanteList.get(estanteList.size() - 1);
        assertThat(testEstante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstante.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testEstante.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testEstante.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEstante.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEstante.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEstante.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEstante.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEstante.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingEstante() throws Exception {
        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();
        estante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstante() throws Exception {
        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();
        estante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstante() throws Exception {
        int databaseSizeBeforeUpdate = estanteRepository.findAll().size();
        estante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstanteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estante in the database
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstante() throws Exception {
        // Initialize the database
        estanteRepository.saveAndFlush(estante);

        int databaseSizeBeforeDelete = estanteRepository.findAll().size();

        // Delete the estante
        restEstanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, estante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estante> estanteList = estanteRepository.findAll();
        assertThat(estanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
