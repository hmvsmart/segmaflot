package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Viaje;
import com.mycompany.myapp.repository.ViajeRepository;
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
 * Integration tests for the {@link ViajeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViajeResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_EMBARQUE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_EMBARQUE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TIPO_CARGA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CARGA = "BBBBBBBBBB";

    private static final Double DEFAULT_PESO_NETO = 1D;
    private static final Double UPDATED_PESO_NETO = 2D;

    private static final byte[] DEFAULT_COMENTARIOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMENTARIOS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMENTARIOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMENTARIOS_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/viajes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViajeMockMvc;

    private Viaje viaje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Viaje createEntity(EntityManager em) {
        Viaje viaje = new Viaje()
            .fecha(DEFAULT_FECHA)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .horaEmbarque(DEFAULT_HORA_EMBARQUE)
            .tipoCarga(DEFAULT_TIPO_CARGA)
            .pesoNeto(DEFAULT_PESO_NETO)
            .comentarios(DEFAULT_COMENTARIOS)
            .comentariosContentType(DEFAULT_COMENTARIOS_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return viaje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Viaje createUpdatedEntity(EntityManager em) {
        Viaje viaje = new Viaje()
            .fecha(UPDATED_FECHA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaEmbarque(UPDATED_HORA_EMBARQUE)
            .tipoCarga(UPDATED_TIPO_CARGA)
            .pesoNeto(UPDATED_PESO_NETO)
            .comentarios(UPDATED_COMENTARIOS)
            .comentariosContentType(UPDATED_COMENTARIOS_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return viaje;
    }

    @BeforeEach
    public void initTest() {
        viaje = createEntity(em);
    }

    @Test
    @Transactional
    void createViaje() throws Exception {
        int databaseSizeBeforeCreate = viajeRepository.findAll().size();
        // Create the Viaje
        restViajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viaje)))
            .andExpect(status().isCreated());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeCreate + 1);
        Viaje testViaje = viajeList.get(viajeList.size() - 1);
        assertThat(testViaje.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testViaje.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testViaje.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testViaje.getHoraEmbarque()).isEqualTo(DEFAULT_HORA_EMBARQUE);
        assertThat(testViaje.getTipoCarga()).isEqualTo(DEFAULT_TIPO_CARGA);
        assertThat(testViaje.getPesoNeto()).isEqualTo(DEFAULT_PESO_NETO);
        assertThat(testViaje.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
        assertThat(testViaje.getComentariosContentType()).isEqualTo(DEFAULT_COMENTARIOS_CONTENT_TYPE);
        assertThat(testViaje.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testViaje.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testViaje.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testViaje.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testViaje.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testViaje.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createViajeWithExistingId() throws Exception {
        // Create the Viaje with an existing ID
        viaje.setId(1L);

        int databaseSizeBeforeCreate = viajeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restViajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viaje)))
            .andExpect(status().isBadRequest());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllViajes() throws Exception {
        // Initialize the database
        viajeRepository.saveAndFlush(viaje);

        // Get all the viajeList
        restViajeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].horaEmbarque").value(hasItem(DEFAULT_HORA_EMBARQUE.toString())))
            .andExpect(jsonPath("$.[*].tipoCarga").value(hasItem(DEFAULT_TIPO_CARGA)))
            .andExpect(jsonPath("$.[*].pesoNeto").value(hasItem(DEFAULT_PESO_NETO.doubleValue())))
            .andExpect(jsonPath("$.[*].comentariosContentType").value(hasItem(DEFAULT_COMENTARIOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].comentarios").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMENTARIOS))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getViaje() throws Exception {
        // Initialize the database
        viajeRepository.saveAndFlush(viaje);

        // Get the viaje
        restViajeMockMvc
            .perform(get(ENTITY_API_URL_ID, viaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viaje.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.horaEmbarque").value(DEFAULT_HORA_EMBARQUE.toString()))
            .andExpect(jsonPath("$.tipoCarga").value(DEFAULT_TIPO_CARGA))
            .andExpect(jsonPath("$.pesoNeto").value(DEFAULT_PESO_NETO.doubleValue()))
            .andExpect(jsonPath("$.comentariosContentType").value(DEFAULT_COMENTARIOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.comentarios").value(Base64Utils.encodeToString(DEFAULT_COMENTARIOS)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingViaje() throws Exception {
        // Get the viaje
        restViajeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewViaje() throws Exception {
        // Initialize the database
        viajeRepository.saveAndFlush(viaje);

        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();

        // Update the viaje
        Viaje updatedViaje = viajeRepository.findById(viaje.getId()).get();
        // Disconnect from session so that the updates on updatedViaje are not directly saved in db
        em.detach(updatedViaje);
        updatedViaje
            .fecha(UPDATED_FECHA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaEmbarque(UPDATED_HORA_EMBARQUE)
            .tipoCarga(UPDATED_TIPO_CARGA)
            .pesoNeto(UPDATED_PESO_NETO)
            .comentarios(UPDATED_COMENTARIOS)
            .comentariosContentType(UPDATED_COMENTARIOS_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restViajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedViaje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedViaje))
            )
            .andExpect(status().isOk());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
        Viaje testViaje = viajeList.get(viajeList.size() - 1);
        assertThat(testViaje.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testViaje.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testViaje.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testViaje.getHoraEmbarque()).isEqualTo(UPDATED_HORA_EMBARQUE);
        assertThat(testViaje.getTipoCarga()).isEqualTo(UPDATED_TIPO_CARGA);
        assertThat(testViaje.getPesoNeto()).isEqualTo(UPDATED_PESO_NETO);
        assertThat(testViaje.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
        assertThat(testViaje.getComentariosContentType()).isEqualTo(UPDATED_COMENTARIOS_CONTENT_TYPE);
        assertThat(testViaje.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testViaje.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testViaje.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testViaje.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testViaje.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testViaje.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingViaje() throws Exception {
        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();
        viaje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, viaje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchViaje() throws Exception {
        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();
        viaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamViaje() throws Exception {
        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();
        viaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViajeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viaje)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateViajeWithPatch() throws Exception {
        // Initialize the database
        viajeRepository.saveAndFlush(viaje);

        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();

        // Update the viaje using partial update
        Viaje partialUpdatedViaje = new Viaje();
        partialUpdatedViaje.setId(viaje.getId());

        partialUpdatedViaje
            .horaEmbarque(UPDATED_HORA_EMBARQUE)
            .pesoNeto(UPDATED_PESO_NETO)
            .status(UPDATED_STATUS)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViaje))
            )
            .andExpect(status().isOk());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
        Viaje testViaje = viajeList.get(viajeList.size() - 1);
        assertThat(testViaje.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testViaje.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testViaje.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testViaje.getHoraEmbarque()).isEqualTo(UPDATED_HORA_EMBARQUE);
        assertThat(testViaje.getTipoCarga()).isEqualTo(DEFAULT_TIPO_CARGA);
        assertThat(testViaje.getPesoNeto()).isEqualTo(UPDATED_PESO_NETO);
        assertThat(testViaje.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
        assertThat(testViaje.getComentariosContentType()).isEqualTo(DEFAULT_COMENTARIOS_CONTENT_TYPE);
        assertThat(testViaje.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testViaje.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testViaje.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testViaje.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testViaje.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testViaje.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateViajeWithPatch() throws Exception {
        // Initialize the database
        viajeRepository.saveAndFlush(viaje);

        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();

        // Update the viaje using partial update
        Viaje partialUpdatedViaje = new Viaje();
        partialUpdatedViaje.setId(viaje.getId());

        partialUpdatedViaje
            .fecha(UPDATED_FECHA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .horaEmbarque(UPDATED_HORA_EMBARQUE)
            .tipoCarga(UPDATED_TIPO_CARGA)
            .pesoNeto(UPDATED_PESO_NETO)
            .comentarios(UPDATED_COMENTARIOS)
            .comentariosContentType(UPDATED_COMENTARIOS_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViaje))
            )
            .andExpect(status().isOk());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
        Viaje testViaje = viajeList.get(viajeList.size() - 1);
        assertThat(testViaje.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testViaje.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testViaje.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testViaje.getHoraEmbarque()).isEqualTo(UPDATED_HORA_EMBARQUE);
        assertThat(testViaje.getTipoCarga()).isEqualTo(UPDATED_TIPO_CARGA);
        assertThat(testViaje.getPesoNeto()).isEqualTo(UPDATED_PESO_NETO);
        assertThat(testViaje.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
        assertThat(testViaje.getComentariosContentType()).isEqualTo(UPDATED_COMENTARIOS_CONTENT_TYPE);
        assertThat(testViaje.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testViaje.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testViaje.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testViaje.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testViaje.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testViaje.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingViaje() throws Exception {
        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();
        viaje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, viaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchViaje() throws Exception {
        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();
        viaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viaje))
            )
            .andExpect(status().isBadRequest());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamViaje() throws Exception {
        int databaseSizeBeforeUpdate = viajeRepository.findAll().size();
        viaje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViajeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(viaje)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Viaje in the database
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteViaje() throws Exception {
        // Initialize the database
        viajeRepository.saveAndFlush(viaje);

        int databaseSizeBeforeDelete = viajeRepository.findAll().size();

        // Delete the viaje
        restViajeMockMvc
            .perform(delete(ENTITY_API_URL_ID, viaje.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Viaje> viajeList = viajeRepository.findAll();
        assertThat(viajeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
