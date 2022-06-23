package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Aditamento;
import com.mycompany.myapp.repository.AditamentoRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AditamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AditamentoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DESCRIPCION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPCION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DESCRIPCION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPCION_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/aditamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AditamentoRepository aditamentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAditamentoMockMvc;

    private Aditamento aditamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aditamento createEntity(EntityManager em) {
        Aditamento aditamento = new Aditamento()
            .nombre(DEFAULT_NOMBRE)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .descripcion(DEFAULT_DESCRIPCION)
            .descripcionContentType(DEFAULT_DESCRIPCION_CONTENT_TYPE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return aditamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aditamento createUpdatedEntity(EntityManager em) {
        Aditamento aditamento = new Aditamento()
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return aditamento;
    }

    @BeforeEach
    public void initTest() {
        aditamento = createEntity(em);
    }

    @Test
    @Transactional
    void createAditamento() throws Exception {
        int databaseSizeBeforeCreate = aditamentoRepository.findAll().size();
        // Create the Aditamento
        restAditamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aditamento)))
            .andExpect(status().isCreated());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Aditamento testAditamento = aditamentoList.get(aditamentoList.size() - 1);
        assertThat(testAditamento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAditamento.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testAditamento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAditamento.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testAditamento.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testAditamento.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAditamento.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testAditamento.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testAditamento.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createAditamentoWithExistingId() throws Exception {
        // Create the Aditamento with an existing ID
        aditamento.setId(1L);

        int databaseSizeBeforeCreate = aditamentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAditamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aditamento)))
            .andExpect(status().isBadRequest());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAditamentos() throws Exception {
        // Initialize the database
        aditamentoRepository.saveAndFlush(aditamento);

        // Get all the aditamentoList
        restAditamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aditamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].descripcionContentType").value(hasItem(DEFAULT_DESCRIPCION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPCION))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getAditamento() throws Exception {
        // Initialize the database
        aditamentoRepository.saveAndFlush(aditamento);

        // Get the aditamento
        restAditamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, aditamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aditamento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.descripcionContentType").value(DEFAULT_DESCRIPCION_CONTENT_TYPE))
            .andExpect(jsonPath("$.descripcion").value(Base64Utils.encodeToString(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingAditamento() throws Exception {
        // Get the aditamento
        restAditamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAditamento() throws Exception {
        // Initialize the database
        aditamentoRepository.saveAndFlush(aditamento);

        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();

        // Update the aditamento
        Aditamento updatedAditamento = aditamentoRepository.findById(aditamento.getId()).get();
        // Disconnect from session so that the updates on updatedAditamento are not directly saved in db
        em.detach(updatedAditamento);
        updatedAditamento
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restAditamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAditamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAditamento))
            )
            .andExpect(status().isOk());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
        Aditamento testAditamento = aditamentoList.get(aditamentoList.size() - 1);
        assertThat(testAditamento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAditamento.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testAditamento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAditamento.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testAditamento.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testAditamento.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAditamento.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testAditamento.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testAditamento.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingAditamento() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();
        aditamento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAditamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aditamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aditamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAditamento() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();
        aditamento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aditamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAditamento() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();
        aditamento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aditamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAditamentoWithPatch() throws Exception {
        // Initialize the database
        aditamentoRepository.saveAndFlush(aditamento);

        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();

        // Update the aditamento using partial update
        Aditamento partialUpdatedAditamento = new Aditamento();
        partialUpdatedAditamento.setId(aditamento.getId());

        partialUpdatedAditamento
            .createByUser(UPDATED_CREATE_BY_USER)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restAditamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAditamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAditamento))
            )
            .andExpect(status().isOk());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
        Aditamento testAditamento = aditamentoList.get(aditamentoList.size() - 1);
        assertThat(testAditamento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAditamento.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testAditamento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAditamento.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testAditamento.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testAditamento.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAditamento.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testAditamento.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testAditamento.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateAditamentoWithPatch() throws Exception {
        // Initialize the database
        aditamentoRepository.saveAndFlush(aditamento);

        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();

        // Update the aditamento using partial update
        Aditamento partialUpdatedAditamento = new Aditamento();
        partialUpdatedAditamento.setId(aditamento.getId());

        partialUpdatedAditamento
            .nombre(UPDATED_NOMBRE)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restAditamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAditamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAditamento))
            )
            .andExpect(status().isOk());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
        Aditamento testAditamento = aditamentoList.get(aditamentoList.size() - 1);
        assertThat(testAditamento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAditamento.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testAditamento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAditamento.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testAditamento.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testAditamento.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAditamento.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testAditamento.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testAditamento.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingAditamento() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();
        aditamento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAditamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aditamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aditamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAditamento() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();
        aditamento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aditamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAditamento() throws Exception {
        int databaseSizeBeforeUpdate = aditamentoRepository.findAll().size();
        aditamento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAditamentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aditamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aditamento in the database
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAditamento() throws Exception {
        // Initialize the database
        aditamentoRepository.saveAndFlush(aditamento);

        int databaseSizeBeforeDelete = aditamentoRepository.findAll().size();

        // Delete the aditamento
        restAditamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, aditamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aditamento> aditamentoList = aditamentoRepository.findAll();
        assertThat(aditamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
