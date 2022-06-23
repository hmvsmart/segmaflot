package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RenglonVenta;
import com.mycompany.myapp.repository.RenglonVentaRepository;
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
 * Integration tests for the {@link RenglonVentaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RenglonVentaResourceIT {

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

    private static final String ENTITY_API_URL = "/api/renglon-ventas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RenglonVentaRepository renglonVentaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRenglonVentaMockMvc;

    private RenglonVenta renglonVenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RenglonVenta createEntity(EntityManager em) {
        RenglonVenta renglonVenta = new RenglonVenta()
            .cantidad(DEFAULT_CANTIDAD)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return renglonVenta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RenglonVenta createUpdatedEntity(EntityManager em) {
        RenglonVenta renglonVenta = new RenglonVenta()
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return renglonVenta;
    }

    @BeforeEach
    public void initTest() {
        renglonVenta = createEntity(em);
    }

    @Test
    @Transactional
    void createRenglonVenta() throws Exception {
        int databaseSizeBeforeCreate = renglonVentaRepository.findAll().size();
        // Create the RenglonVenta
        restRenglonVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(renglonVenta)))
            .andExpect(status().isCreated());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeCreate + 1);
        RenglonVenta testRenglonVenta = renglonVentaList.get(renglonVentaList.size() - 1);
        assertThat(testRenglonVenta.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testRenglonVenta.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testRenglonVenta.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRenglonVenta.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testRenglonVenta.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testRenglonVenta.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createRenglonVentaWithExistingId() throws Exception {
        // Create the RenglonVenta with an existing ID
        renglonVenta.setId(1L);

        int databaseSizeBeforeCreate = renglonVentaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRenglonVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(renglonVenta)))
            .andExpect(status().isBadRequest());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRenglonVentas() throws Exception {
        // Initialize the database
        renglonVentaRepository.saveAndFlush(renglonVenta);

        // Get all the renglonVentaList
        restRenglonVentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(renglonVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getRenglonVenta() throws Exception {
        // Initialize the database
        renglonVentaRepository.saveAndFlush(renglonVenta);

        // Get the renglonVenta
        restRenglonVentaMockMvc
            .perform(get(ENTITY_API_URL_ID, renglonVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(renglonVenta.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingRenglonVenta() throws Exception {
        // Get the renglonVenta
        restRenglonVentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRenglonVenta() throws Exception {
        // Initialize the database
        renglonVentaRepository.saveAndFlush(renglonVenta);

        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();

        // Update the renglonVenta
        RenglonVenta updatedRenglonVenta = renglonVentaRepository.findById(renglonVenta.getId()).get();
        // Disconnect from session so that the updates on updatedRenglonVenta are not directly saved in db
        em.detach(updatedRenglonVenta);
        updatedRenglonVenta
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restRenglonVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRenglonVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRenglonVenta))
            )
            .andExpect(status().isOk());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
        RenglonVenta testRenglonVenta = renglonVentaList.get(renglonVentaList.size() - 1);
        assertThat(testRenglonVenta.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRenglonVenta.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testRenglonVenta.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRenglonVenta.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testRenglonVenta.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testRenglonVenta.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRenglonVenta() throws Exception {
        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();
        renglonVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRenglonVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, renglonVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(renglonVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRenglonVenta() throws Exception {
        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();
        renglonVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(renglonVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRenglonVenta() throws Exception {
        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();
        renglonVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonVentaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(renglonVenta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRenglonVentaWithPatch() throws Exception {
        // Initialize the database
        renglonVentaRepository.saveAndFlush(renglonVenta);

        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();

        // Update the renglonVenta using partial update
        RenglonVenta partialUpdatedRenglonVenta = new RenglonVenta();
        partialUpdatedRenglonVenta.setId(renglonVenta.getId());

        partialUpdatedRenglonVenta.cantidad(UPDATED_CANTIDAD).modifyByUser(UPDATED_MODIFY_BY_USER).modifiedOn(UPDATED_MODIFIED_ON);

        restRenglonVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRenglonVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRenglonVenta))
            )
            .andExpect(status().isOk());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
        RenglonVenta testRenglonVenta = renglonVentaList.get(renglonVentaList.size() - 1);
        assertThat(testRenglonVenta.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRenglonVenta.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testRenglonVenta.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRenglonVenta.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testRenglonVenta.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testRenglonVenta.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRenglonVentaWithPatch() throws Exception {
        // Initialize the database
        renglonVentaRepository.saveAndFlush(renglonVenta);

        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();

        // Update the renglonVenta using partial update
        RenglonVenta partialUpdatedRenglonVenta = new RenglonVenta();
        partialUpdatedRenglonVenta.setId(renglonVenta.getId());

        partialUpdatedRenglonVenta
            .cantidad(UPDATED_CANTIDAD)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restRenglonVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRenglonVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRenglonVenta))
            )
            .andExpect(status().isOk());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
        RenglonVenta testRenglonVenta = renglonVentaList.get(renglonVentaList.size() - 1);
        assertThat(testRenglonVenta.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRenglonVenta.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testRenglonVenta.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRenglonVenta.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testRenglonVenta.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testRenglonVenta.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRenglonVenta() throws Exception {
        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();
        renglonVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRenglonVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, renglonVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(renglonVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRenglonVenta() throws Exception {
        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();
        renglonVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(renglonVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRenglonVenta() throws Exception {
        int databaseSizeBeforeUpdate = renglonVentaRepository.findAll().size();
        renglonVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonVentaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(renglonVenta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RenglonVenta in the database
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRenglonVenta() throws Exception {
        // Initialize the database
        renglonVentaRepository.saveAndFlush(renglonVenta);

        int databaseSizeBeforeDelete = renglonVentaRepository.findAll().size();

        // Delete the renglonVenta
        restRenglonVentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, renglonVenta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RenglonVenta> renglonVentaList = renglonVentaRepository.findAll();
        assertThat(renglonVentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
