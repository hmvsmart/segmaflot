package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ExperienciaHabilidad;
import com.mycompany.myapp.repository.ExperienciaHabilidadRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ExperienciaHabilidadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExperienciaHabilidadResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/experiencia-habilidads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExperienciaHabilidadRepository experienciaHabilidadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExperienciaHabilidadMockMvc;

    private ExperienciaHabilidad experienciaHabilidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExperienciaHabilidad createEntity(EntityManager em) {
        ExperienciaHabilidad experienciaHabilidad = new ExperienciaHabilidad()
            .fecha(DEFAULT_FECHA)
            .descripcion(DEFAULT_DESCRIPCION)
            .descripcionContentType(DEFAULT_DESCRIPCION_CONTENT_TYPE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return experienciaHabilidad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExperienciaHabilidad createUpdatedEntity(EntityManager em) {
        ExperienciaHabilidad experienciaHabilidad = new ExperienciaHabilidad()
            .fecha(UPDATED_FECHA)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return experienciaHabilidad;
    }

    @BeforeEach
    public void initTest() {
        experienciaHabilidad = createEntity(em);
    }

    @Test
    @Transactional
    void createExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeCreate = experienciaHabilidadRepository.findAll().size();
        // Create the ExperienciaHabilidad
        restExperienciaHabilidadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isCreated());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeCreate + 1);
        ExperienciaHabilidad testExperienciaHabilidad = experienciaHabilidadList.get(experienciaHabilidadList.size() - 1);
        assertThat(testExperienciaHabilidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testExperienciaHabilidad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testExperienciaHabilidad.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testExperienciaHabilidad.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testExperienciaHabilidad.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testExperienciaHabilidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testExperienciaHabilidad.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testExperienciaHabilidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createExperienciaHabilidadWithExistingId() throws Exception {
        // Create the ExperienciaHabilidad with an existing ID
        experienciaHabilidad.setId(1L);

        int databaseSizeBeforeCreate = experienciaHabilidadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienciaHabilidadMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExperienciaHabilidads() throws Exception {
        // Initialize the database
        experienciaHabilidadRepository.saveAndFlush(experienciaHabilidad);

        // Get all the experienciaHabilidadList
        restExperienciaHabilidadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienciaHabilidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
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
    void getExperienciaHabilidad() throws Exception {
        // Initialize the database
        experienciaHabilidadRepository.saveAndFlush(experienciaHabilidad);

        // Get the experienciaHabilidad
        restExperienciaHabilidadMockMvc
            .perform(get(ENTITY_API_URL_ID, experienciaHabilidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(experienciaHabilidad.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
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
    void getNonExistingExperienciaHabilidad() throws Exception {
        // Get the experienciaHabilidad
        restExperienciaHabilidadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExperienciaHabilidad() throws Exception {
        // Initialize the database
        experienciaHabilidadRepository.saveAndFlush(experienciaHabilidad);

        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();

        // Update the experienciaHabilidad
        ExperienciaHabilidad updatedExperienciaHabilidad = experienciaHabilidadRepository.findById(experienciaHabilidad.getId()).get();
        // Disconnect from session so that the updates on updatedExperienciaHabilidad are not directly saved in db
        em.detach(updatedExperienciaHabilidad);
        updatedExperienciaHabilidad
            .fecha(UPDATED_FECHA)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restExperienciaHabilidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExperienciaHabilidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedExperienciaHabilidad))
            )
            .andExpect(status().isOk());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
        ExperienciaHabilidad testExperienciaHabilidad = experienciaHabilidadList.get(experienciaHabilidadList.size() - 1);
        assertThat(testExperienciaHabilidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testExperienciaHabilidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testExperienciaHabilidad.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testExperienciaHabilidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testExperienciaHabilidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExperienciaHabilidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testExperienciaHabilidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testExperienciaHabilidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();
        experienciaHabilidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienciaHabilidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, experienciaHabilidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();
        experienciaHabilidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienciaHabilidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();
        experienciaHabilidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienciaHabilidadMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExperienciaHabilidadWithPatch() throws Exception {
        // Initialize the database
        experienciaHabilidadRepository.saveAndFlush(experienciaHabilidad);

        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();

        // Update the experienciaHabilidad using partial update
        ExperienciaHabilidad partialUpdatedExperienciaHabilidad = new ExperienciaHabilidad();
        partialUpdatedExperienciaHabilidad.setId(experienciaHabilidad.getId());

        partialUpdatedExperienciaHabilidad
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restExperienciaHabilidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperienciaHabilidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExperienciaHabilidad))
            )
            .andExpect(status().isOk());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
        ExperienciaHabilidad testExperienciaHabilidad = experienciaHabilidadList.get(experienciaHabilidadList.size() - 1);
        assertThat(testExperienciaHabilidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testExperienciaHabilidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testExperienciaHabilidad.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testExperienciaHabilidad.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testExperienciaHabilidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExperienciaHabilidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testExperienciaHabilidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testExperienciaHabilidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateExperienciaHabilidadWithPatch() throws Exception {
        // Initialize the database
        experienciaHabilidadRepository.saveAndFlush(experienciaHabilidad);

        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();

        // Update the experienciaHabilidad using partial update
        ExperienciaHabilidad partialUpdatedExperienciaHabilidad = new ExperienciaHabilidad();
        partialUpdatedExperienciaHabilidad.setId(experienciaHabilidad.getId());

        partialUpdatedExperienciaHabilidad
            .fecha(UPDATED_FECHA)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restExperienciaHabilidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExperienciaHabilidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExperienciaHabilidad))
            )
            .andExpect(status().isOk());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
        ExperienciaHabilidad testExperienciaHabilidad = experienciaHabilidadList.get(experienciaHabilidadList.size() - 1);
        assertThat(testExperienciaHabilidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testExperienciaHabilidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testExperienciaHabilidad.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testExperienciaHabilidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testExperienciaHabilidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExperienciaHabilidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testExperienciaHabilidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testExperienciaHabilidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();
        experienciaHabilidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienciaHabilidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, experienciaHabilidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();
        experienciaHabilidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienciaHabilidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExperienciaHabilidad() throws Exception {
        int databaseSizeBeforeUpdate = experienciaHabilidadRepository.findAll().size();
        experienciaHabilidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExperienciaHabilidadMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(experienciaHabilidad))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExperienciaHabilidad in the database
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExperienciaHabilidad() throws Exception {
        // Initialize the database
        experienciaHabilidadRepository.saveAndFlush(experienciaHabilidad);

        int databaseSizeBeforeDelete = experienciaHabilidadRepository.findAll().size();

        // Delete the experienciaHabilidad
        restExperienciaHabilidadMockMvc
            .perform(delete(ENTITY_API_URL_ID, experienciaHabilidad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExperienciaHabilidad> experienciaHabilidadList = experienciaHabilidadRepository.findAll();
        assertThat(experienciaHabilidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
