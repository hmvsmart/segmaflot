package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LicenciaManejo.
 */
@Entity
@Table(name = "licencia_manejo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LicenciaManejo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

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
    @JsonIgnoreProperties(
        value = {
            "empleadoTipoVehiculos", "experienciaHabilidads", "licenciaManejos", "horarios", "operadorUnidads", "persona", "sucursal",
        },
        allowSetters = true
    )
    private Empleado empleado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LicenciaManejo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public LicenciaManejo fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return this.tipo;
    }

    public LicenciaManejo tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public LicenciaManejo status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getFechaExpiracion() {
        return this.fechaExpiracion;
    }

    public LicenciaManejo fechaExpiracion(LocalDate fechaExpiracion) {
        this.setFechaExpiracion(fechaExpiracion);
        return this;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public LicenciaManejo createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public LicenciaManejo createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public LicenciaManejo modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public LicenciaManejo modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public LicenciaManejo auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LicenciaManejo empleado(Empleado empleado) {
        this.setEmpleado(empleado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenciaManejo)) {
            return false;
        }
        return id != null && id.equals(((LicenciaManejo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenciaManejo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", status='" + getStatus() + "'" +
            ", fechaExpiracion='" + getFechaExpiracion() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
