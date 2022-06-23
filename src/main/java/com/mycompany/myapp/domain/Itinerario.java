package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Itinerario.
 */
@Entity
@Table(name = "itinerario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Itinerario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "accion")
    private String accion;

    @Column(name = "fecha_hora_estimada")
    private Instant fechaHoraEstimada;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "itinerarios", "unidadViajes", "origen" }, allowSetters = true)
    private Viaje viaje;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Itinerario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccion() {
        return this.accion;
    }

    public Itinerario accion(String accion) {
        this.setAccion(accion);
        return this;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Instant getFechaHoraEstimada() {
        return this.fechaHoraEstimada;
    }

    public Itinerario fechaHoraEstimada(Instant fechaHoraEstimada) {
        this.setFechaHoraEstimada(fechaHoraEstimada);
        return this;
    }

    public void setFechaHoraEstimada(Instant fechaHoraEstimada) {
        this.fechaHoraEstimada = fechaHoraEstimada;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Itinerario createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Itinerario createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Itinerario modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Itinerario modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Itinerario auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Viaje getViaje() {
        return this.viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Itinerario viaje(Viaje viaje) {
        this.setViaje(viaje);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Itinerario)) {
            return false;
        }
        return id != null && id.equals(((Itinerario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Itinerario{" +
            "id=" + getId() +
            ", accion='" + getAccion() + "'" +
            ", fechaHoraEstimada='" + getFechaHoraEstimada() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
