package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Servicio;
import com.mycompany.myapp.repository.ServicioRepository;
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
 * Integration tests for the {@link ServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServicioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DESCRIPCION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPCION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DESCRIPCION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPCION_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DURACION_ESTIMADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DURACION_ESTIMADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicioMockMvc;

    private Servicio servicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicio createEntity(EntityManager em) {
        Servicio servicio = new Servicio()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .descripcionContentType(DEFAULT_DESCRIPCION_CONTENT_TYPE)
            .duracionEstimada(DEFAULT_DURACION_ESTIMADA)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return servicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicio createUpdatedEntity(EntityManager em) {
        Servicio servicio = new Servicio()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .duracionEstimada(UPDATED_DURACION_ESTIMADA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return servicio;
    }

    @BeforeEach
    public void initTest() {
        servicio = createEntity(em);
    }

    @Test
    @Transactional
    void createServicio() throws Exception {
        int databaseSizeBeforeCreate = servicioRepository.findAll().size();
        // Create the Servicio
        restServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicio)))
            .andExpect(status().isCreated());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeCreate + 1);
        Servicio testServicio = servicioList.get(servicioList.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testServicio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testServicio.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testServicio.getDuracionEstimada()).isEqualTo(DEFAULT_DURACION_ESTIMADA);
        assertThat(testServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createServicioWithExistingId() throws Exception {
        // Create the Servicio with an existing ID
        servicio.setId(1L);

        int databaseSizeBeforeCreate = servicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicio)))
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicios() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        // Get all the servicioList
        restServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcionContentType").value(hasItem(DEFAULT_DESCRIPCION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPCION))))
            .andExpect(jsonPath("$.[*].duracionEstimada").value(hasItem(DEFAULT_DURACION_ESTIMADA.toString())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        // Get the servicio
        restServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, servicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcionContentType").value(DEFAULT_DESCRIPCION_CONTENT_TYPE))
            .andExpect(jsonPath("$.descripcion").value(Base64Utils.encodeToString(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.duracionEstimada").value(DEFAULT_DURACION_ESTIMADA.toString()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingServicio() throws Exception {
        // Get the servicio
        restServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();

        // Update the servicio
        Servicio updatedServicio = servicioRepository.findById(servicio.getId()).get();
        // Disconnect from session so that the updates on updatedServicio are not directly saved in db
        em.detach(updatedServicio);
        updatedServicio
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .duracionEstimada(UPDATED_DURACION_ESTIMADA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedServicio))
            )
            .andExpect(status().isOk());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
        Servicio testServicio = servicioList.get(servicioList.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServicio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicio.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testServicio.getDuracionEstimada()).isEqualTo(UPDATED_DURACION_ESTIMADA);
        assertThat(testServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();
        servicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();
        servicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();
        servicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicioWithPatch() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();

        // Update the servicio using partial update
        Servicio partialUpdatedServicio = new Servicio();
        partialUpdatedServicio.setId(servicio.getId());

        partialUpdatedServicio.nombre(UPDATED_NOMBRE).createdOn(UPDATED_CREATED_ON);

        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicio))
            )
            .andExpect(status().isOk());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
        Servicio testServicio = servicioList.get(servicioList.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServicio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testServicio.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testServicio.getDuracionEstimada()).isEqualTo(DEFAULT_DURACION_ESTIMADA);
        assertThat(testServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateServicioWithPatch() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();

        // Update the servicio using partial update
        Servicio partialUpdatedServicio = new Servicio();
        partialUpdatedServicio.setId(servicio.getId());

        partialUpdatedServicio
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .duracionEstimada(UPDATED_DURACION_ESTIMADA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicio))
            )
            .andExpect(status().isOk());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
        Servicio testServicio = servicioList.get(servicioList.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServicio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicio.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testServicio.getDuracionEstimada()).isEqualTo(UPDATED_DURACION_ESTIMADA);
        assertThat(testServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();
        servicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();
        servicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();
        servicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(servicio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        int databaseSizeBeforeDelete = servicioRepository.findAll().size();

        // Delete the servicio
        restServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
