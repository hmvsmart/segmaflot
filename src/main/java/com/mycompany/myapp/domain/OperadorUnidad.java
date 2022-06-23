package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OperadorUnidad.
 */
@Entity
@Table(name = "operador_unidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperadorUnidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "rol")
    private String rol;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "operadorUnidads", "viaje", "unidad" }, allowSetters = true)
    private UnidadViaje unidasViaje;

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

    public OperadorUnidad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public OperadorUnidad fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getRol() {
        return this.rol;
    }

    public OperadorUnidad rol(String rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public OperadorUnidad status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public OperadorUnidad createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public OperadorUnidad createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public OperadorUnidad modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public OperadorUnidad modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public OperadorUnidad auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public UnidadViaje getUnidasViaje() {
        return this.unidasViaje;
    }

    public void setUnidasViaje(UnidadViaje unidadViaje) {
        this.unidasViaje = unidadViaje;
    }

    public OperadorUnidad unidasViaje(UnidadViaje unidadViaje) {
        this.setUnidasViaje(unidadViaje);
        return this;
    }

    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public OperadorUnidad empleado(Empleado empleado) {
        this.setEmpleado(empleado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperadorUnidad)) {
            return false;
        }
        return id != null && id.equals(((OperadorUnidad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperadorUnidad{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", rol='" + getRol() + "'" +
            ", status='" + getStatus() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
