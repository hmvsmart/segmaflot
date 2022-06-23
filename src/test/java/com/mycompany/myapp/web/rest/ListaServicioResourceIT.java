package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ListaServicio;
import com.mycompany.myapp.repository.ListaServicioRepository;
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
 * Integration tests for the {@link ListaServicioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListaServicioResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

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

    private static final String ENTITY_API_URL = "/api/lista-servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListaServicioRepository listaServicioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListaServicioMockMvc;

    private ListaServicio listaServicio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaServicio createEntity(EntityManager em) {
        ListaServicio listaServicio = new ListaServicio()
            .cantidad(DEFAULT_CANTIDAD)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return listaServicio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListaServicio createUpdatedEntity(EntityManager em) {
        ListaServicio listaServicio = new ListaServicio()
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return listaServicio;
    }

    @BeforeEach
    public void initTest() {
        listaServicio = createEntity(em);
    }

    @Test
    @Transactional
    void createListaServicio() throws Exception {
        int databaseSizeBeforeCreate = listaServicioRepository.findAll().size();
        // Create the ListaServicio
        restListaServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaServicio)))
            .andExpect(status().isCreated());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeCreate + 1);
        ListaServicio testListaServicio = listaServicioList.get(listaServicioList.size() - 1);
        assertThat(testListaServicio.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testListaServicio.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testListaServicio.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testListaServicio.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testListaServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testListaServicio.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createListaServicioWithExistingId() throws Exception {
        // Create the ListaServicio with an existing ID
        listaServicio.setId(1L);

        int databaseSizeBeforeCreate = listaServicioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaServicioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaServicio)))
            .andExpect(status().isBadRequest());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListaServicios() throws Exception {
        // Initialize the database
        listaServicioRepository.saveAndFlush(listaServicio);

        // Get all the listaServicioList
        restListaServicioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listaServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getListaServicio() throws Exception {
        // Initialize the database
        listaServicioRepository.saveAndFlush(listaServicio);

        // Get the listaServicio
        restListaServicioMockMvc
            .perform(get(ENTITY_API_URL_ID, listaServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listaServicio.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingListaServicio() throws Exception {
        // Get the listaServicio
        restListaServicioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListaServicio() throws Exception {
        // Initialize the database
        listaServicioRepository.saveAndFlush(listaServicio);

        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();

        // Update the listaServicio
        ListaServicio updatedListaServicio = listaServicioRepository.findById(listaServicio.getId()).get();
        // Disconnect from session so that the updates on updatedListaServicio are not directly saved in db
        em.detach(updatedListaServicio);
        updatedListaServicio
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restListaServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListaServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListaServicio))
            )
            .andExpect(status().isOk());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
        ListaServicio testListaServicio = listaServicioList.get(listaServicioList.size() - 1);
        assertThat(testListaServicio.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testListaServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testListaServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testListaServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testListaServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testListaServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingListaServicio() throws Exception {
        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();
        listaServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listaServicio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListaServicio() throws Exception {
        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();
        listaServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaServicioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listaServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListaServicio() throws Exception {
        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();
        listaServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaServicioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listaServicio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListaServicioWithPatch() throws Exception {
        // Initialize the database
        listaServicioRepository.saveAndFlush(listaServicio);

        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();

        // Update the listaServicio using partial update
        ListaServicio partialUpdatedListaServicio = new ListaServicio();
        partialUpdatedListaServicio.setId(listaServicio.getId());

        partialUpdatedListaServicio
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restListaServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListaServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListaServicio))
            )
            .andExpect(status().isOk());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
        ListaServicio testListaServicio = listaServicioList.get(listaServicioList.size() - 1);
        assertThat(testListaServicio.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testListaServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testListaServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testListaServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testListaServicio.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testListaServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateListaServicioWithPatch() throws Exception {
        // Initialize the database
        listaServicioRepository.saveAndFlush(listaServicio);

        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();

        // Update the listaServicio using partial update
        ListaServicio partialUpdatedListaServicio = new ListaServicio();
        partialUpdatedListaServicio.setId(listaServicio.getId());

        partialUpdatedListaServicio
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restListaServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListaServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListaServicio))
            )
            .andExpect(status().isOk());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
        ListaServicio testListaServicio = listaServicioList.get(listaServicioList.size() - 1);
        assertThat(testListaServicio.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testListaServicio.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testListaServicio.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testListaServicio.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testListaServicio.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testListaServicio.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingListaServicio() throws Exception {
        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();
        listaServicio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listaServicio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listaServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListaServicio() throws Exception {
        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();
        listaServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaServicioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listaServicio))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListaServicio() throws Exception {
        int databaseSizeBeforeUpdate = listaServicioRepository.findAll().size();
        listaServicio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListaServicioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(listaServicio))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListaServicio in the database
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListaServicio() throws Exception {
        // Initialize the database
        listaServicioRepository.saveAndFlush(listaServicio);

        int databaseSizeBeforeDelete = listaServicioRepository.findAll().size();

        // Delete the listaServicio
        restListaServicioMockMvc
            .perform(delete(ENTITY_API_URL_ID, listaServicio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListaServicio> listaServicioList = listaServicioRepository.findAll();
        assertThat(listaServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
