package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PersonaFisica;
import com.mycompany.myapp.repository.PersonaFisicaRepository;
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
 * Integration tests for the {@link PersonaFisicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonaFisicaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_AMATERNO = "AAAAAAAAAA";
    private static final String UPDATED_AMATERNO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/persona-fisicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonaFisicaRepository personaFisicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaFisicaMockMvc;

    private PersonaFisica personaFisica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaFisica createEntity(EntityManager em) {
        PersonaFisica personaFisica = new PersonaFisica()
            .nombre(DEFAULT_NOMBRE)
            .apaterno(DEFAULT_APATERNO)
            .amaterno(DEFAULT_AMATERNO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .curp(DEFAULT_CURP)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return personaFisica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaFisica createUpdatedEntity(EntityManager em) {
        PersonaFisica personaFisica = new PersonaFisica()
            .nombre(UPDATED_NOMBRE)
            .apaterno(UPDATED_APATERNO)
            .amaterno(UPDATED_AMATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .curp(UPDATED_CURP)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return personaFisica;
    }

    @BeforeEach
    public void initTest() {
        personaFisica = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonaFisica() throws Exception {
        int databaseSizeBeforeCreate = personaFisicaRepository.findAll().size();
        // Create the PersonaFisica
        restPersonaFisicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personaFisica)))
            .andExpect(status().isCreated());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeCreate + 1);
        PersonaFisica testPersonaFisica = personaFisicaList.get(personaFisicaList.size() - 1);
        assertThat(testPersonaFisica.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonaFisica.getApaterno()).isEqualTo(DEFAULT_APATERNO);
        assertThat(testPersonaFisica.getAmaterno()).isEqualTo(DEFAULT_AMATERNO);
        assertThat(testPersonaFisica.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPersonaFisica.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testPersonaFisica.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPersonaFisica.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPersonaFisica.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPersonaFisica.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPersonaFisica.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createPersonaFisicaWithExistingId() throws Exception {
        // Create the PersonaFisica with an existing ID
        personaFisica.setId(1L);

        int databaseSizeBeforeCreate = personaFisicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaFisicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personaFisica)))
            .andExpect(status().isBadRequest());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonaFisicas() throws Exception {
        // Initialize the database
        personaFisicaRepository.saveAndFlush(personaFisica);

        // Get all the personaFisicaList
        restPersonaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personaFisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apaterno").value(hasItem(DEFAULT_APATERNO)))
            .andExpect(jsonPath("$.[*].amaterno").value(hasItem(DEFAULT_AMATERNO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getPersonaFisica() throws Exception {
        // Initialize the database
        personaFisicaRepository.saveAndFlush(personaFisica);

        // Get the personaFisica
        restPersonaFisicaMockMvc
            .perform(get(ENTITY_API_URL_ID, personaFisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personaFisica.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apaterno").value(DEFAULT_APATERNO))
            .andExpect(jsonPath("$.amaterno").value(DEFAULT_AMATERNO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPersonaFisica() throws Exception {
        // Get the personaFisica
        restPersonaFisicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonaFisica() throws Exception {
        // Initialize the database
        personaFisicaRepository.saveAndFlush(personaFisica);

        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();

        // Update the personaFisica
        PersonaFisica updatedPersonaFisica = personaFisicaRepository.findById(personaFisica.getId()).get();
        // Disconnect from session so that the updates on updatedPersonaFisica are not directly saved in db
        em.detach(updatedPersonaFisica);
        updatedPersonaFisica
            .nombre(UPDATED_NOMBRE)
            .apaterno(UPDATED_APATERNO)
            .amaterno(UPDATED_AMATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .curp(UPDATED_CURP)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPersonaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonaFisica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
        PersonaFisica testPersonaFisica = personaFisicaList.get(personaFisicaList.size() - 1);
        assertThat(testPersonaFisica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonaFisica.getApaterno()).isEqualTo(UPDATED_APATERNO);
        assertThat(testPersonaFisica.getAmaterno()).isEqualTo(UPDATED_AMATERNO);
        assertThat(testPersonaFisica.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPersonaFisica.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testPersonaFisica.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPersonaFisica.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPersonaFisica.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPersonaFisica.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPersonaFisica.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPersonaFisica() throws Exception {
        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();
        personaFisica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personaFisica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonaFisica() throws Exception {
        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();
        personaFisica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonaFisica() throws Exception {
        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();
        personaFisica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaFisicaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personaFisica)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonaFisicaWithPatch() throws Exception {
        // Initialize the database
        personaFisicaRepository.saveAndFlush(personaFisica);

        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();

        // Update the personaFisica using partial update
        PersonaFisica partialUpdatedPersonaFisica = new PersonaFisica();
        partialUpdatedPersonaFisica.setId(personaFisica.getId());

        partialUpdatedPersonaFisica
            .nombre(UPDATED_NOMBRE)
            .apaterno(UPDATED_APATERNO)
            .amaterno(UPDATED_AMATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON);

        restPersonaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
        PersonaFisica testPersonaFisica = personaFisicaList.get(personaFisicaList.size() - 1);
        assertThat(testPersonaFisica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonaFisica.getApaterno()).isEqualTo(UPDATED_APATERNO);
        assertThat(testPersonaFisica.getAmaterno()).isEqualTo(UPDATED_AMATERNO);
        assertThat(testPersonaFisica.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPersonaFisica.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testPersonaFisica.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPersonaFisica.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPersonaFisica.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPersonaFisica.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPersonaFisica.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePersonaFisicaWithPatch() throws Exception {
        // Initialize the database
        personaFisicaRepository.saveAndFlush(personaFisica);

        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();

        // Update the personaFisica using partial update
        PersonaFisica partialUpdatedPersonaFisica = new PersonaFisica();
        partialUpdatedPersonaFisica.setId(personaFisica.getId());

        partialUpdatedPersonaFisica
            .nombre(UPDATED_NOMBRE)
            .apaterno(UPDATED_APATERNO)
            .amaterno(UPDATED_AMATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .curp(UPDATED_CURP)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPersonaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
        PersonaFisica testPersonaFisica = personaFisicaList.get(personaFisicaList.size() - 1);
        assertThat(testPersonaFisica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonaFisica.getApaterno()).isEqualTo(UPDATED_APATERNO);
        assertThat(testPersonaFisica.getAmaterno()).isEqualTo(UPDATED_AMATERNO);
        assertThat(testPersonaFisica.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPersonaFisica.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testPersonaFisica.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPersonaFisica.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPersonaFisica.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPersonaFisica.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPersonaFisica.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPersonaFisica() throws Exception {
        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();
        personaFisica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonaFisica() throws Exception {
        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();
        personaFisica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonaFisica() throws Exception {
        int databaseSizeBeforeUpdate = personaFisicaRepository.findAll().size();
        personaFisica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personaFisica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaFisica in the database
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonaFisica() throws Exception {
        // Initialize the database
        personaFisicaRepository.saveAndFlush(personaFisica);

        int databaseSizeBeforeDelete = personaFisicaRepository.findAll().size();

        // Delete the personaFisica
        restPersonaFisicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, personaFisica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonaFisica> personaFisicaList = personaFisicaRepository.findAll();
        assertThat(personaFisicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
