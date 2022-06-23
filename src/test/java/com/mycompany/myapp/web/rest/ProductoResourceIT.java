package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.repository.ProductoRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final String DEFAULT_UNIDAD_MEDIDA = "AAAAAAAAAA";
    private static final String UPDATED_UNIDAD_MEDIDA = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_USO = "AAAAAAAAAA";
    private static final String UPDATED_USO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DESCRIPCION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DESCRIPCION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DESCRIPCION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DESCRIPCION_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_OTROS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OTROS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OTROS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OTROS_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoMockMvc;

    private Producto producto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .nombre(DEFAULT_NOMBRE)
            .marca(DEFAULT_MARCA)
            .tipo(DEFAULT_TIPO)
            .cantidad(DEFAULT_CANTIDAD)
            .unidadMedida(DEFAULT_UNIDAD_MEDIDA)
            .material(DEFAULT_MATERIAL)
            .color(DEFAULT_COLOR)
            .uso(DEFAULT_USO)
            .descripcion(DEFAULT_DESCRIPCION)
            .descripcionContentType(DEFAULT_DESCRIPCION_CONTENT_TYPE)
            .otros(DEFAULT_OTROS)
            .otrosContentType(DEFAULT_OTROS_CONTENT_TYPE)
            .createByUser(DEFAULT_CREATE_BY_USER)
            .createdOn(DEFAULT_CREATED_ON)
            .modifyByUser(DEFAULT_MODIFY_BY_USER)
            .modifiedOn(DEFAULT_MODIFIED_ON)
            .auditStatus(DEFAULT_AUDIT_STATUS);
        return producto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .nombre(UPDATED_NOMBRE)
            .marca(UPDATED_MARCA)
            .tipo(UPDATED_TIPO)
            .cantidad(UPDATED_CANTIDAD)
            .unidadMedida(UPDATED_UNIDAD_MEDIDA)
            .material(UPDATED_MATERIAL)
            .color(UPDATED_COLOR)
            .uso(UPDATED_USO)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .otros(UPDATED_OTROS)
            .otrosContentType(UPDATED_OTROS_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();
        // Create the Producto
        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProducto.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testProducto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testProducto.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProducto.getUnidadMedida()).isEqualTo(DEFAULT_UNIDAD_MEDIDA);
        assertThat(testProducto.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testProducto.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProducto.getUso()).isEqualTo(DEFAULT_USO);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testProducto.getOtros()).isEqualTo(DEFAULT_OTROS);
        assertThat(testProducto.getOtrosContentType()).isEqualTo(DEFAULT_OTROS_CONTENT_TYPE);
        assertThat(testProducto.getCreateByUser()).isEqualTo(DEFAULT_CREATE_BY_USER);
        assertThat(testProducto.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProducto.getModifyByUser()).isEqualTo(DEFAULT_MODIFY_BY_USER);
        assertThat(testProducto.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testProducto.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void createProductoWithExistingId() throws Exception {
        // Create the Producto with an existing ID
        producto.setId(1L);

        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].unidadMedida").value(hasItem(DEFAULT_UNIDAD_MEDIDA)))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].uso").value(hasItem(DEFAULT_USO)))
            .andExpect(jsonPath("$.[*].descripcionContentType").value(hasItem(DEFAULT_DESCRIPCION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(Base64Utils.encodeToString(DEFAULT_DESCRIPCION))))
            .andExpect(jsonPath("$.[*].otrosContentType").value(hasItem(DEFAULT_OTROS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otros").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTROS))))
            .andExpect(jsonPath("$.[*].createByUser").value(hasItem(DEFAULT_CREATE_BY_USER)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].modifyByUser").value(hasItem(DEFAULT_MODIFY_BY_USER)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())))
            .andExpect(jsonPath("$.[*].auditStatus").value(hasItem(DEFAULT_AUDIT_STATUS)));
    }

    @Test
    @Transactional
    void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.unidadMedida").value(DEFAULT_UNIDAD_MEDIDA))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.uso").value(DEFAULT_USO))
            .andExpect(jsonPath("$.descripcionContentType").value(DEFAULT_DESCRIPCION_CONTENT_TYPE))
            .andExpect(jsonPath("$.descripcion").value(Base64Utils.encodeToString(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.otrosContentType").value(DEFAULT_OTROS_CONTENT_TYPE))
            .andExpect(jsonPath("$.otros").value(Base64Utils.encodeToString(DEFAULT_OTROS)))
            .andExpect(jsonPath("$.createByUser").value(DEFAULT_CREATE_BY_USER))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.modifyByUser").value(DEFAULT_MODIFY_BY_USER))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()))
            .andExpect(jsonPath("$.auditStatus").value(DEFAULT_AUDIT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .nombre(UPDATED_NOMBRE)
            .marca(UPDATED_MARCA)
            .tipo(UPDATED_TIPO)
            .cantidad(UPDATED_CANTIDAD)
            .unidadMedida(UPDATED_UNIDAD_MEDIDA)
            .material(UPDATED_MATERIAL)
            .color(UPDATED_COLOR)
            .uso(UPDATED_USO)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .otros(UPDATED_OTROS)
            .otrosContentType(UPDATED_OTROS_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProducto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testProducto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testProducto.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProducto.getUnidadMedida()).isEqualTo(UPDATED_UNIDAD_MEDIDA);
        assertThat(testProducto.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testProducto.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducto.getUso()).isEqualTo(UPDATED_USO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testProducto.getOtros()).isEqualTo(UPDATED_OTROS);
        assertThat(testProducto.getOtrosContentType()).isEqualTo(UPDATED_OTROS_CONTENT_TYPE);
        assertThat(testProducto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testProducto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProducto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testProducto.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testProducto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, producto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoWithPatch() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto using partial update
        Producto partialUpdatedProducto = new Producto();
        partialUpdatedProducto.setId(producto.getId());

        partialUpdatedProducto
            .nombre(UPDATED_NOMBRE)
            .marca(UPDATED_MARCA)
            .unidadMedida(UPDATED_UNIDAD_MEDIDA)
            .color(UPDATED_COLOR)
            .uso(UPDATED_USO)
            .otros(UPDATED_OTROS)
            .otrosContentType(UPDATED_OTROS_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER);

        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testProducto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testProducto.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProducto.getUnidadMedida()).isEqualTo(UPDATED_UNIDAD_MEDIDA);
        assertThat(testProducto.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testProducto.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducto.getUso()).isEqualTo(UPDATED_USO);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getDescripcionContentType()).isEqualTo(DEFAULT_DESCRIPCION_CONTENT_TYPE);
        assertThat(testProducto.getOtros()).isEqualTo(UPDATED_OTROS);
        assertThat(testProducto.getOtrosContentType()).isEqualTo(UPDATED_OTROS_CONTENT_TYPE);
        assertThat(testProducto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testProducto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProducto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testProducto.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);
        assertThat(testProducto.getAuditStatus()).isEqualTo(DEFAULT_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateProductoWithPatch() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto using partial update
        Producto partialUpdatedProducto = new Producto();
        partialUpdatedProducto.setId(producto.getId());

        partialUpdatedProducto
            .nombre(UPDATED_NOMBRE)
            .marca(UPDATED_MARCA)
            .tipo(UPDATED_TIPO)
            .cantidad(UPDATED_CANTIDAD)
            .unidadMedida(UPDATED_UNIDAD_MEDIDA)
            .material(UPDATED_MATERIAL)
            .color(UPDATED_COLOR)
            .uso(UPDATED_USO)
            .descripcion(UPDATED_DESCRIPCION)
            .descripcionContentType(UPDATED_DESCRIPCION_CONTENT_TYPE)
            .otros(UPDATED_OTROS)
            .otrosContentType(UPDATED_OTROS_CONTENT_TYPE)
            .createByUser(UPDATED_CREATE_BY_USER)
            .createdOn(UPDATED_CREATED_ON)
            .modifyByUser(UPDATED_MODIFY_BY_USER)
            .modifiedOn(UPDATED_MODIFIED_ON)
            .auditStatus(UPDATED_AUDIT_STATUS);

        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducto))
            )
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProducto.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testProducto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testProducto.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProducto.getUnidadMedida()).isEqualTo(UPDATED_UNIDAD_MEDIDA);
        assertThat(testProducto.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testProducto.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProducto.getUso()).isEqualTo(UPDATED_USO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getDescripcionContentType()).isEqualTo(UPDATED_DESCRIPCION_CONTENT_TYPE);
        assertThat(testProducto.getOtros()).isEqualTo(UPDATED_OTROS);
        assertThat(testProducto.getOtrosContentType()).isEqualTo(UPDATED_OTROS_CONTENT_TYPE);
        assertThat(testProducto.getCreateByUser()).isEqualTo(UPDATED_CREATE_BY_USER);
        assertThat(testProducto.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProducto.getModifyByUser()).isEqualTo(UPDATED_MODIFY_BY_USER);
        assertThat(testProducto.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);
        assertThat(testProducto.getAuditStatus()).isEqualTo(UPDATED_AUDIT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, producto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(producto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(producto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();
        producto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, producto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
