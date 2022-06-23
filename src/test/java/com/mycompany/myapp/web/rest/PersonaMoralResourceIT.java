package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PersonaMoral;
import com.mycompany.myapp.repository.PersonaMoralRepository;
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
 * Integration tests for the {@link PersonaMoralResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonaMoralResourceIT {

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/persona-morals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonaMoralRepository personaMoralRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaMoralMockMvc;

    private PersonaMoral personaMoral;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaMoral createEntity(EntityManager em) {
        PersonaMoral personaMoral = new PersonaMoral()
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .rfc(DEFAULT_RFC)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return personaMoral;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonaMoral createUpdatedEntity(EntityManager em) {
        PersonaMoral personaMoral = new PersonaMoral()
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .rfc(UPDATED_RFC)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return personaMoral;
    }

    @BeforeEach
    public void initTest() {
        personaMoral = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonaMoral() throws Exception {
        int databaseSizeBeforeCreate = personaMoralRepository.findAll().size();
        // Create the PersonaMoral
        restPersonaMoralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personaMoral)))
            .andExpect(status().isCreated());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeCreate + 1);
        PersonaMoral testPersonaMoral = personaMoralList.get(personaMoralList.size() - 1);
        assertThat(testPersonaMoral.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testPersonaMoral.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testPersonaMoral.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPersonaMoral.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPersonaMoral.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testPersonaMoral.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testPersonaMoral.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createPersonaMoralWithExistingId() throws Exception {
        // Create the PersonaMoral with an existing ID
        personaMoral.setId(1L);

        int databaseSizeBeforeCreate = personaMoralRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMoralMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personaMoral)))
            .andExpect(status().isBadRequest());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonaMorals() throws Exception {
        // Initialize the database
        personaMoralRepository.saveAndFlush(personaMoral);

        // Get all the personaMoralList
        restPersonaMoralMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personaMoral.getId().intValue())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL)))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getPersonaMoral() throws Exception {
        // Initialize the database
        personaMoralRepository.saveAndFlush(personaMoral);

        // Get the personaMoral
        restPersonaMoralMockMvc
            .perform(get(ENTITY_API_URL_ID, personaMoral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personaMoral.getId().intValue()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPersonaMoral() throws Exception {
        // Get the personaMoral
        restPersonaMoralMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonaMoral() throws Exception {
        // Initialize the database
        personaMoralRepository.saveAndFlush(personaMoral);

        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();

        // Update the personaMoral
        PersonaMoral updatedPersonaMoral = personaMoralRepository.findById(personaMoral.getId()).get();
        // Disconnect from session so that the updates on updatedPersonaMoral are not directly saved in db
        em.detach(updatedPersonaMoral);
        updatedPersonaMoral
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .rfc(UPDATED_RFC)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPersonaMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonaMoral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonaMoral))
            )
            .andExpect(status().isOk());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
        PersonaMoral testPersonaMoral = personaMoralList.get(personaMoralList.size() - 1);
        assertThat(testPersonaMoral.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testPersonaMoral.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPersonaMoral.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPersonaMoral.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPersonaMoral.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPersonaMoral.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPersonaMoral.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPersonaMoral() throws Exception {
        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();
        personaMoral.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personaMoral.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonaMoral() throws Exception {
        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();
        personaMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMoralMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personaMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonaMoral() throws Exception {
        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();
        personaMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMoralMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personaMoral)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonaMoralWithPatch() throws Exception {
        // Initialize the database
        personaMoralRepository.saveAndFlush(personaMoral);

        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();

        // Update the personaMoral using partial update
        PersonaMoral partialUpdatedPersonaMoral = new PersonaMoral();
        partialUpdatedPersonaMoral.setId(personaMoral.getId());

        partialUpdatedPersonaMoral
            .rfc(UPDATED_RFC)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPersonaMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonaMoral))
            )
            .andExpect(status().isOk());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
        PersonaMoral testPersonaMoral = personaMoralList.get(personaMoralList.size() - 1);
        assertThat(testPersonaMoral.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testPersonaMoral.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPersonaMoral.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testPersonaMoral.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPersonaMoral.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPersonaMoral.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPersonaMoral.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePersonaMoralWithPatch() throws Exception {
        // Initialize the database
        personaMoralRepository.saveAndFlush(personaMoral);

        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();

        // Update the personaMoral using partial update
        PersonaMoral partialUpdatedPersonaMoral = new PersonaMoral();
        partialUpdatedPersonaMoral.setId(personaMoral.getId());

        partialUpdatedPersonaMoral
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .rfc(UPDATED_RFC)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restPersonaMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonaMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonaMoral))
            )
            .andExpect(status().isOk());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
        PersonaMoral testPersonaMoral = personaMoralList.get(personaMoralList.size() - 1);
        assertThat(testPersonaMoral.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testPersonaMoral.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPersonaMoral.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testPersonaMoral.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPersonaMoral.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testPersonaMoral.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testPersonaMoral.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPersonaMoral() throws Exception {
        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();
        personaMoral.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personaMoral.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonaMoral() throws Exception {
        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();
        personaMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMoralMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personaMoral))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonaMoral() throws Exception {
        int databaseSizeBeforeUpdate = personaMoralRepository.findAll().size();
        personaMoral.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonaMoralMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personaMoral))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonaMoral in the database
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonaMoral() throws Exception {
        // Initialize the database
        personaMoralRepository.saveAndFlush(personaMoral);

        int databaseSizeBeforeDelete = personaMoralRepository.findAll().size();

        // Delete the personaMoral
        restPersonaMoralMockMvc
            .perform(delete(ENTITY_API_URL_ID, personaMoral.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonaMoral> personaMoralList = personaMoralRepository.findAll();
        assertThat(personaMoralList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
