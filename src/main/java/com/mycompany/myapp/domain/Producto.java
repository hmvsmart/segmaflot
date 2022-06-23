package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "marca")
    private String marca;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "material")
    private String material;

    @Column(name = "color")
    private String color;

    @Column(name = "uso")
    private String uso;

    @Lob
    @Column(name = "descripcion")
    private byte[] descripcion;

    @Column(name = "descripcion_content_type")
    private String descripcionContentType;

    @Lob
    @Column(name = "otros")
    private byte[] otros;

    @Column(name = "otros_content_type")
    private String otrosContentType;

    @Column(name = "create_by_user")
    private String createByUser;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modify_by_user")
    private String modifyByUser;

    @Column(name = "modified_on")
    private Instant modifiedOn;

    @Column(name = "audit_status")
    private String auditStatus;

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ubicacionInventarios", "resurtirs", "precioVentas", "materialServicios", "perdidas", "listaPedidos", "producto", "sucursal",
        },
        allowSetters = true
    )
    private Set<Inventario> inventarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Producto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return this.marca;
    }

    public Producto marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Producto tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Producto cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return this.unidadMedida;
    }

    public Producto unidadMedida(String unidadMedida) {
        this.setUnidadMedida(unidadMedida);
        return this;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getMaterial() {
        return this.material;
    }

    public Producto material(String material) {
        this.setMaterial(material);
        return this;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return this.color;
    }

    public Producto color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUso() {
        return this.uso;
    }

    public Producto uso(String uso) {
        this.setUso(uso);
        return this;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public byte[] getDescripcion() {
        return this.descripcion;
    }

    public Producto descripcion(byte[] descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(byte[] descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionContentType() {
        return this.descripcionContentType;
    }

    public Producto descripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
        return this;
    }

    public void setDescripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
    }

    public byte[] getOtros() {
        return this.otros;
    }

    public Producto otros(byte[] otros) {
        this.setOtros(otros);
        return this;
    }

    public void setOtros(byte[] otros) {
        this.otros = otros;
    }

    public String getOtrosContentType() {
        return this.otrosContentType;
    }

    public Producto otrosContentType(String otrosContentType) {
        this.otrosContentType = otrosContentType;
        return this;
    }

    public void setOtrosContentType(String otrosContentType) {
        this.otrosContentType = otrosContentType;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Producto createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Producto createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Producto modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Producto modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Producto auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<Inventario> getInventarios() {
        return this.inventarios;
    }

    public void setInventarios(Set<Inventario> inventarios) {
        if (this.inventarios != null) {
            this.inventarios.forEach(i -> i.setProducto(null));
        }
        if (inventarios != null) {
            inventarios.forEach(i -> i.setProducto(this));
        }
        this.inventarios = inventarios;
    }

    public Producto inventarios(Set<Inventario> inventarios) {
        this.setInventarios(inventarios);
        return this;
    }

    public Producto addInventario(Inventario inventario) {
        this.inventarios.add(inventario);
        inventario.setProducto(this);
        return this;
    }

    public Producto removeInventario(Inventario inventario) {
        this.inventarios.remove(inventario);
        inventario.setProducto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", marca='" + getMarca() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", cantidad=" + getCantidad() +
            ", unidadMedida='" + getUnidadMedida() + "'" +
            ", material='" + getMaterial() + "'" +
            ", color='" + getColor() + "'" +
            ", uso='" + getUso() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", descripcionContentType='" + getDescripcionContentType() + "'" +
            ", otros='" + getOtros() + "'" +
            ", otrosContentType='" + getOtrosContentType() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
