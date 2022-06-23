package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Sucursal;
import com.mycompany.myapp.repository.SucursalRepository;
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
 * Integration tests for the {@link SucursalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SucursalResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SUCURSAL = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SUCURSAL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/sucursals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSucursalMockMvc;

    private Sucursal sucursal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sucursal createEntity(EntityManager em) {
        Sucursal sucursal = new Sucursal()
            .nombre(DEFAULT_NOMBRE)
            .tipoSucursal(DEFAULT_TIPO_SUCURSAL)
            .telefono(DEFAULT_TELEFONO)
            .status(DEFAULT_STATUS)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return sucursal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sucursal createUpdatedEntity(EntityManager em) {
        Sucursal sucursal = new Sucursal()
            .nombre(UPDATED_NOMBRE)
            .tipoSucursal(UPDATED_TIPO_SUCURSAL)
            .telefono(UPDATED_TELEFONO)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return sucursal;
    }

    @BeforeEach
    public void initTest() {
        sucursal = createEntity(em);
    }

    @Test
    @Transactional
    void createSucursal() throws Exception {
        int databaseSizeBeforeCreate = sucursalRepository.findAll().size();
        // Create the Sucursal
        restSucursalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sucursal)))
            .andExpect(status().isCreated());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeCreate + 1);
        Sucursal testSucursal = sucursalList.get(sucursalList.size() - 1);
        assertThat(testSucursal.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSucursal.getTipoSucursal()).isEqualTo(DEFAULT_TIPO_SUCURSAL);
        assertThat(testSucursal.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testSucursal.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSucursal.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testSucursal.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSucursal.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testSucursal.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testSucursal.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createSucursalWithExistingId() throws Exception {
        // Create the Sucursal with an existing ID
        sucursal.setId(1L);

        int databaseSizeBeforeCreate = sucursalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSucursalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sucursal)))
            .andExpect(status().isBadRequest());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSucursals() throws Exception {
        // Initialize the database
        sucursalRepository.saveAndFlush(sucursal);

        // Get all the sucursalList
        restSucursalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sucursal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tipoSucursal").value(hasItem(DEFAULT_TIPO_SUCURSAL)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getSucursal() throws Exception {
        // Initialize the database
        sucursalRepository.saveAndFlush(sucursal);

        // Get the sucursal
        restSucursalMockMvc
            .perform(get(ENTITY_API_URL_ID, sucursal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sucursal.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tipoSucursal").value(DEFAULT_TIPO_SUCURSAL))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingSucursal() throws Exception {
        // Get the sucursal
        restSucursalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSucursal() throws Exception {
        // Initialize the database
        sucursalRepository.saveAndFlush(sucursal);

        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();

        // Update the sucursal
        Sucursal updatedSucursal = sucursalRepository.findById(sucursal.getId()).get();
        // Disconnect from session so that the updates on updatedSucursal are not directly saved in db
        em.detach(updatedSucursal);
        updatedSucursal
            .nombre(UPDATED_NOMBRE)
            .tipoSucursal(UPDATED_TIPO_SUCURSAL)
            .telefono(UPDATED_TELEFONO)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restSucursalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSucursal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSucursal))
            )
            .andExpect(status().isOk());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
        Sucursal testSucursal = sucursalList.get(sucursalList.size() - 1);
        assertThat(testSucursal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSucursal.getTipoSucursal()).isEqualTo(UPDATED_TIPO_SUCURSAL);
        assertThat(testSucursal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testSucursal.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSucursal.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testSucursal.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSucursal.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testSucursal.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testSucursal.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingSucursal() throws Exception {
        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();
        sucursal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSucursalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sucursal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sucursal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSucursal() throws Exception {
        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();
        sucursal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSucursalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sucursal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSucursal() throws Exception {
        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();
        sucursal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSucursalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sucursal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSucursalWithPatch() throws Exception {
        // Initialize the database
        sucursalRepository.saveAndFlush(sucursal);

        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();

        // Update the sucursal using partial update
        Sucursal partialUpdatedSucursal = new Sucursal();
        partialUpdatedSucursal.setId(sucursal.getId());

        partialUpdatedSucursal
            .nombre(UPDATED_NOMBRE)
            .tipoSucursal(UPDATED_TIPO_SUCURSAL)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restSucursalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSucursal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSucursal))
            )
            .andExpect(status().isOk());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
        Sucursal testSucursal = sucursalList.get(sucursalList.size() - 1);
        assertThat(testSucursal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSucursal.getTipoSucursal()).isEqualTo(UPDATED_TIPO_SUCURSAL);
        assertThat(testSucursal.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testSucursal.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSucursal.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testSucursal.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSucursal.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testSucursal.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testSucursal.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateSucursalWithPatch() throws Exception {
        // Initialize the database
        sucursalRepository.saveAndFlush(sucursal);

        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();

        // Update the sucursal using partial update
        Sucursal partialUpdatedSucursal = new Sucursal();
        partialUpdatedSucursal.setId(sucursal.getId());

        partialUpdatedSucursal
            .nombre(UPDATED_NOMBRE)
            .tipoSucursal(UPDATED_TIPO_SUCURSAL)
            .telefono(UPDATED_TELEFONO)
            .status(UPDATED_STATUS)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restSucursalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSucursal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSucursal))
            )
            .andExpect(status().isOk());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
        Sucursal testSucursal = sucursalList.get(sucursalList.size() - 1);
        assertThat(testSucursal.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSucursal.getTipoSucursal()).isEqualTo(UPDATED_TIPO_SUCURSAL);
        assertThat(testSucursal.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testSucursal.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSucursal.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testSucursal.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSucursal.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testSucursal.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testSucursal.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingSucursal() throws Exception {
        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();
        sucursal.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSucursalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sucursal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sucursal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSucursal() throws Exception {
        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();
        sucursal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSucursalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sucursal))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSucursal() throws Exception {
        int databaseSizeBeforeUpdate = sucursalRepository.findAll().size();
        sucursal.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSucursalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sucursal)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sucursal in the database
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSucursal() throws Exception {
        // Initialize the database
        sucursalRepository.saveAndFlush(sucursal);

        int databaseSizeBeforeDelete = sucursalRepository.findAll().size();

        // Delete the sucursal
        restSucursalMockMvc
            .perform(delete(ENTITY_API_URL_ID, sucursal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sucursal> sucursalList = sucursalRepository.findAll();
        assertThat(sucursalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
