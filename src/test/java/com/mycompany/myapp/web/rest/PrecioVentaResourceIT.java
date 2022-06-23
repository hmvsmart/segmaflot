package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PrecioVenta;
import com.mycompany.myapp.repository.PrecioVentaRepository;
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
 * Integration tests for the {@link PrecioVentaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrecioVentaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PPU = new BigDecimal(1);
    private static final BigDecimal UPDATED_PPU = new BigDecimal(2);

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

    private static final String ENTITY_API_URL = "/api/precio-ventas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrecioVentaRepository precioVentaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrecioVentaMockMvc;

    private PrecioVenta precioVenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrecioVenta createEntity(EntityManager em) {
        PrecioVenta precioVenta = new PrecioVenta()
            .fecha(DEFAULT_FECHA)
            .ppu(DEFAULT_PPU)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return precioVenta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrecioVenta createUpdatedEntity(EntityManager em) {
        PrecioVenta precioVenta = new PrecioVenta()
            .fecha(UPDATED_FECHA)
            .ppu(UPDATED_PPU)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return precioVenta;
    }

    @BeforeEach
    public void initTest() {
        precioVenta = createEntity(em);
    }

    @Test
    @Transactional
    void createPrecioVenta() throws Exception {
        int databaseSizeBeforeCreate = precioVentaRepository.findAll().size();
        // Create the PrecioVenta
        restPrecioVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioVenta)))
            .andExpect(status().isCreated());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeCreate + 1);
        PrecioVenta testPrecioVenta = precioVentaList.get(precioVentaList.size() - 1);
        assertThat(testPrecioVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPrecioVenta.getPpu()).isEqualByComparingTo(DEFAULT_PPU);
        assertThat(testPrecioVenta.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPrecioVenta.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPrecioVenta.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPrecioVenta.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPrecioVenta.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createPrecioVentaWithExistingId() throws Exception {
        // Create the PrecioVenta with an existing ID
        precioVenta.setId(1L);

        int databaseSizeBeforeCreate = precioVentaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecioVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioVenta)))
            .andExpect(status().isBadRequest());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrecioVentas() throws Exception {
        // Initialize the database
        precioVentaRepository.saveAndFlush(precioVenta);

        // Get all the precioVentaList
        restPrecioVentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precioVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].ppu").value(hasItem(sameNumber(DEFAULT_PPU))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getPrecioVenta() throws Exception {
        // Initialize the database
        precioVentaRepository.saveAndFlush(precioVenta);

        // Get the precioVenta
        restPrecioVentaMockMvc
            .perform(get(ENTITY_API_URL_ID, precioVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(precioVenta.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.ppu").value(sameNumber(DEFAULT_PPU)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPrecioVenta() throws Exception {
        // Get the precioVenta
        restPrecioVentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPrecioVenta() throws Exception {
        // Initialize the database
        precioVentaRepository.saveAndFlush(precioVenta);

        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();

        // Update the precioVenta
        PrecioVenta updatedPrecioVenta = precioVentaRepository.findById(precioVenta.getId()).get();
        // Disconnect from session so that the updates on updatedPrecioVenta are not directly saved in db
        em.detach(updatedPrecioVenta);
        updatedPrecioVenta
            .fecha(UPDATED_FECHA)
            .ppu(UPDATED_PPU)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPrecioVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrecioVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrecioVenta))
            )
            .andExpect(status().isOk());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
        PrecioVenta testPrecioVenta = precioVentaList.get(precioVentaList.size() - 1);
        assertThat(testPrecioVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPrecioVenta.getPpu()).isEqualByComparingTo(UPDATED_PPU);
        assertThat(testPrecioVenta.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPrecioVenta.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPrecioVenta.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPrecioVenta.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPrecioVenta.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPrecioVenta() throws Exception {
        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();
        precioVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, precioVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrecioVenta() throws Exception {
        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();
        precioVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(precioVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrecioVenta() throws Exception {
        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();
        precioVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioVentaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(precioVenta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrecioVentaWithPatch() throws Exception {
        // Initialize the database
        precioVentaRepository.saveAndFlush(precioVenta);

        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();

        // Update the precioVenta using partial update
        PrecioVenta partialUpdatedPrecioVenta = new PrecioVenta();
        partialUpdatedPrecioVenta.setId(precioVenta.getId());

        partialUpdatedPrecioVenta.fecha(UPDATED_FECHA).ppu(UPDATED_PPU).auditStatus(UPDATED_AUDIT_STATUS);

        restPrecioVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecioVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecioVenta))
            )
            .andExpect(status().isOk());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
        PrecioVenta testPrecioVenta = precioVentaList.get(precioVentaList.size() - 1);
        assertThat(testPrecioVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPrecioVenta.getPpu()).isEqualByComparingTo(UPDATED_PPU);
        assertThat(testPrecioVenta.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPrecioVenta.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPrecioVenta.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPrecioVenta.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPrecioVenta.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePrecioVentaWithPatch() throws Exception {
        // Initialize the database
        precioVentaRepository.saveAndFlush(precioVenta);

        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();

        // Update the precioVenta using partial update
        PrecioVenta partialUpdatedPrecioVenta = new PrecioVenta();
        partialUpdatedPrecioVenta.setId(precioVenta.getId());

        partialUpdatedPrecioVenta
            .fecha(UPDATED_FECHA)
            .ppu(UPDATED_PPU)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPrecioVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrecioVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrecioVenta))
            )
            .andExpect(status().isOk());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
        PrecioVenta testPrecioVenta = precioVentaList.get(precioVentaList.size() - 1);
        assertThat(testPrecioVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPrecioVenta.getPpu()).isEqualByComparingTo(UPDATED_PPU);
        assertThat(testPrecioVenta.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPrecioVenta.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPrecioVenta.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPrecioVenta.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPrecioVenta.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPrecioVenta() throws Exception {
        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();
        precioVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, precioVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precioVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrecioVenta() throws Exception {
        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();
        precioVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(precioVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrecioVenta() throws Exception {
        int databaseSizeBeforeUpdate = precioVentaRepository.findAll().size();
        precioVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrecioVentaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(precioVenta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrecioVenta in the database
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrecioVenta() throws Exception {
        // Initialize the database
        precioVentaRepository.saveAndFlush(precioVenta);

        int databaseSizeBeforeDelete = precioVentaRepository.findAll().size();

        // Delete the precioVenta
        restPrecioVentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, precioVenta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrecioVenta> precioVentaList = precioVentaRepository.findAll();
        assertThat(precioVentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
