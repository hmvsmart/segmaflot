package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Unidad;
import com.mycompany.myapp.repository.UnidadRepository;
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
 * Integration tests for the {@link UnidadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnidadResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final Double DEFAULT_KM_ACTUAL = 1D;
    private static final Double UPDATED_KM_ACTUAL = 2D;

    private static final String DEFAULT_TIPO_ADQUISICION = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ADQUISICION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DESCRIPCION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPCION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DESCRIPCION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPCION_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

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

    private static final String ENTITY_API_URL = "/api/unidads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnidadRepository unidadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnidadMockMvc;

    private Unidad unidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unidad createEntity(EntityManager em) {
        Unidad unidad = new Unidad()
            .fecha(DEFAULT_FECHA)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .kmActual(DEFAULT_KM_ACTUAL)
            .tipoAdquisicion(DEFAULT_TIPO_ADQUISICION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .color(DEFAULT_COLOR)
            .descripcion(DEFAULT_DESCRIPCION)
            .descripcionContentType(DEFAULT_DESCRIPCION_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return unidad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unidad createUpdatedEntity(EntityManager em) {
        Unidad unidad = new Unidad()
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .kmActual(UPDATED_KM_ACTUAL)
            .tipoAdquisicion(UPDATED_TIPO_ADQUISICION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .color(UPDATED_COLOR)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return unidad;
    }

    @BeforeEach
    public void initTest() {
        unidad = createEntity(em);
    }

    @Test
    @Transactional
    void createUnidad() throws Exception {
        int databaseSizeBeforeCreate = unidadRepository.findAll().size();
        // Create the Unidad
        restUnidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isCreated());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeCreate + 1);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUnidad.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testUnidad.getKmActual()).isEqualTo(DEFAULT_KM_ACTUAL);
        assertThat(testUnidad.getTipoAdquisicion()).isEqualTo(DEFAULT_TIPO_ADQUISICION);
        assertThat(testUnidad.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testUnidad.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testUnidad.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testUnidad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testUnidad.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testUnidad.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUnidad.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testUnidad.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUnidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUnidad.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testUnidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createUnidadWithExistingId() throws Exception {
        // Create the Unidad with an existing ID
        unidad.setId(1L);

        int databaseSizeBeforeCreate = unidadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnidads() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        // Get all the unidadList
        restUnidadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].kmActual").value(hasItem(DEFAULT_KM_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].tipoAdquisicion").value(hasItem(DEFAULT_TIPO_ADQUISICION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].descripcionContentType").value(hasItem(DEFAULT_DESCRIPCION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPCION))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        // Get the unidad
        restUnidadMockMvc
            .perform(get(ENTITY_API_URL_ID, unidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unidad.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.kmActual").value(DEFAULT_KM_ACTUAL.doubleValue()))
            .andExpect(jsonPath("$.tipoAdquisicion").value(DEFAULT_TIPO_ADQUISICION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.descripcionContentType").value(DEFAULT_DESCRIPCION_CONTENT_TYPE))
            .andExpect(jsonPath("$.descripcion").value(Base64Utils.encodeToString(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingUnidad() throws Exception {
        // Get the unidad
        restUnidadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad
        Unidad updatedUnidad = unidadRepository.findById(unidad.getId()).get();
        // Disconnect from session so that the updates on updatedUnidad are not directly saved in db
        em.detach(updatedUnidad);
        updatedUnidad
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .kmActual(UPDATED_KM_ACTUAL)
            .tipoAdquisicion(UPDATED_TIPO_ADQUISICION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .color(UPDATED_COLOR)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUnidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUnidad))
            )
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidad.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testUnidad.getKmActual()).isEqualTo(UPDATED_KM_ACTUAL);
        assertThat(testUnidad.getTipoAdquisicion()).isEqualTo(UPDATED_TIPO_ADQUISICION);
        assertThat(testUnidad.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testUnidad.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testUnidad.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testUnidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testUnidad.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testUnidad.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unidad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnidadWithPatch() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad using partial update
        Unidad partialUpdatedUnidad = new Unidad();
        partialUpdatedUnidad.setId(unidad.getId());

        partialUpdatedUnidad
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .kmActual(UPDATED_KM_ACTUAL)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidad))
            )
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testUnidad.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testUnidad.getKmActual()).isEqualTo(UPDATED_KM_ACTUAL);
        assertThat(testUnidad.getTipoAdquisicion()).isEqualTo(DEFAULT_TIPO_ADQUISICION);
        assertThat(testUnidad.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testUnidad.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testUnidad.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testUnidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testUnidad.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testUnidad.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidad.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testUnidad.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidad.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateUnidadWithPatch() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad using partial update
        Unidad partialUpdatedUnidad = new Unidad();
        partialUpdatedUnidad.setId(unidad.getId());

        partialUpdatedUnidad
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .kmActual(UPDATED_KM_ACTUAL)
            .tipoAdquisicion(UPDATED_TIPO_ADQUISICION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .color(UPDATED_COLOR)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnidad))
            )
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testUnidad.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testUnidad.getKmActual()).isEqualTo(UPDATED_KM_ACTUAL);
        assertThat(testUnidad.getTipoAdquisicion()).isEqualTo(UPDATED_TIPO_ADQUISICION);
        assertThat(testUnidad.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testUnidad.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testUnidad.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testUnidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testUnidad.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testUnidad.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUnidad.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testUnidad.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testUnidad.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testUnidad.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testUnidad.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unidad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unidad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();
        unidad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnidadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        int databaseSizeBeforeDelete = unidadRepository.findAll().size();

        // Delete the unidad
        restUnidadMockMvc
            .perform(delete(ENTITY_API_URL_ID, unidad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
