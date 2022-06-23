package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "nss")
    private String nss;

    @Column(name = "finicio")
    private LocalDate finicio;

    @Column(name = "puesto")
    private String puesto;

    @Column(name = "salario", precision = 21, scale = 2)
    private BigDecimal salario;

    @Column(name = "dia_pago")
    private String diaPago;

    @Column(name = "tipo_pago")
    private String tipoPago;

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

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empleado" }, allowSetters = true)
    private Set<EmpleadoTipoVehiculo> empleadoTipoVehiculos = new HashSet<>();

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empleado" }, allowSetters = true)
    private Set<ExperienciaHabilidad> experienciaHabilidads = new HashSet<>();

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empleado" }, allowSetters = true)
    private Set<LicenciaManejo> licenciaManejos = new HashSet<>();

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diaHorarios", "empleado" }, allowSetters = true)
    private Set<Horario> horarios = new HashSet<>();

    @OneToMany(mappedBy = "empleado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "unidasViaje", "empleado" }, allowSetters = true)
    private Set<OperadorUnidad> operadorUnidads = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "personaMorals", "direccionPersonas", "empleados", "clientes", "contactos" }, allowSetters = true)
    private Persona persona;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inventarios", "empleados", "empresa" }, allowSetters = true)
    private Sucursal sucursal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empleado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRfc() {
        return this.rfc;
    }

    public Empleado rfc(String rfc) {
        this.setRfc(rfc);
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return this.nss;
    }

    public Empleado nss(String nss) {
        this.setNss(nss);
        return this;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public LocalDate getFinicio() {
        return this.finicio;
    }

    public Empleado finicio(LocalDate finicio) {
        this.setFinicio(finicio);
        return this;
    }

    public void setFinicio(LocalDate finicio) {
        this.finicio = finicio;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public Empleado puesto(String puesto) {
        this.setPuesto(puesto);
        return this;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    public Empleado salario(BigDecimal salario) {
        this.setSalario(salario);
        return this;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getDiaPago() {
        return this.diaPago;
    }

    public Empleado diaPago(String diaPago) {
        this.setDiaPago(diaPago);
        return this;
    }

    public void setDiaPago(String diaPago) {
        this.diaPago = diaPago;
    }

    public String getTipoPago() {
        return this.tipoPago;
    }

    public Empleado tipoPago(String tipoPago) {
        this.setTipoPago(tipoPago);
        return this;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Empleado createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Empleado createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Empleado modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Empleado modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Empleado auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<EmpleadoTipoVehiculo> getEmpleadoTipoVehiculos() {
        return this.empleadoTipoVehiculos;
    }

    public void setEmpleadoTipoVehiculos(Set<EmpleadoTipoVehiculo> empleadoTipoVehiculos) {
        if (this.empleadoTipoVehiculos != null) {
            this.empleadoTipoVehiculos.forEach(i -> i.setEmpleado(null));
        }
        if (empleadoTipoVehiculos != null) {
            empleadoTipoVehiculos.forEach(i -> i.setEmpleado(this));
        }
        this.empleadoTipoVehiculos = empleadoTipoVehiculos;
    }

    public Empleado empleadoTipoVehiculos(Set<EmpleadoTipoVehiculo> empleadoTipoVehiculos) {
        this.setEmpleadoTipoVehiculos(empleadoTipoVehiculos);
        return this;
    }

    public Empleado addEmpleadoTipoVehiculo(EmpleadoTipoVehiculo empleadoTipoVehiculo) {
        this.empleadoTipoVehiculos.add(empleadoTipoVehiculo);
        empleadoTipoVehiculo.setEmpleado(this);
        return this;
    }

    public Empleado removeEmpleadoTipoVehiculo(EmpleadoTipoVehiculo empleadoTipoVehiculo) {
        this.empleadoTipoVehiculos.remove(empleadoTipoVehiculo);
        empleadoTipoVehiculo.setEmpleado(null);
        return this;
    }

    public Set<ExperienciaHabilidad> getExperienciaHabilidads() {
        return this.experienciaHabilidads;
    }

    public void setExperienciaHabilidads(Set<ExperienciaHabilidad> experienciaHabilidads) {
        if (this.experienciaHabilidads != null) {
            this.experienciaHabilidads.forEach(i -> i.setEmpleado(null));
        }
        if (experienciaHabilidads != null) {
            experienciaHabilidads.forEach(i -> i.setEmpleado(this));
        }
        this.experienciaHabilidads = experienciaHabilidads;
    }

    public Empleado experienciaHabilidads(Set<ExperienciaHabilidad> experienciaHabilidads) {
        this.setExperienciaHabilidads(experienciaHabilidads);
        return this;
    }

    public Empleado addExperienciaHabilidad(ExperienciaHabilidad experienciaHabilidad) {
        this.experienciaHabilidads.add(experienciaHabilidad);
        experienciaHabilidad.setEmpleado(this);
        return this;
    }

    public Empleado removeExperienciaHabilidad(ExperienciaHabilidad experienciaHabilidad) {
        this.experienciaHabilidads.remove(experienciaHabilidad);
        experienciaHabilidad.setEmpleado(null);
        return this;
    }

    public Set<LicenciaManejo> getLicenciaManejos() {
        return this.licenciaManejos;
    }

    public void setLicenciaManejos(Set<LicenciaManejo> licenciaManejos) {
        if (this.licenciaManejos != null) {
            this.licenciaManejos.forEach(i -> i.setEmpleado(null));
        }
        if (licenciaManejos != null) {
            licenciaManejos.forEach(i -> i.setEmpleado(this));
        }
        this.licenciaManejos = licenciaManejos;
    }

    public Empleado licenciaManejos(Set<LicenciaManejo> licenciaManejos) {
        this.setLicenciaManejos(licenciaManejos);
        return this;
    }

    public Empleado addLicenciaManejo(LicenciaManejo licenciaManejo) {
        this.licenciaManejos.add(licenciaManejo);
        licenciaManejo.setEmpleado(this);
        return this;
    }

    public Empleado removeLicenciaManejo(LicenciaManejo licenciaManejo) {
        this.licenciaManejos.remove(licenciaManejo);
        licenciaManejo.setEmpleado(null);
        return this;
    }

    public Set<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        if (this.horarios != null) {
            this.horarios.forEach(i -> i.setEmpleado(null));
        }
        if (horarios != null) {
            horarios.forEach(i -> i.setEmpleado(this));
        }
        this.horarios = horarios;
    }

    public Empleado horarios(Set<Horario> horarios) {
        this.setHorarios(horarios);
        return this;
    }

    public Empleado addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setEmpleado(this);
        return this;
    }

    public Empleado removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setEmpleado(null);
        return this;
    }

    public Set<OperadorUnidad> getOperadorUnidads() {
        return this.operadorUnidads;
    }

    public void setOperadorUnidads(Set<OperadorUnidad> operadorUnidads) {
        if (this.operadorUnidads != null) {
            this.operadorUnidads.forEach(i -> i.setEmpleado(null));
        }
        if (operadorUnidads != null) {
            operadorUnidads.forEach(i -> i.setEmpleado(this));
        }
        this.operadorUnidads = operadorUnidads;
    }

    public Empleado operadorUnidads(Set<OperadorUnidad> operadorUnidads) {
        this.setOperadorUnidads(operadorUnidads);
        return this;
    }

    public Empleado addOperadorUnidad(OperadorUnidad operadorUnidad) {
        this.operadorUnidads.add(operadorUnidad);
        operadorUnidad.setEmpleado(this);
        return this;
    }

    public Empleado removeOperadorUnidad(OperadorUnidad operadorUnidad) {
        this.operadorUnidads.remove(operadorUnidad);
        operadorUnidad.setEmpleado(null);
        return this;
    }

    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Empleado persona(Persona persona) {
        this.setPersona(persona);
        return this;
    }

    public Sucursal getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Empleado sucursal(Sucursal sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleado)) {
            return false;
        }
        return id != null && id.equals(((Empleado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", rfc='" + getRfc() + "'" +
            ", nss='" + getNss() + "'" +
            ", finicio='" + getFinicio() + "'" +
            ", puesto='" + getPuesto() + "'" +
            ", salario=" + getSalario() +
            ", diaPago='" + getDiaPago() + "'" +
            ", tipoPago='" + getTipoPago() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
