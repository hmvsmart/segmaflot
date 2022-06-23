package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ListaPedido;
import com.mycompany.myapp.repository.ListaPedidoRepository;
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
 * Integration tests for the {@link ListaPedidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListaPedidoResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final String ENTITY_API_URL = "/api/lista-pedidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListaPedidoRepository listaPedidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListaPedidoMockMvc;

    private ListaPedido listaPedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaPedido createEntity(EntityManager em) {
        ListaPedido listaPedido = new ListaPedido().cantidad(DEFAULT_CANTIDAD);
        return listaPedido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaPedido createUpdatedEntity(EntityManager em) {
        ListaPedido listaPedido = new ListaPedido().cantidad(UPDATED_CANTIDAD);
        return listaPedido;
    }

    @BeforeEach
    public void initTest() {
        listaPedido = createEntity(em);
    }

    @Test
    @Transactional
    void createListaPedido() throws Exception {
        int databaseSizeBeforeCreate = listaPedidoRepository.findAll().size();
        // Create the ListaPedido
        restListaPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPedido)))
            .andExpect(status().isCreated());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        ListaPedido testListaPedido = listaPedidoList.get(listaPedidoList.size() - 1);
        assertThat(testListaPedido.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    void createListaPedidoWithExistingId() throws Exception {
        // Create the ListaPedido with an existing ID
        listaPedido.setId(1L);

        int databaseSizeBeforeCreate = listaPedidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPedido)))
            .andExpect(status().isBadRequest());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListaPedidos() throws Exception {
        // Initialize the database
        listaPedidoRepository.saveAndFlush(listaPedido);

        // Get all the listaPedidoList
        restListaPedidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    void getListaPedido() throws Exception {
        // Initialize the database
        listaPedidoRepository.saveAndFlush(listaPedido);

        // Get the listaPedido
        restListaPedidoMockMvc
            .perform(get(ENTITY_API_URL_ID, listaPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listaPedido.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    void getNonExistingListaPedido() throws Exception {
        // Get the listaPedido
        restListaPedidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListaPedido() throws Exception {
        // Initialize the database
        listaPedidoRepository.saveAndFlush(listaPedido);

        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();

        // Update the listaPedido
        ListaPedido updatedListaPedido = listaPedidoRepository.findById(listaPedido.getId()).get();
        // Disconnect from session so that the updates on updatedListaPedido are not directly saved in db
        em.detach(updatedListaPedido);
        updatedListaPedido.cantidad(UPDATED_CANTIDAD);

        restListaPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListaPedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListaPedido))
            )
            .andExpect(status().isOk());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
        ListaPedido testListaPedido = listaPedidoList.get(listaPedidoList.size() - 1);
        assertThat(testListaPedido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void putNonExistingListaPedido() throws Exception {
        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();
        listaPedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listaPedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListaPedido() throws Exception {
        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();
        listaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListaPedido() throws Exception {
        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();
        listaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPedidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaPedido)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListaPedidoWithPatch() throws Exception {
        // Initialize the database
        listaPedidoRepository.saveAndFlush(listaPedido);

        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();

        // Update the listaPedido using partial update
        ListaPedido partialUpdatedListaPedido = new ListaPedido();
        partialUpdatedListaPedido.setId(listaPedido.getId());

        restListaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListaPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListaPedido))
            )
            .andExpect(status().isOk());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
        ListaPedido testListaPedido = listaPedidoList.get(listaPedidoList.size() - 1);
        assertThat(testListaPedido.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    void fullUpdateListaPedidoWithPatch() throws Exception {
        // Initialize the database
        listaPedidoRepository.saveAndFlush(listaPedido);

        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();

        // Update the listaPedido using partial update
        ListaPedido partialUpdatedListaPedido = new ListaPedido();
        partialUpdatedListaPedido.setId(listaPedido.getId());

        partialUpdatedListaPedido.cantidad(UPDATED_CANTIDAD);

        restListaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListaPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListaPedido))
            )
            .andExpect(status().isOk());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
        ListaPedido testListaPedido = listaPedidoList.get(listaPedidoList.size() - 1);
        assertThat(testListaPedido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    void patchNonExistingListaPedido() throws Exception {
        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();
        listaPedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listaPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListaPedido() throws Exception {
        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();
        listaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listaPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListaPedido() throws Exception {
        int databaseSizeBeforeUpdate = listaPedidoRepository.findAll().size();
        listaPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listaPedido))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListaPedido in the database
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListaPedido() throws Exception {
        // Initialize the database
        listaPedidoRepository.saveAndFlush(listaPedido);

        int databaseSizeBeforeDelete = listaPedidoRepository.findAll().size();

        // Delete the listaPedido
        restListaPedidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, listaPedido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListaPedido> listaPedidoList = listaPedidoRepository.findAll();
        assertThat(listaPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
