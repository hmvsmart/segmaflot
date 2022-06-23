package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Vehiculo;
import com.mycompany.myapp.repository.VehiculoRepository;
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
 * Integration tests for the {@link VehiculoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehiculoResourceIT {

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_SUBMARCA = "AAAAAAAAAA";
    private static final String UPDATED_SUBMARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final String DEFAULT_GENERACION = "AAAAAAAAAA";
    private static final String UPDATED_GENERACION = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_VEHICULO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_VEHICULO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/vehiculos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehiculoMockMvc;

    private Vehiculo vehiculo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehiculo createEntity(EntityManager em) {
        Vehiculo vehiculo = new Vehiculo()
            .marca(DEFAULT_MARCA)
            .submarca(DEFAULT_SUBMARCA)
            .modelo(DEFAULT_MODELO)
            .generacion(DEFAULT_GENERACION)
            .tipoVehiculo(DEFAULT_TIPO_VEHICULO)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return vehiculo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehiculo createUpdatedEntity(EntityManager em) {
        Vehiculo vehiculo = new Vehiculo()
            .marca(UPDATED_MARCA)
            .submarca(UPDATED_SUBMARCA)
            .modelo(UPDATED_MODELO)
            .generacion(UPDATED_GENERACION)
            .tipoVehiculo(UPDATED_TIPO_VEHICULO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return vehiculo;
    }

    @BeforeEach
    public void initTest() {
        vehiculo = createEntity(em);
    }

    @Test
    @Transactional
    void createVehiculo() throws Exception {
        int databaseSizeBeforeCreate = vehiculoRepository.findAll().size();
        // Create the Vehiculo
        restVehiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isCreated());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeCreate + 1);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testVehiculo.getSubmarca()).isEqualTo(DEFAULT_SUBMARCA);
        assertThat(testVehiculo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVehiculo.getGeneracion()).isEqualTo(DEFAULT_GENERACION);
        assertThat(testVehiculo.getTipoVehiculo()).isEqualTo(DEFAULT_TIPO_VEHICULO);
        assertThat(testVehiculo.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testVehiculo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testVehiculo.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testVehiculo.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testVehiculo.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createVehiculoWithExistingId() throws Exception {
        // Create the Vehiculo with an existing ID
        vehiculo.setId(1L);

        int databaseSizeBeforeCreate = vehiculoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVehiculos() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        // Get all the vehiculoList
        restVehiculoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].submarca").value(hasItem(DEFAULT_SUBMARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].generacion").value(hasItem(DEFAULT_GENERACION)))
            .andExpect(jsonPath("$.[*].tipoVehiculo").value(hasItem(DEFAULT_TIPO_VEHICULO)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        // Get the vehiculo
        restVehiculoMockMvc
            .perform(get(ENTITY_API_URL_ID, vehiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculo.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.submarca").value(DEFAULT_SUBMARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.generacion").value(DEFAULT_GENERACION))
            .andExpect(jsonPath("$.tipoVehiculo").value(DEFAULT_TIPO_VEHICULO))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingVehiculo() throws Exception {
        // Get the vehiculo
        restVehiculoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Update the vehiculo
        Vehiculo updatedVehiculo = vehiculoRepository.findById(vehiculo.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculo are not directly saved in db
        em.detach(updatedVehiculo);
        updatedVehiculo
            .marca(UPDATED_MARCA)
            .submarca(UPDATED_SUBMARCA)
            .modelo(UPDATED_MODELO)
            .generacion(UPDATED_GENERACION)
            .tipoVehiculo(UPDATED_TIPO_VEHICULO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVehiculo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVehiculo))
            )
            .andExpect(status().isOk());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVehiculo.getSubmarca()).isEqualTo(UPDATED_SUBMARCA);
        assertThat(testVehiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVehiculo.getGeneracion()).isEqualTo(UPDATED_GENERACION);
        assertThat(testVehiculo.getTipoVehiculo()).isEqualTo(UPDATED_TIPO_VEHICULO);
        assertThat(testVehiculo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testVehiculo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVehiculo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testVehiculo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testVehiculo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();
        vehiculo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehiculo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();
        vehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();
        vehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehiculoWithPatch() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Update the vehiculo using partial update
        Vehiculo partialUpdatedVehiculo = new Vehiculo();
        partialUpdatedVehiculo.setId(vehiculo.getId());

        partialUpdatedVehiculo
            .marca(UPDATED_MARCA)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehiculo))
            )
            .andExpect(status().isOk());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVehiculo.getSubmarca()).isEqualTo(DEFAULT_SUBMARCA);
        assertThat(testVehiculo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVehiculo.getGeneracion()).isEqualTo(DEFAULT_GENERACION);
        assertThat(testVehiculo.getTipoVehiculo()).isEqualTo(DEFAULT_TIPO_VEHICULO);
        assertThat(testVehiculo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testVehiculo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVehiculo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testVehiculo.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testVehiculo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateVehiculoWithPatch() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Update the vehiculo using partial update
        Vehiculo partialUpdatedVehiculo = new Vehiculo();
        partialUpdatedVehiculo.setId(vehiculo.getId());

        partialUpdatedVehiculo
            .marca(UPDATED_MARCA)
            .submarca(UPDATED_SUBMARCA)
            .modelo(UPDATED_MODELO)
            .generacion(UPDATED_GENERACION)
            .tipoVehiculo(UPDATED_TIPO_VEHICULO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehiculo))
            )
            .andExpect(status().isOk());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVehiculo.getSubmarca()).isEqualTo(UPDATED_SUBMARCA);
        assertThat(testVehiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVehiculo.getGeneracion()).isEqualTo(UPDATED_GENERACION);
        assertThat(testVehiculo.getTipoVehiculo()).isEqualTo(UPDATED_TIPO_VEHICULO);
        assertThat(testVehiculo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testVehiculo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVehiculo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testVehiculo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testVehiculo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();
        vehiculo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();
        vehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();
        vehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehiculoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vehiculo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        int databaseSizeBeforeDelete = vehiculoRepository.findAll().size();

        // Delete the vehiculo
        restVehiculoMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehiculo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
