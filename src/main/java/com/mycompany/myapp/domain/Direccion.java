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
 * A Direccion.
 */
@Entity
@Table(name = "direccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numero_exterior")
    private Integer numeroExterior;

    @Column(name = "numero_interior")
    private String numeroInterior;

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

    @OneToMany(mappedBy = "direccion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "direccion", "persona" }, allowSetters = true)
    private Set<DireccionPersona> direccionPersonas = new HashSet<>();

    @OneToMany(mappedBy = "origen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "itinerarios", "unidadViajes", "origen" }, allowSetters = true)
    private Set<Viaje> viajes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "direccions", "cp" }, allowSetters = true)
    private Colonia colonia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Direccion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return this.calle;
    }

    public Direccion calle(String calle) {
        this.setCalle(calle);
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumeroExterior() {
        return this.numeroExterior;
    }

    public Direccion numeroExterior(Integer numeroExterior) {
        this.setNumeroExterior(numeroExterior);
        return this;
    }

    public void setNumeroExterior(Integer numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return this.numeroInterior;
    }

    public Direccion numeroInterior(String numeroInterior) {
        this.setNumeroInterior(numeroInterior);
        return this;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Direccion createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Direccion createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Direccion modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Direccion modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Direccion auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<DireccionPersona> getDireccionPersonas() {
        return this.direccionPersonas;
    }

    public void setDireccionPersonas(Set<DireccionPersona> direccionPersonas) {
        if (this.direccionPersonas != null) {
            this.direccionPersonas.forEach(i -> i.setDireccion(null));
        }
        if (direccionPersonas != null) {
            direccionPersonas.forEach(i -> i.setDireccion(this));
        }
        this.direccionPersonas = direccionPersonas;
    }

    public Direccion direccionPersonas(Set<DireccionPersona> direccionPersonas) {
        this.setDireccionPersonas(direccionPersonas);
        return this;
    }

    public Direccion addDireccionPersona(DireccionPersona direccionPersona) {
        this.direccionPersonas.add(direccionPersona);
        direccionPersona.setDireccion(this);
        return this;
    }

    public Direccion removeDireccionPersona(DireccionPersona direccionPersona) {
        this.direccionPersonas.remove(direccionPersona);
        direccionPersona.setDireccion(null);
        return this;
    }

    public Set<Viaje> getViajes() {
        return this.viajes;
    }

    public void setViajes(Set<Viaje> viajes) {
        if (this.viajes != null) {
            this.viajes.forEach(i -> i.setOrigen(null));
        }
        if (viajes != null) {
            viajes.forEach(i -> i.setOrigen(this));
        }
        this.viajes = viajes;
    }

    public Direccion viajes(Set<Viaje> viajes) {
        this.setViajes(viajes);
        return this;
    }

    public Direccion addViaje(Viaje viaje) {
        this.viajes.add(viaje);
        viaje.setOrigen(this);
        return this;
    }

    public Direccion removeViaje(Viaje viaje) {
        this.viajes.remove(viaje);
        viaje.setOrigen(null);
        return this;
    }

    public Colonia getColonia() {
        return this.colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    public Direccion colonia(Colonia colonia) {
        this.setColonia(colonia);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direccion)) {
            return false;
        }
        return id != null && id.equals(((Direccion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Direccion{" +
            "id=" + getId() +
            ", calle='" + getCalle() + "'" +
            ", numeroExterior=" + getNumeroExterior() +
            ", numeroInterior='" + getNumeroInterior() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
