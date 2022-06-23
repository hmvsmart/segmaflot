package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.VehiculoCliente;
import com.mycompany.myapp.repository.VehiculoClienteRepository;
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
 * Integration tests for the {@link VehiculoClienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehiculoClienteResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SERIE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/vehiculo-clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehiculoClienteRepository vehiculoClienteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculoClienteMockMvc;

    private VehiculoCliente vehiculoCliente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculoCliente createEntity(EntityManager em) {
        VehiculoCliente vehiculoCliente = new VehiculoCliente()
            .fecha(DEFAULT_FECHA)
            .numeroSerie(DEFAULT_NUMERO_SERIE)
            .color(DEFAULT_COLOR)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return vehiculoCliente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehiculoCliente createUpdatedEntity(EntityManager em) {
        VehiculoCliente vehiculoCliente = new VehiculoCliente()
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .color(UPDATED_COLOR)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return vehiculoCliente;
    }

    @BeforeEach
    public void initTest() {
        vehiculoCliente = createEntity(em);
    }

    @Test
    @Transactional
    void createVehiculoCliente() throws Exception {
        int databaseSizeBeforeCreate = vehiculoClienteRepository.findAll().size();
        // Create the VehiculoCliente
        restVehiculoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isCreated());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeCreate + 1);
        VehiculoCliente testVehiculoCliente = vehiculoClienteList.get(vehiculoClienteList.size() - 1);
        assertThat(testVehiculoCliente.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testVehiculoCliente.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testVehiculoCliente.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVehiculoCliente.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVehiculoCliente.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testVehiculoCliente.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testVehiculoCliente.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testVehiculoCliente.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testVehiculoCliente.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createVehiculoClienteWithExistingId() throws Exception {
        // Create the VehiculoCliente with an existing ID
        vehiculoCliente.setId(1L);

        int databaseSizeBeforeCreate = vehiculoClienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculoClienteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVehiculoClientes() throws Exception {
        // Initialize the database
        vehiculoClienteRepository.saveAndFlush(vehiculoCliente);

        // Get all the vehiculoClienteList
        restVehiculoClienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculoCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].numeroSerie").value(hasItem(DEFAULT_NUMERO_SERIE)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getVehiculoCliente() throws Exception {
        // Initialize the database
        vehiculoClienteRepository.saveAndFlush(vehiculoCliente);

        // Get the vehiculoCliente
        restVehiculoClienteMockMvc
            .perform(get(ENTITY_API_URL_ID, vehiculoCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculoCliente.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.numeroSerie").value(DEFAULT_NUMERO_SERIE))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingVehiculoCliente() throws Exception {
        // Get the vehiculoCliente
        restVehiculoClienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehiculoCliente() throws Exception {
        // Initialize the database
        vehiculoClienteRepository.saveAndFlush(vehiculoCliente);

        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();

        // Update the vehiculoCliente
        VehiculoCliente updatedVehiculoCliente = vehiculoClienteRepository.findById(vehiculoCliente.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculoCliente are not directly saved in db
        em.detach(updatedVehiculoCliente);
        updatedVehiculoCliente
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .color(UPDATED_COLOR)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restVehiculoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVehiculoCliente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVehiculoCliente))
            )
            .andExpect(status().isOk());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
        VehiculoCliente testVehiculoCliente = vehiculoClienteList.get(vehiculoClienteList.size() - 1);
        assertThat(testVehiculoCliente.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testVehiculoCliente.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testVehiculoCliente.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehiculoCliente.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVehiculoCliente.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testVehiculoCliente.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVehiculoCliente.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testVehiculoCliente.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testVehiculoCliente.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingVehiculoCliente() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();
        vehiculoCliente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehiculoCliente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehiculoCliente() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();
        vehiculoCliente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoClienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehiculoCliente() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();
        vehiculoCliente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoClienteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehiculoClienteWithPatch() throws Exception {
        // Initialize the database
        vehiculoClienteRepository.saveAndFlush(vehiculoCliente);

        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();

        // Update the vehiculoCliente using partial update
        VehiculoCliente partialUpdatedVehiculoCliente = new VehiculoCliente();
        partialUpdatedVehiculoCliente.setId(vehiculoCliente.getId());

        partialUpdatedVehiculoCliente.createByUser(UPDATED_CREATE_BY_USER).createdOn(UPDATED_CREATED_ON).auditStatus(UPDATED_AUDIT_STATUS);

        restVehiculoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehiculoCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehiculoCliente))
            )
            .andExpect(status().isOk());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
        VehiculoCliente testVehiculoCliente = vehiculoClienteList.get(vehiculoClienteList.size() - 1);
        assertThat(testVehiculoCliente.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testVehiculoCliente.getNumeroSerie()).isEqualTo(DEFAULT_NUMERO_SERIE);
        assertThat(testVehiculoCliente.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVehiculoCliente.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVehiculoCliente.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testVehiculoCliente.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVehiculoCliente.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testVehiculoCliente.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testVehiculoCliente.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateVehiculoClienteWithPatch() throws Exception {
        // Initialize the database
        vehiculoClienteRepository.saveAndFlush(vehiculoCliente);

        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();

        // Update the vehiculoCliente using partial update
        VehiculoCliente partialUpdatedVehiculoCliente = new VehiculoCliente();
        partialUpdatedVehiculoCliente.setId(vehiculoCliente.getId());

        partialUpdatedVehiculoCliente
            .fecha(UPDATED_FECHA)
            .numeroSerie(UPDATED_NUMERO_SERIE)
            .color(UPDATED_COLOR)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restVehiculoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehiculoCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehiculoCliente))
            )
            .andExpect(status().isOk());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
        VehiculoCliente testVehiculoCliente = vehiculoClienteList.get(vehiculoClienteList.size() - 1);
        assertThat(testVehiculoCliente.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testVehiculoCliente.getNumeroSerie()).isEqualTo(UPDATED_NUMERO_SERIE);
        assertThat(testVehiculoCliente.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehiculoCliente.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVehiculoCliente.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testVehiculoCliente.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVehiculoCliente.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testVehiculoCliente.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testVehiculoCliente.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingVehiculoCliente() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();
        vehiculoCliente.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehiculoCliente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehiculoCliente() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();
        vehiculoCliente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehiculoCliente() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoClienteRepository.findAll().size();
        vehiculoCliente.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoClienteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehiculoCliente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehiculoCliente in the database
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehiculoCliente() throws Exception {
        // Initialize the database
        vehiculoClienteRepository.saveAndFlush(vehiculoCliente);

        int databaseSizeBeforeDelete = vehiculoClienteRepository.findAll().size();

        // Delete the vehiculoCliente
        restVehiculoClienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehiculoCliente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehiculoCliente> vehiculoClienteList = vehiculoClienteRepository.findAll();
        assertThat(vehiculoClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
