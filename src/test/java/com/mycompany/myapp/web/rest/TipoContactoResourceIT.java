package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TipoContacto;
import com.mycompany.myapp.repository.TipoContactoRepository;
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
 * Integration tests for the {@link TipoContactoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoContactoResourceIT {

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

    private static final String ENTITY_API_URL = "/api/tipo-contactos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoContactoRepository tipoContactoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoContactoMockMvc;

    private TipoContacto tipoContacto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoContacto createEntity(EntityManager em) {
        TipoContacto tipoContacto = new TipoContacto()
            .nombre(DEFAULT_NOMBRE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return tipoContacto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoContacto createUpdatedEntity(EntityManager em) {
        TipoContacto tipoContacto = new TipoContacto()
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return tipoContacto;
    }

    @BeforeEach
    public void initTest() {
        tipoContacto = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoContacto() throws Exception {
        int databaseSizeBeforeCreate = tipoContactoRepository.findAll().size();
        // Create the TipoContacto
        restTipoContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContacto)))
            .andExpect(status().isCreated());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoContacto testTipoContacto = tipoContactoList.get(tipoContactoList.size() - 1);
        assertThat(testTipoContacto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoContacto.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testTipoContacto.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testTipoContacto.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testTipoContacto.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testTipoContacto.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createTipoContactoWithExistingId() throws Exception {
        // Create the TipoContacto with an existing ID
        tipoContacto.setId(1L);

        int databaseSizeBeforeCreate = tipoContactoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContacto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoContactos() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        // Get all the tipoContactoList
        restTipoContactoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoContacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getTipoContacto() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        // Get the tipoContacto
        restTipoContactoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoContacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoContacto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingTipoContacto() throws Exception {
        // Get the tipoContacto
        restTipoContactoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoContacto() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();

        // Update the tipoContacto
        TipoContacto updatedTipoContacto = tipoContactoRepository.findById(tipoContacto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoContacto are not directly saved in db
        em.detach(updatedTipoContacto);
        updatedTipoContacto
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restTipoContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoContacto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoContacto))
            )
            .andExpect(status().isOk());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
        TipoContacto testTipoContacto = tipoContactoList.get(tipoContactoList.size() - 1);
        assertThat(testTipoContacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoContacto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testTipoContacto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTipoContacto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testTipoContacto.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testTipoContacto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();
        tipoContacto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoContacto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoContacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();
        tipoContacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoContacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();
        tipoContacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContactoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoContacto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoContactoWithPatch() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();

        // Update the tipoContacto using partial update
        TipoContacto partialUpdatedTipoContacto = new TipoContacto();
        partialUpdatedTipoContacto.setId(tipoContacto.getId());

        partialUpdatedTipoContacto
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restTipoContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoContacto))
            )
            .andExpect(status().isOk());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
        TipoContacto testTipoContacto = tipoContactoList.get(tipoContactoList.size() - 1);
        assertThat(testTipoContacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoContacto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testTipoContacto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTipoContacto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testTipoContacto.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testTipoContacto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTipoContactoWithPatch() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();

        // Update the tipoContacto using partial update
        TipoContacto partialUpdatedTipoContacto = new TipoContacto();
        partialUpdatedTipoContacto.setId(tipoContacto.getId());

        partialUpdatedTipoContacto
            .nombre(UPDATED_NOMBRE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restTipoContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoContacto))
            )
            .andExpect(status().isOk());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
        TipoContacto testTipoContacto = tipoContactoList.get(tipoContactoList.size() - 1);
        assertThat(testTipoContacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoContacto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testTipoContacto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testTipoContacto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testTipoContacto.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testTipoContacto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();
        tipoContacto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoContacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();
        tipoContacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoContacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoContacto() throws Exception {
        int databaseSizeBeforeUpdate = tipoContactoRepository.findAll().size();
        tipoContacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoContactoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoContacto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoContacto in the database
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoContacto() throws Exception {
        // Initialize the database
        tipoContactoRepository.saveAndFlush(tipoContacto);

        int databaseSizeBeforeDelete = tipoContactoRepository.findAll().size();

        // Delete the tipoContacto
        restTipoContactoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoContacto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoContacto> tipoContactoList = tipoContactoRepository.findAll();
        assertThat(tipoContactoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
