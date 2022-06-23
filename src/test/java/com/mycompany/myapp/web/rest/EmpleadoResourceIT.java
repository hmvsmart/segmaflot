package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.repository.EmpleadoRepository;
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
 * Integration tests for the {@link EmpleadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpleadoResourceIT {

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final String DEFAULT_NSS = "AAAAAAAAAA";
    private static final String UPDATED_NSS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FINICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINICIO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PUESTO = "AAAAAAAAAA";
    private static final String UPDATED_PUESTO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARIO = new BigDecimal(2);

    private static final String DEFAULT_DIA_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_DIA_PAGO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PAGO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/empleados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpleadoMockMvc;

    private Empleado empleado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleado createEntity(EntityManager em) {
        Empleado empleado = new Empleado()
            .rfc(DEFAULT_RFC)
            .nss(DEFAULT_NSS)
            .finicio(DEFAULT_FINICIO)
            .puesto(DEFAULT_PUESTO)
            .salario(DEFAULT_SALARIO)
            .diaPago(DEFAULT_DIA_PAGO)
            .tipoPago(DEFAULT_TIPO_PAGO)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return empleado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleado createUpdatedEntity(EntityManager em) {
        Empleado empleado = new Empleado()
            .rfc(UPDATED_RFC)
            .nss(UPDATED_NSS)
            .finicio(UPDATED_FINICIO)
            .puesto(UPDATED_PUESTO)
            .salario(UPDATED_SALARIO)
            .diaPago(UPDATED_DIA_PAGO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return empleado;
    }

    @BeforeEach
    public void initTest() {
        empleado = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpleado() throws Exception {
        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();
        // Create the Empleado
        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isCreated());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate + 1);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testEmpleado.getNss()).isEqualTo(DEFAULT_NSS);
        assertThat(testEmpleado.getFinicio()).isEqualTo(DEFAULT_FINICIO);
        assertThat(testEmpleado.getPuesto()).isEqualTo(DEFAULT_PUESTO);
        assertThat(testEmpleado.getSalario()).isEqualByComparingTo(DEFAULT_SALARIO);
        assertThat(testEmpleado.getDiaPago()).isEqualTo(DEFAULT_DIA_PAGO);
        assertThat(testEmpleado.getTipoPago()).isEqualTo(DEFAULT_TIPO_PAGO);
        assertThat(testEmpleado.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testEmpleado.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmpleado.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testEmpleado.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testEmpleado.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createEmpleadoWithExistingId() throws Exception {
        // Create the Empleado with an existing ID
        empleado.setId(1L);

        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpleados() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get all the empleadoList
        restEmpleadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].nss").value(hasItem(DEFAULT_NSS)))
            .andExpect(jsonPath("$.[*].finicio").value(hasItem(DEFAULT_FINICIO.toString())))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO)))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(sameNumber(DEFAULT_SALARIO))))
            .andExpect(jsonPath("$.[*].diaPago").value(hasItem(DEFAULT_DIA_PAGO)))
            .andExpect(jsonPath("$.[*].tipoPago").value(hasItem(DEFAULT_TIPO_PAGO)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get the empleado
        restEmpleadoMockMvc
            .perform(get(ENTITY_API_URL_ID, empleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empleado.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC))
            .andExpect(jsonPath("$.nss").value(DEFAULT_NSS))
            .andExpect(jsonPath("$.finicio").value(DEFAULT_FINICIO.toString()))
            .andExpect(jsonPath("$.puesto").value(DEFAULT_PUESTO))
            .andExpect(jsonPath("$.salario").value(sameNumber(DEFAULT_SALARIO)))
            .andExpect(jsonPath("$.diaPago").value(DEFAULT_DIA_PAGO))
            .andExpect(jsonPath("$.tipoPago").value(DEFAULT_TIPO_PAGO))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingEmpleado() throws Exception {
        // Get the empleado
        restEmpleadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado
        Empleado updatedEmpleado = empleadoRepository.findById(empleado.getId()).get();
        // Disconnect from session so that the updates on updatedEmpleado are not directly saved in db
        em.detach(updatedEmpleado);
        updatedEmpleado
            .rfc(UPDATED_RFC)
            .nss(UPDATED_NSS)
            .finicio(UPDATED_FINICIO)
            .puesto(UPDATED_PUESTO)
            .salario(UPDATED_SALARIO)
            .diaPago(UPDATED_DIA_PAGO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpleado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testEmpleado.getNss()).isEqualTo(UPDATED_NSS);
        assertThat(testEmpleado.getFinicio()).isEqualTo(UPDATED_FINICIO);
        assertThat(testEmpleado.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testEmpleado.getSalario()).isEqualByComparingTo(UPDATED_SALARIO);
        assertThat(testEmpleado.getDiaPago()).isEqualTo(UPDATED_DIA_PAGO);
        assertThat(testEmpleado.getTipoPago()).isEqualTo(UPDATED_TIPO_PAGO);
        assertThat(testEmpleado.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEmpleado.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpleado.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEmpleado.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEmpleado.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empleado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpleadoWithPatch() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado using partial update
        Empleado partialUpdatedEmpleado = new Empleado();
        partialUpdatedEmpleado.setId(empleado.getId());

        partialUpdatedEmpleado
            .rfc(UPDATED_RFC)
            .nss(UPDATED_NSS)
            .puesto(UPDATED_PUESTO)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testEmpleado.getNss()).isEqualTo(UPDATED_NSS);
        assertThat(testEmpleado.getFinicio()).isEqualTo(DEFAULT_FINICIO);
        assertThat(testEmpleado.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testEmpleado.getSalario()).isEqualByComparingTo(DEFAULT_SALARIO);
        assertThat(testEmpleado.getDiaPago()).isEqualTo(DEFAULT_DIA_PAGO);
        assertThat(testEmpleado.getTipoPago()).isEqualTo(DEFAULT_TIPO_PAGO);
        assertThat(testEmpleado.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testEmpleado.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmpleado.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEmpleado.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEmpleado.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateEmpleadoWithPatch() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado using partial update
        Empleado partialUpdatedEmpleado = new Empleado();
        partialUpdatedEmpleado.setId(empleado.getId());

        partialUpdatedEmpleado
            .rfc(UPDATED_RFC)
            .nss(UPDATED_NSS)
            .finicio(UPDATED_FINICIO)
            .puesto(UPDATED_PUESTO)
            .salario(UPDATED_SALARIO)
            .diaPago(UPDATED_DIA_PAGO)
            .tipoPago(UPDATED_TIPO_PAGO)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testEmpleado.getNss()).isEqualTo(UPDATED_NSS);
        assertThat(testEmpleado.getFinicio()).isEqualTo(UPDATED_FINICIO);
        assertThat(testEmpleado.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testEmpleado.getSalario()).isEqualByComparingTo(UPDATED_SALARIO);
        assertThat(testEmpleado.getDiaPago()).isEqualTo(UPDATED_DIA_PAGO);
        assertThat(testEmpleado.getTipoPago()).isEqualTo(UPDATED_TIPO_PAGO);
        assertThat(testEmpleado.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testEmpleado.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmpleado.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testEmpleado.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testEmpleado.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();
        empleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeDelete = empleadoRepository.findAll().size();

        // Delete the empleado
        restEmpleadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, empleado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
