package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RenglonResurtir;
import com.mycompany.myapp.repository.RenglonResurtirRepository;
import java.math.BigDecimal;
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

/**
 * Integration tests for the {@link RenglonResurtirResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RenglonResurtirResourceIT {

    private static final LocalDate DEFAULT_FECHA_CADUCIDAD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CADUCIDAD = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final BigDecimal DEFAULT_PRECIO_COMPRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_COMPRA = new BigDecimal(2);

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

    private static final String ENTITY_API_URL = "/api/renglon-resurtirs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RenglonResurtirRepository renglonResurtirRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRenglonResurtirMockMvc;

    private RenglonResurtir renglonResurtir;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RenglonResurtir createEntity(EntityManager em) {
        RenglonResurtir renglonResurtir = new RenglonResurtir()
            .fechaCaducidad(DEFAULT_FECHA_CADUCIDAD)
            .cantidad(DEFAULT_CANTIDAD)
            .precioCompra(DEFAULT_PRECIO_COMPRA)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return renglonResurtir;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RenglonResurtir createUpdatedEntity(EntityManager em) {
        RenglonResurtir renglonResurtir = new RenglonResurtir()
            .fechaCaducidad(UPDATED_FECHA_CADUCIDAD)
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return renglonResurtir;
    }

    @BeforeEach
    public void initTest() {
        renglonResurtir = createEntity(em);
    }

    @Test
    @Transactional
    void createRenglonResurtir() throws Exception {
        int databaseSizeBeforeCreate = renglonResurtirRepository.findAll().size();
        // Create the RenglonResurtir
        restRenglonResurtirMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isCreated());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeCreate + 1);
        RenglonResurtir testRenglonResurtir = renglonResurtirList.get(renglonResurtirList.size() - 1);
        assertThat(testRenglonResurtir.getFechaCaducidad()).isEqualTo(DEFAULT_FECHA_CADUCIDAD);
        assertThat(testRenglonResurtir.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testRenglonResurtir.getPrecioCompra()).isEqualByComparingTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testRenglonResurtir.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testRenglonResurtir.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRenglonResurtir.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testRenglonResurtir.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testRenglonResurtir.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createRenglonResurtirWithExistingId() throws Exception {
        // Create the RenglonResurtir with an existing ID
        renglonResurtir.setId(1L);

        int databaseSizeBeforeCreate = renglonResurtirRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRenglonResurtirMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRenglonResurtirs() throws Exception {
        // Initialize the database
        renglonResurtirRepository.saveAndFlush(renglonResurtir);

        // Get all the renglonResurtirList
        restRenglonResurtirMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(renglonResurtir.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCaducidad").value(hasItem(DEFAULT_FECHA_CADUCIDAD.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(sameNumber(DEFAULT_PRECIO_COMPRA))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getRenglonResurtir() throws Exception {
        // Initialize the database
        renglonResurtirRepository.saveAndFlush(renglonResurtir);

        // Get the renglonResurtir
        restRenglonResurtirMockMvc
            .perform(get(ENTITY_API_URL_ID, renglonResurtir.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(renglonResurtir.getId().intValue()))
            .andExpect(jsonPath("$.fechaCaducidad").value(DEFAULT_FECHA_CADUCIDAD.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precioCompra").value(sameNumber(DEFAULT_PRECIO_COMPRA)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingRenglonResurtir() throws Exception {
        // Get the renglonResurtir
        restRenglonResurtirMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRenglonResurtir() throws Exception {
        // Initialize the database
        renglonResurtirRepository.saveAndFlush(renglonResurtir);

        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();

        // Update the renglonResurtir
        RenglonResurtir updatedRenglonResurtir = renglonResurtirRepository.findById(renglonResurtir.getId()).get();
        // Disconnect from session so that the updates on updatedRenglonResurtir are not directly saved in db
        em.detach(updatedRenglonResurtir);
        updatedRenglonResurtir
            .fechaCaducidad(UPDATED_FECHA_CADUCIDAD)
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restRenglonResurtirMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRenglonResurtir.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRenglonResurtir))
            )
            .andExpect(status().isOk());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
        RenglonResurtir testRenglonResurtir = renglonResurtirList.get(renglonResurtirList.size() - 1);
        assertThat(testRenglonResurtir.getFechaCaducidad()).isEqualTo(UPDATED_FECHA_CADUCIDAD);
        assertThat(testRenglonResurtir.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRenglonResurtir.getPrecioCompra()).isEqualByComparingTo(UPDATED_PRECIO_COMPRA);
        assertThat(testRenglonResurtir.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testRenglonResurtir.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRenglonResurtir.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testRenglonResurtir.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testRenglonResurtir.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRenglonResurtir() throws Exception {
        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();
        renglonResurtir.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRenglonResurtirMockMvc
            .perform(
                put(ENTITY_API_URL_ID, renglonResurtir.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRenglonResurtir() throws Exception {
        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();
        renglonResurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonResurtirMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRenglonResurtir() throws Exception {
        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();
        renglonResurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonResurtirMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRenglonResurtirWithPatch() throws Exception {
        // Initialize the database
        renglonResurtirRepository.saveAndFlush(renglonResurtir);

        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();

        // Update the renglonResurtir using partial update
        RenglonResurtir partialUpdatedRenglonResurtir = new RenglonResurtir();
        partialUpdatedRenglonResurtir.setId(renglonResurtir.getId());

        partialUpdatedRenglonResurtir.cantidad(UPDATED_CANTIDAD).createByUser(UPDATED_CREATE_BY_USER);

        restRenglonResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRenglonResurtir.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRenglonResurtir))
            )
            .andExpect(status().isOk());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
        RenglonResurtir testRenglonResurtir = renglonResurtirList.get(renglonResurtirList.size() - 1);
        assertThat(testRenglonResurtir.getFechaCaducidad()).isEqualTo(DEFAULT_FECHA_CADUCIDAD);
        assertThat(testRenglonResurtir.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRenglonResurtir.getPrecioCompra()).isEqualByComparingTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testRenglonResurtir.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testRenglonResurtir.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRenglonResurtir.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testRenglonResurtir.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testRenglonResurtir.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRenglonResurtirWithPatch() throws Exception {
        // Initialize the database
        renglonResurtirRepository.saveAndFlush(renglonResurtir);

        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();

        // Update the renglonResurtir using partial update
        RenglonResurtir partialUpdatedRenglonResurtir = new RenglonResurtir();
        partialUpdatedRenglonResurtir.setId(renglonResurtir.getId());

        partialUpdatedRenglonResurtir
            .fechaCaducidad(UPDATED_FECHA_CADUCIDAD)
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restRenglonResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRenglonResurtir.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRenglonResurtir))
            )
            .andExpect(status().isOk());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
        RenglonResurtir testRenglonResurtir = renglonResurtirList.get(renglonResurtirList.size() - 1);
        assertThat(testRenglonResurtir.getFechaCaducidad()).isEqualTo(UPDATED_FECHA_CADUCIDAD);
        assertThat(testRenglonResurtir.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRenglonResurtir.getPrecioCompra()).isEqualByComparingTo(UPDATED_PRECIO_COMPRA);
        assertThat(testRenglonResurtir.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testRenglonResurtir.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRenglonResurtir.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testRenglonResurtir.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testRenglonResurtir.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRenglonResurtir() throws Exception {
        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();
        renglonResurtir.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRenglonResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, renglonResurtir.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRenglonResurtir() throws Exception {
        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();
        renglonResurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isBadRequest());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRenglonResurtir() throws Exception {
        int databaseSizeBeforeUpdate = renglonResurtirRepository.findAll().size();
        renglonResurtir.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRenglonResurtirMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(renglonResurtir))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RenglonResurtir in the database
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRenglonResurtir() throws Exception {
        // Initialize the database
        renglonResurtirRepository.saveAndFlush(renglonResurtir);

        int databaseSizeBeforeDelete = renglonResurtirRepository.findAll().size();

        // Delete the renglonResurtir
        restRenglonResurtirMockMvc
            .perform(delete(ENTITY_API_URL_ID, renglonResurtir.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RenglonResurtir> renglonResurtirList = renglonResurtirRepository.findAll();
        assertThat(renglonResurtirList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
