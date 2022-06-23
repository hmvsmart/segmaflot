package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.VentaPedido;
import com.mycompany.myapp.repository.VentaPedidoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link VentaPedidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VentaPedidoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/venta-pedidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VentaPedidoRepository ventaPedidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVentaPedidoMockMvc;

    private VentaPedido ventaPedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentaPedido createEntity(EntityManager em) {
        VentaPedido ventaPedido = new VentaPedido().fecha(DEFAULT_FECHA);
        return ventaPedido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VentaPedido createUpdatedEntity(EntityManager em) {
        VentaPedido ventaPedido = new VentaPedido().fecha(UPDATED_FECHA);
        return ventaPedido;
    }

    @BeforeEach
    public void initTest() {
        ventaPedido = createEntity(em);
    }

    @Test
    @Transactional
    void createVentaPedido() throws Exception {
        int databaseSizeBeforeCreate = ventaPedidoRepository.findAll().size();
        // Create the VentaPedido
        restVentaPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaPedido)))
            .andExpect(status().isCreated());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        VentaPedido testVentaPedido = ventaPedidoList.get(ventaPedidoList.size() - 1);
        assertThat(testVentaPedido.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void createVentaPedidoWithExistingId() throws Exception {
        // Create the VentaPedido with an existing ID
        ventaPedido.setId(1L);

        int databaseSizeBeforeCreate = ventaPedidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaPedido)))
            .andExpect(status().isBadRequest());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVentaPedidos() throws Exception {
        // Initialize the database
        ventaPedidoRepository.saveAndFlush(ventaPedido);

        // Get all the ventaPedidoList
        restVentaPedidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventaPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    void getVentaPedido() throws Exception {
        // Initialize the database
        ventaPedidoRepository.saveAndFlush(ventaPedido);

        // Get the ventaPedido
        restVentaPedidoMockMvc
            .perform(get(ENTITY_API_URL_ID, ventaPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ventaPedido.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingVentaPedido() throws Exception {
        // Get the ventaPedido
        restVentaPedidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVentaPedido() throws Exception {
        // Initialize the database
        ventaPedidoRepository.saveAndFlush(ventaPedido);

        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();

        // Update the ventaPedido
        VentaPedido updatedVentaPedido = ventaPedidoRepository.findById(ventaPedido.getId()).get();
        // Disconnect from session so that the updates on updatedVentaPedido are not directly saved in db
        em.detach(updatedVentaPedido);
        updatedVentaPedido.fecha(UPDATED_FECHA);

        restVentaPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVentaPedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVentaPedido))
            )
            .andExpect(status().isOk());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
        VentaPedido testVentaPedido = ventaPedidoList.get(ventaPedidoList.size() - 1);
        assertThat(testVentaPedido.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void putNonExistingVentaPedido() throws Exception {
        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();
        ventaPedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ventaPedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVentaPedido() throws Exception {
        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();
        ventaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVentaPedido() throws Exception {
        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();
        ventaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaPedidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventaPedido)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVentaPedidoWithPatch() throws Exception {
        // Initialize the database
        ventaPedidoRepository.saveAndFlush(ventaPedido);

        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();

        // Update the ventaPedido using partial update
        VentaPedido partialUpdatedVentaPedido = new VentaPedido();
        partialUpdatedVentaPedido.setId(ventaPedido.getId());

        restVentaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentaPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentaPedido))
            )
            .andExpect(status().isOk());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
        VentaPedido testVentaPedido = ventaPedidoList.get(ventaPedidoList.size() - 1);
        assertThat(testVentaPedido.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void fullUpdateVentaPedidoWithPatch() throws Exception {
        // Initialize the database
        ventaPedidoRepository.saveAndFlush(ventaPedido);

        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();

        // Update the ventaPedido using partial update
        VentaPedido partialUpdatedVentaPedido = new VentaPedido();
        partialUpdatedVentaPedido.setId(ventaPedido.getId());

        partialUpdatedVentaPedido.fecha(UPDATED_FECHA);

        restVentaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentaPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentaPedido))
            )
            .andExpect(status().isOk());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
        VentaPedido testVentaPedido = ventaPedidoList.get(ventaPedidoList.size() - 1);
        assertThat(testVentaPedido.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void patchNonExistingVentaPedido() throws Exception {
        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();
        ventaPedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ventaPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVentaPedido() throws Exception {
        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();
        ventaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVentaPedido() throws Exception {
        int databaseSizeBeforeUpdate = ventaPedidoRepository.findAll().size();
        ventaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ventaPedido))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VentaPedido in the database
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVentaPedido() throws Exception {
        // Initialize the database
        ventaPedidoRepository.saveAndFlush(ventaPedido);

        int databaseSizeBeforeDelete = ventaPedidoRepository.findAll().size();

        // Delete the ventaPedido
        restVentaPedidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ventaPedido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VentaPedido> ventaPedidoList = ventaPedidoRepository.findAll();
        assertThat(ventaPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
