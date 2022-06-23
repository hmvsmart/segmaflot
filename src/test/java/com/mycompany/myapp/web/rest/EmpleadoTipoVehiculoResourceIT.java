package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EmpleadoTipoVehiculo;
import com.mycompany.myapp.repository.EmpleadoTipoVehiculoRepository;
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
 * Integration tests for the {@link EmpleadoTipoVehiculoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpleadoTipoVehiculoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/empleado-tipo-vehiculos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpleadoTipoVehiculoRepository empleadoTipoVehiculoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpleadoTipoVehiculoMockMvc;

    private EmpleadoTipoVehiculo empleadoTipoVehiculo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpleadoTipoVehiculo createEntity(EntityManager em) {
        EmpleadoTipoVehiculo empleadoTipoVehiculo = new EmpleadoTipoVehiculo()
            .fecha(DEFAULT_FECHA)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return empleadoTipoVehiculo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpleadoTipoVehiculo createUpdatedEntity(EntityManager em) {
        EmpleadoTipoVehiculo empleadoTipoVehiculo = new EmpleadoTipoVehiculo()
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return empleadoTipoVehiculo;
    }

    @BeforeEach
    public void initTest() {
        empleadoTipoVehiculo = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeCreate = empleadoTipoVehiculoRepository.findAll().size();
        // Create the EmpleadoTipoVehiculo
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isCreated());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeCreate + 1);
        EmpleadoTipoVehiculo testEmpleadoTipoVehiculo = empleadoTipoVehiculoList.get(empleadoTipoVehiculoList.size() - 1);
        assertThat(testEmpleadoTipoVehiculo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEmpleadoTipoVehiculo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmpleadoTipoVehiculo.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmpleadoTipoVehiculo.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testEmpleadoTipoVehiculo.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createEmpleadoTipoVehiculoWithExistingId() throws Exception {
        // Create the EmpleadoTipoVehiculo with an existing ID
        empleadoTipoVehiculo.setId(1L);

        int databaseSizeBeforeCreate = empleadoTipoVehiculoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpleadoTipoVehiculos() throws Exception {
        // Initialize the database
        empleadoTipoVehiculoRepository.saveAndFlush(empleadoTipoVehiculo);

        // Get all the empleadoTipoVehiculoList
        restEmpleadoTipoVehiculoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleadoTipoVehiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getEmpleadoTipoVehiculo() throws Exception {
        // Initialize the database
        empleadoTipoVehiculoRepository.saveAndFlush(empleadoTipoVehiculo);

        // Get the empleadoTipoVehiculo
        restEmpleadoTipoVehiculoMockMvc
            .perform(get(ENTITY_API_URL_ID, empleadoTipoVehiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empleadoTipoVehiculo.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingEmpleadoTipoVehiculo() throws Exception {
        // Get the empleadoTipoVehiculo
        restEmpleadoTipoVehiculoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpleadoTipoVehiculo() throws Exception {
        // Initialize the database
        empleadoTipoVehiculoRepository.saveAndFlush(empleadoTipoVehiculo);

        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();

        // Update the empleadoTipoVehiculo
        EmpleadoTipoVehiculo updatedEmpleadoTipoVehiculo = empleadoTipoVehiculoRepository.findById(empleadoTipoVehiculo.getId()).get();
        // Disconnect from session so that the updates on updatedEmpleadoTipoVehiculo are not directly saved in db
        em.detach(updatedEmpleadoTipoVehiculo);
        updatedEmpleadoTipoVehiculo
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEmpleadoTipoVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpleadoTipoVehiculo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmpleadoTipoVehiculo))
            )
            .andExpect(status().isOk());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
        EmpleadoTipoVehiculo testEmpleadoTipoVehiculo = empleadoTipoVehiculoList.get(empleadoTipoVehiculoList.size() - 1);
        assertThat(testEmpleadoTipoVehiculo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEmpleadoTipoVehiculo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmpleadoTipoVehiculo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpleadoTipoVehiculo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEmpleadoTipoVehiculo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();
        empleadoTipoVehiculo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empleadoTipoVehiculo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();
        empleadoTipoVehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();
        empleadoTipoVehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpleadoTipoVehiculoWithPatch() throws Exception {
        // Initialize the database
        empleadoTipoVehiculoRepository.saveAndFlush(empleadoTipoVehiculo);

        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();

        // Update the empleadoTipoVehiculo using partial update
        EmpleadoTipoVehiculo partialUpdatedEmpleadoTipoVehiculo = new EmpleadoTipoVehiculo();
        partialUpdatedEmpleadoTipoVehiculo.setId(empleadoTipoVehiculo.getId());

        partialUpdatedEmpleadoTipoVehiculo
            .status(UPDATED_STATUS)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restEmpleadoTipoVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleadoTipoVehiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpleadoTipoVehiculo))
            )
            .andExpect(status().isOk());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
        EmpleadoTipoVehiculo testEmpleadoTipoVehiculo = empleadoTipoVehiculoList.get(empleadoTipoVehiculoList.size() - 1);
        assertThat(testEmpleadoTipoVehiculo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEmpleadoTipoVehiculo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmpleadoTipoVehiculo.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpleadoTipoVehiculo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEmpleadoTipoVehiculo.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateEmpleadoTipoVehiculoWithPatch() throws Exception {
        // Initialize the database
        empleadoTipoVehiculoRepository.saveAndFlush(empleadoTipoVehiculo);

        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();

        // Update the empleadoTipoVehiculo using partial update
        EmpleadoTipoVehiculo partialUpdatedEmpleadoTipoVehiculo = new EmpleadoTipoVehiculo();
        partialUpdatedEmpleadoTipoVehiculo.setId(empleadoTipoVehiculo.getId());

        partialUpdatedEmpleadoTipoVehiculo
            .fecha(UPDATED_FECHA)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEmpleadoTipoVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleadoTipoVehiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpleadoTipoVehiculo))
            )
            .andExpect(status().isOk());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
        EmpleadoTipoVehiculo testEmpleadoTipoVehiculo = empleadoTipoVehiculoList.get(empleadoTipoVehiculoList.size() - 1);
        assertThat(testEmpleadoTipoVehiculo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEmpleadoTipoVehiculo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmpleadoTipoVehiculo.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpleadoTipoVehiculo.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEmpleadoTipoVehiculo.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEmpleadoTipoVehiculo.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();
        empleadoTipoVehiculo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empleadoTipoVehiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();
        empleadoTipoVehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpleadoTipoVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = empleadoTipoVehiculoRepository.findAll().size();
        empleadoTipoVehiculo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoTipoVehiculoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleadoTipoVehiculo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpleadoTipoVehiculo in the database
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpleadoTipoVehiculo() throws Exception {
        // Initialize the database
        empleadoTipoVehiculoRepository.saveAndFlush(empleadoTipoVehiculo);

        int databaseSizeBeforeDelete = empleadoTipoVehiculoRepository.findAll().size();

        // Delete the empleadoTipoVehiculo
        restEmpleadoTipoVehiculoMockMvc
            .perform(delete(ENTITY_API_URL_ID, empleadoTipoVehiculo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmpleadoTipoVehiculo> empleadoTipoVehiculoList = empleadoTipoVehiculoRepository.findAll();
        assertThat(empleadoTipoVehiculoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
