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
 * A UnidadViaje.
 */
@Entity
@Table(name = "unidad_viaje")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnidadViaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

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

    @OneToMany(mappedBy = "unidasViaje")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "unidasViaje", "empleado" }, allowSetters = true)
    private Set<OperadorUnidad> operadorUnidads = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "itinerarios", "unidadViajes", "origen" }, allowSetters = true)
    private Viaje viaje;

    @ManyToOne
    @JsonIgnoreProperties(value = { "aditamentoUnidads", "polizas", "unidadViajes", "vehiculo" }, allowSetters = true)
    private Unidad unidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UnidadViaje id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public UnidadViaje fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public UnidadViaje status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public UnidadViaje createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public UnidadViaje createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public UnidadViaje modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public UnidadViaje modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public UnidadViaje auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<OperadorUnidad> getOperadorUnidads() {
        return this.operadorUnidads;
    }

    public void setOperadorUnidads(Set<OperadorUnidad> operadorUnidads) {
        if (this.operadorUnidads != null) {
            this.operadorUnidads.forEach(i -> i.setUnidasViaje(null));
        }
        if (operadorUnidads != null) {
            operadorUnidads.forEach(i -> i.setUnidasViaje(this));
        }
        this.operadorUnidads = operadorUnidads;
    }

    public UnidadViaje operadorUnidads(Set<OperadorUnidad> operadorUnidads) {
        this.setOperadorUnidads(operadorUnidads);
        return this;
    }

    public UnidadViaje addOperadorUnidad(OperadorUnidad operadorUnidad) {
        this.operadorUnidads.add(operadorUnidad);
        operadorUnidad.setUnidasViaje(this);
        return this;
    }

    public UnidadViaje removeOperadorUnidad(OperadorUnidad operadorUnidad) {
        this.operadorUnidads.remove(operadorUnidad);
        operadorUnidad.setUnidasViaje(null);
        return this;
    }

    public Viaje getViaje() {
        return this.viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public UnidadViaje viaje(Viaje viaje) {
        this.setViaje(viaje);
        return this;
    }

    public Unidad getUnidad() {
        return this.unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public UnidadViaje unidad(Unidad unidad) {
        this.setUnidad(unidad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadViaje)) {
            return false;
        }
        return id != null && id.equals(((UnidadViaje) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnidadViaje{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", status='" + getStatus() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
