package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Contacto;
import com.mycompany.myapp.repository.ContactoRepository;
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
 * Integration tests for the {@link ContactoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactoResourceIT {

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    private static final String DEFAULT_ETIQUETA = "AAAAAAAAAA";
    private static final String UPDATED_ETIQUETA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STRING = false;
    private static final Boolean UPDATED_STRING = true;

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

    private static final String ENTITY_API_URL = "/api/contactos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactoMockMvc;

    private Contacto contacto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacto createEntity(EntityManager em) {
        Contacto contacto = new Contacto()
            .valor(DEFAULT_VALOR)
            .etiqueta(DEFAULT_ETIQUETA)
            .string(DEFAULT_STRING)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return contacto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacto createUpdatedEntity(EntityManager em) {
        Contacto contacto = new Contacto()
            .valor(UPDATED_VALOR)
            .etiqueta(UPDATED_ETIQUETA)
            .string(UPDATED_STRING)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return contacto;
    }

    @BeforeEach
    public void initTest() {
        contacto = createEntity(em);
    }

    @Test
    @Transactional
    void createContacto() throws Exception {
        int databaseSizeBeforeCreate = contactoRepository.findAll().size();
        // Create the Contacto
        restContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isCreated());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeCreate + 1);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testContacto.getEtiqueta()).isEqualTo(DEFAULT_ETIQUETA);
        assertThat(testContacto.getString()).isEqualTo(DEFAULT_STRING);
        assertThat(testContacto.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testContacto.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testContacto.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testContacto.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testContacto.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createContactoWithExistingId() throws Exception {
        // Create the Contacto with an existing ID
        contacto.setId(1L);

        int databaseSizeBeforeCreate = contactoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContactos() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get all the contactoList
        restContactoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].etiqueta").value(hasItem(DEFAULT_ETIQUETA)))
            .andExpect(jsonPath("$.[*].string").value(hasItem(DEFAULT_STRING.booleanValue())))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get the contacto
        restContactoMockMvc
            .perform(get(ENTITY_API_URL_ID, contacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contacto.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.etiqueta").value(DEFAULT_ETIQUETA))
            .andExpect(jsonPath("$.string").value(DEFAULT_STRING.booleanValue()))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingContacto() throws Exception {
        // Get the contacto
        restContactoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Update the contacto
        Contacto updatedContacto = contactoRepository.findById(contacto.getId()).get();
        // Disconnect from session so that the updates on updatedContacto are not directly saved in db
        em.detach(updatedContacto);
        updatedContacto
            .valor(UPDATED_VALOR)
            .etiqueta(UPDATED_ETIQUETA)
            .string(UPDATED_STRING)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContacto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedContacto))
            )
            .andExpect(status().isOk());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testContacto.getEtiqueta()).isEqualTo(UPDATED_ETIQUETA);
        assertThat(testContacto.getString()).isEqualTo(UPDATED_STRING);
        assertThat(testContacto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testContacto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testContacto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testContacto.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testContacto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();
        contacto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contacto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();
        contacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();
        contacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactoWithPatch() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Update the contacto using partial update
        Contacto partialUpdatedContacto = new Contacto();
        partialUpdatedContacto.setId(contacto.getId());

        partialUpdatedContacto.valor(UPDATED_VALOR).modifyByUser(UPDATED_MODIFY_BY_USER).auditStatus(UPDATED_AUDIT_STATUS);

        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacto))
            )
            .andExpect(status().isOk());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testContacto.getEtiqueta()).isEqualTo(DEFAULT_ETIQUETA);
        assertThat(testContacto.getString()).isEqualTo(DEFAULT_STRING);
        assertThat(testContacto.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testContacto.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testContacto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testContacto.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testContacto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateContactoWithPatch() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Update the contacto using partial update
        Contacto partialUpdatedContacto = new Contacto();
        partialUpdatedContacto.setId(contacto.getId());

        partialUpdatedContacto
            .valor(UPDATED_VALOR)
            .etiqueta(UPDATED_ETIQUETA)
            .string(UPDATED_STRING)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacto))
            )
            .andExpect(status().isOk());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
        Contacto testContacto = contactoList.get(contactoList.size() - 1);
        assertThat(testContacto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testContacto.getEtiqueta()).isEqualTo(UPDATED_ETIQUETA);
        assertThat(testContacto.getString()).isEqualTo(UPDATED_STRING);
        assertThat(testContacto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testContacto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testContacto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testContacto.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testContacto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();
        contacto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();
        contacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contacto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContacto() throws Exception {
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();
        contacto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contacto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacto in the database
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        int databaseSizeBeforeDelete = contactoRepository.findAll().size();

        // Delete the contacto
        restContactoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contacto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contacto> contactoList = contactoRepository.findAll();
        assertThat(contactoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
