package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Perdida;
import com.mycompany.myapp.repository.PerdidaRepository;
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
 * Integration tests for the {@link PerdidaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerdidaResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final byte[] DEFAULT_OBSERVACIONES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OBSERVACIONES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OBSERVACIONES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OBSERVACIONES_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/perdidas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PerdidaRepository perdidaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerdidaMockMvc;

    private Perdida perdida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perdida createEntity(EntityManager em) {
        Perdida perdida = new Perdida()
            .fecha(DEFAULT_FECHA)
            .cantidad(DEFAULT_CANTIDAD)
            .observaciones(DEFAULT_OBSERVACIONES)
            .observacionesContentType(DEFAULT_OBSERVACIONES_CONTENT_TYPE);
        return perdida;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perdida createUpdatedEntity(EntityManager em) {
        Perdida perdida = new Perdida()
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .observaciones(UPDATED_OBSERVACIONES)
            .observacionesContentType(UPDATED_OBSERVACIONES_CONTENT_TYPE);
        return perdida;
    }

    @BeforeEach
    public void initTest() {
        perdida = createEntity(em);
    }

    @Test
    @Transactional
    void createPerdida() throws Exception {
        int databaseSizeBeforeCreate = perdidaRepository.findAll().size();
        // Create the Perdida
        restPerdidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(perdida)))
            .andExpect(status().isCreated());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeCreate + 1);
        Perdida testPerdida = perdidaList.get(perdidaList.size() - 1);
        assertThat(testPerdida.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPerdida.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testPerdida.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testPerdida.getObservacionesContentType()).isEqualTo(DEFAULT_OBSERVACIONES_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPerdidaWithExistingId() throws Exception {
        // Create the Perdida with an existing ID
        perdida.setId(1L);

        int databaseSizeBeforeCreate = perdidaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerdidaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(perdida)))
            .andExpect(status().isBadRequest());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerdidas() throws Exception {
        // Initialize the database
        perdidaRepository.saveAndFlush(perdida);

        // Get all the perdidaList
        restPerdidaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perdida.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].observacionesContentType").value(hasItem(DEFAULT_OBSERVACIONES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(Base64Utils.encodeToString(DEFAULT_OBSERVACIONES))));
    }

    @Test
    @Transactional
    void getPerdida() throws Exception {
        // Initialize the database
        perdidaRepository.saveAndFlush(perdida);

        // Get the perdida
        restPerdidaMockMvc
            .perform(get(ENTITY_API_URL_ID, perdida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perdida.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.observacionesContentType").value(DEFAULT_OBSERVACIONES_CONTENT_TYPE))
            .andExpect(jsonPath("$.observaciones").value(Base64Utils.encodeToString(DEFAULT_OBSERVACIONES)));
    }

    @Test
    @Transactional
    void getNonExistingPerdida() throws Exception {
        // Get the perdida
        restPerdidaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPerdida() throws Exception {
        // Initialize the database
        perdidaRepository.saveAndFlush(perdida);

        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();

        // Update the perdida
        Perdida updatedPerdida = perdidaRepository.findById(perdida.getId()).get();
        // Disconnect from session so that the updates on updatedPerdida are not directly saved in db
        em.detach(updatedPerdida);
        updatedPerdida
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .observaciones(UPDATED_OBSERVACIONES)
            .observacionesContentType(UPDATED_OBSERVACIONES_CONTENT_TYPE);

        restPerdidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerdida.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPerdida))
            )
            .andExpect(status().isOk());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
        Perdida testPerdida = perdidaList.get(perdidaList.size() - 1);
        assertThat(testPerdida.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPerdida.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPerdida.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testPerdida.getObservacionesContentType()).isEqualTo(UPDATED_OBSERVACIONES_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPerdida() throws Exception {
        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();
        perdida.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerdidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perdida.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(perdida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerdida() throws Exception {
        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();
        perdida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerdidaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(perdida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerdida() throws Exception {
        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();
        perdida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerdidaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(perdida)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerdidaWithPatch() throws Exception {
        // Initialize the database
        perdidaRepository.saveAndFlush(perdida);

        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();

        // Update the perdida using partial update
        Perdida partialUpdatedPerdida = new Perdida();
        partialUpdatedPerdida.setId(perdida.getId());

        partialUpdatedPerdida
            .cantidad(UPDATED_CANTIDAD)
            .observaciones(UPDATED_OBSERVACIONES)
            .observacionesContentType(UPDATED_OBSERVACIONES_CONTENT_TYPE);

        restPerdidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerdida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerdida))
            )
            .andExpect(status().isOk());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
        Perdida testPerdida = perdidaList.get(perdidaList.size() - 1);
        assertThat(testPerdida.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPerdida.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPerdida.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testPerdida.getObservacionesContentType()).isEqualTo(UPDATED_OBSERVACIONES_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePerdidaWithPatch() throws Exception {
        // Initialize the database
        perdidaRepository.saveAndFlush(perdida);

        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();

        // Update the perdida using partial update
        Perdida partialUpdatedPerdida = new Perdida();
        partialUpdatedPerdida.setId(perdida.getId());

        partialUpdatedPerdida
            .fecha(UPDATED_FECHA)
            .cantidad(UPDATED_CANTIDAD)
            .observaciones(UPDATED_OBSERVACIONES)
            .observacionesContentType(UPDATED_OBSERVACIONES_CONTENT_TYPE);

        restPerdidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerdida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerdida))
            )
            .andExpect(status().isOk());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
        Perdida testPerdida = perdidaList.get(perdidaList.size() - 1);
        assertThat(testPerdida.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPerdida.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testPerdida.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testPerdida.getObservacionesContentType()).isEqualTo(UPDATED_OBSERVACIONES_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPerdida() throws Exception {
        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();
        perdida.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerdidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perdida.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(perdida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerdida() throws Exception {
        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();
        perdida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerdidaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(perdida))
            )
            .andExpect(status().isBadRequest());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerdida() throws Exception {
        int databaseSizeBeforeUpdate = perdidaRepository.findAll().size();
        perdida.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerdidaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(perdida)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Perdida in the database
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerdida() throws Exception {
        // Initialize the database
        perdidaRepository.saveAndFlush(perdida);

        int databaseSizeBeforeDelete = perdidaRepository.findAll().size();

        // Delete the perdida
        restPerdidaMockMvc
            .perform(delete(ENTITY_API_URL_ID, perdida.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Perdida> perdidaList = perdidaRepository.findAll();
        assertThat(perdidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
