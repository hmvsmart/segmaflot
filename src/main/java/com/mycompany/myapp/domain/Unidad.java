package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Unidad.
 */
@Entity
@Table(name = "unidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "km_actual")
    private Double kmActual;

    @Column(name = "tipo_adquisicion")
    private String tipoAdquisicion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "color")
    private String color;

    @Lob
    @Column(name = "descripcion")
    private byte[] descripcion;

    @Column(name = "descripcion_content_type")
    private String descripcionContentType;

    @Column(name = "status")
    private Boolean status;

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

    @OneToMany(mappedBy = "unidad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aditamento", "unidad" }, allowSetters = true)
    private Set<AditamentoUnidad> aditamentoUnidads = new HashSet<>();

    @OneToMany(mappedBy = "unidad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "unidad" }, allowSetters = true)
    private Set<Poliza> polizas = new HashSet<>();

    @OneToMany(mappedBy = "unidad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "operadorUnidads", "viaje", "unidad" }, allowSetters = true)
    private Set<UnidadViaje> unidadViajes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "unidads" }, allowSetters = true)
    private Vehiculo vehiculo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Unidad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Unidad fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Unidad numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Double getKmActual() {
        return this.kmActual;
    }

    public Unidad kmActual(Double kmActual) {
        this.setKmActual(kmActual);
        return this;
    }

    public void setKmActual(Double kmActual) {
        this.kmActual = kmActual;
    }

    public String getTipoAdquisicion() {
        return this.tipoAdquisicion;
    }

    public Unidad tipoAdquisicion(String tipoAdquisicion) {
        this.setTipoAdquisicion(tipoAdquisicion);
        return this;
    }

    public void setTipoAdquisicion(String tipoAdquisicion) {
        this.tipoAdquisicion = tipoAdquisicion;
    }

    public byte[] getImagen() {
        return this.imagen;
    }

    public Unidad imagen(byte[] imagen) {
        this.setImagen(imagen);
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return this.imagenContentType;
    }

    public Unidad imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getColor() {
        return this.color;
    }

    public Unidad color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public byte[] getDescripcion() {
        return this.descripcion;
    }

    public Unidad descripcion(byte[] descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(byte[] descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionContentType() {
        return this.descripcionContentType;
    }

    public Unidad descripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
        return this;
    }

    public void setDescripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Unidad status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Unidad createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Unidad createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Unidad modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Unidad modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Unidad auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<AditamentoUnidad> getAditamentoUnidads() {
        return this.aditamentoUnidads;
    }

    public void setAditamentoUnidads(Set<AditamentoUnidad> aditamentoUnidads) {
        if (this.aditamentoUnidads != null) {
            this.aditamentoUnidads.forEach(i -> i.setUnidad(null));
        }
        if (aditamentoUnidads != null) {
            aditamentoUnidads.forEach(i -> i.setUnidad(this));
        }
        this.aditamentoUnidads = aditamentoUnidads;
    }

    public Unidad aditamentoUnidads(Set<AditamentoUnidad> aditamentoUnidads) {
        this.setAditamentoUnidads(aditamentoUnidads);
        return this;
    }

    public Unidad addAditamentoUnidad(AditamentoUnidad aditamentoUnidad) {
        this.aditamentoUnidads.add(aditamentoUnidad);
        aditamentoUnidad.setUnidad(this);
        return this;
    }

    public Unidad removeAditamentoUnidad(AditamentoUnidad aditamentoUnidad) {
        this.aditamentoUnidads.remove(aditamentoUnidad);
        aditamentoUnidad.setUnidad(null);
        return this;
    }

    public Set<Poliza> getPolizas() {
        return this.polizas;
    }

    public void setPolizas(Set<Poliza> polizas) {
        if (this.polizas != null) {
            this.polizas.forEach(i -> i.setUnidad(null));
        }
        if (polizas != null) {
            polizas.forEach(i -> i.setUnidad(this));
        }
        this.polizas = polizas;
    }

    public Unidad polizas(Set<Poliza> polizas) {
        this.setPolizas(polizas);
        return this;
    }

    public Unidad addPoliza(Poliza poliza) {
        this.polizas.add(poliza);
        poliza.setUnidad(this);
        return this;
    }

    public Unidad removePoliza(Poliza poliza) {
        this.polizas.remove(poliza);
        poliza.setUnidad(null);
        return this;
    }

    public Set<UnidadViaje> getUnidadViajes() {
        return this.unidadViajes;
    }

    public void setUnidadViajes(Set<UnidadViaje> unidadViajes) {
        if (this.unidadViajes != null) {
            this.unidadViajes.forEach(i -> i.setUnidad(null));
        }
        if (unidadViajes != null) {
            unidadViajes.forEach(i -> i.setUnidad(this));
        }
        this.unidadViajes = unidadViajes;
    }

    public Unidad unidadViajes(Set<UnidadViaje> unidadViajes) {
        this.setUnidadViajes(unidadViajes);
        return this;
    }

    public Unidad addUnidadViaje(UnidadViaje unidadViaje) {
        this.unidadViajes.add(unidadViaje);
        unidadViaje.setUnidad(this);
        return this;
    }

    public Unidad removeUnidadViaje(UnidadViaje unidadViaje) {
        this.unidadViajes.remove(unidadViaje);
        unidadViaje.setUnidad(null);
        return this;
    }

    public Vehiculo getVehiculo() {
        return this.vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Unidad vehiculo(Vehiculo vehiculo) {
        this.setVehiculo(vehiculo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unidad)) {
            return false;
        }
        return id != null && id.equals(((Unidad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unidad{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", kmActual=" + getKmActual() +
            ", tipoAdquisicion='" + getTipoAdquisicion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", color='" + getColor() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", descripcionContentType='" + getDescripcionContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
