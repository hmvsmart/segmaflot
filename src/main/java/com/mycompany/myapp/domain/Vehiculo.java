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
 * A Vehiculo.
 */
@Entity
@Table(name = "vehiculo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "submarca")
    private String submarca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "generacion")
    private String generacion;

    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;

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

    @OneToMany(mappedBy = "vehiculo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aditamentoUnidads", "polizas", "unidadViajes", "vehiculo" }, allowSetters = true)
    private Set<Unidad> unidads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehiculo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return this.marca;
    }

    public Vehiculo marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSubmarca() {
        return this.submarca;
    }

    public Vehiculo submarca(String submarca) {
        this.setSubmarca(submarca);
        return this;
    }

    public void setSubmarca(String submarca) {
        this.submarca = submarca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Vehiculo modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getGeneracion() {
        return this.generacion;
    }

    public Vehiculo generacion(String generacion) {
        this.setGeneracion(generacion);
        return this;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public String getTipoVehiculo() {
        return this.tipoVehiculo;
    }

    public Vehiculo tipoVehiculo(String tipoVehiculo) {
        this.setTipoVehiculo(tipoVehiculo);
        return this;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Vehiculo createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Vehiculo createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Vehiculo modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Vehiculo modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Vehiculo auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<Unidad> getUnidads() {
        return this.unidads;
    }

    public void setUnidads(Set<Unidad> unidads) {
        if (this.unidads != null) {
            this.unidads.forEach(i -> i.setVehiculo(null));
        }
        if (unidads != null) {
            unidads.forEach(i -> i.setVehiculo(this));
        }
        this.unidads = unidads;
    }

    public Vehiculo unidads(Set<Unidad> unidads) {
        this.setUnidads(unidads);
        return this;
    }

    public Vehiculo addUnidad(Unidad unidad) {
        this.unidads.add(unidad);
        unidad.setVehiculo(this);
        return this;
    }

    public Vehiculo removeUnidad(Unidad unidad) {
        this.unidads.remove(unidad);
        unidad.setVehiculo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehiculo)) {
            return false;
        }
        return id != null && id.equals(((Vehiculo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehiculo{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", submarca='" + getSubmarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", generacion='" + getGeneracion() + "'" +
            ", tipoVehiculo='" + getTipoVehiculo() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
