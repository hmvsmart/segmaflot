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
 * A Servicio.
 */
@Entity
@Table(name = "servicio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private byte[] descripcion;

    @Column(name = "descripcion_content_type")
    private String descripcionContentType;

    @Column(name = "duracion_estimada")
    private Instant duracionEstimada;

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

    @OneToMany(mappedBy = "servicio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inventario", "servicio" }, allowSetters = true)
    private Set<MaterialServicio> materialServicios = new HashSet<>();

    @OneToMany(mappedBy = "servicio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicio" }, allowSetters = true)
    private Set<PrecioServicio> precioServicios = new HashSet<>();

    @OneToMany(mappedBy = "servicio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicio", "unidadServicio" }, allowSetters = true)
    private Set<ListaServicio> listaServicios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Servicio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Servicio nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getDescripcion() {
        return this.descripcion;
    }

    public Servicio descripcion(byte[] descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(byte[] descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionContentType() {
        return this.descripcionContentType;
    }

    public Servicio descripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
        return this;
    }

    public void setDescripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
    }

    public Instant getDuracionEstimada() {
        return this.duracionEstimada;
    }

    public Servicio duracionEstimada(Instant duracionEstimada) {
        this.setDuracionEstimada(duracionEstimada);
        return this;
    }

    public void setDuracionEstimada(Instant duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Servicio createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Servicio createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Servicio modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Servicio modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Servicio auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<MaterialServicio> getMaterialServicios() {
        return this.materialServicios;
    }

    public void setMaterialServicios(Set<MaterialServicio> materialServicios) {
        if (this.materialServicios != null) {
            this.materialServicios.forEach(i -> i.setServicio(null));
        }
        if (materialServicios != null) {
            materialServicios.forEach(i -> i.setServicio(this));
        }
        this.materialServicios = materialServicios;
    }

    public Servicio materialServicios(Set<MaterialServicio> materialServicios) {
        this.setMaterialServicios(materialServicios);
        return this;
    }

    public Servicio addMaterialServicio(MaterialServicio materialServicio) {
        this.materialServicios.add(materialServicio);
        materialServicio.setServicio(this);
        return this;
    }

    public Servicio removeMaterialServicio(MaterialServicio materialServicio) {
        this.materialServicios.remove(materialServicio);
        materialServicio.setServicio(null);
        return this;
    }

    public Set<PrecioServicio> getPrecioServicios() {
        return this.precioServicios;
    }

    public void setPrecioServicios(Set<PrecioServicio> precioServicios) {
        if (this.precioServicios != null) {
            this.precioServicios.forEach(i -> i.setServicio(null));
        }
        if (precioServicios != null) {
            precioServicios.forEach(i -> i.setServicio(this));
        }
        this.precioServicios = precioServicios;
    }

    public Servicio precioServicios(Set<PrecioServicio> precioServicios) {
        this.setPrecioServicios(precioServicios);
        return this;
    }

    public Servicio addPrecioServicio(PrecioServicio precioServicio) {
        this.precioServicios.add(precioServicio);
        precioServicio.setServicio(this);
        return this;
    }

    public Servicio removePrecioServicio(PrecioServicio precioServicio) {
        this.precioServicios.remove(precioServicio);
        precioServicio.setServicio(null);
        return this;
    }

    public Set<ListaServicio> getListaServicios() {
        return this.listaServicios;
    }

    public void setListaServicios(Set<ListaServicio> listaServicios) {
        if (this.listaServicios != null) {
            this.listaServicios.forEach(i -> i.setServicio(null));
        }
        if (listaServicios != null) {
            listaServicios.forEach(i -> i.setServicio(this));
        }
        this.listaServicios = listaServicios;
    }

    public Servicio listaServicios(Set<ListaServicio> listaServicios) {
        this.setListaServicios(listaServicios);
        return this;
    }

    public Servicio addListaServicio(ListaServicio listaServicio) {
        this.listaServicios.add(listaServicio);
        listaServicio.setServicio(this);
        return this;
    }

    public Servicio removeListaServicio(ListaServicio listaServicio) {
        this.listaServicios.remove(listaServicio);
        listaServicio.setServicio(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servicio)) {
            return false;
        }
        return id != null && id.equals(((Servicio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servicio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", descripcionContentType='" + getDescripcionContentType() + "'" +
            ", duracionEstimada='" + getDuracionEstimada() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
