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
 * A Sucursal.
 */
@Entity
@Table(name = "sucursal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo_sucursal")
    private String tipoSucursal;

    @Column(name = "telefono")
    private String telefono;

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

    @OneToMany(mappedBy = "sucursal")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "ubicacionInventarios", "resurtirs", "precioVentas", "materialServicios", "perdidas", "listaPedidos", "producto", "sucursal",
        },
        allowSetters = true
    )
    private Set<Inventario> inventarios = new HashSet<>();

    @OneToMany(mappedBy = "sucursal")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "empleadoTipoVehiculos", "experienciaHabilidads", "licenciaManejos", "horarios", "operadorUnidads", "persona", "sucursal",
        },
        allowSetters = true
    )
    private Set<Empleado> empleados = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "sucursals", "reportes", "pedidos", "persona" }, allowSetters = true)
    private PersonaMoral empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sucursal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Sucursal nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoSucursal() {
        return this.tipoSucursal;
    }

    public Sucursal tipoSucursal(String tipoSucursal) {
        this.setTipoSucursal(tipoSucursal);
        return this;
    }

    public void setTipoSucursal(String tipoSucursal) {
        this.tipoSucursal = tipoSucursal;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Sucursal telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Sucursal status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Sucursal createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Sucursal createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Sucursal modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Sucursal modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Sucursal auditStatus(String auditStatus) {
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
            this.inventarios.forEach(i -> i.setSucursal(null));
        }
        if (inventarios != null) {
            inventarios.forEach(i -> i.setSucursal(this));
        }
        this.inventarios = inventarios;
    }

    public Sucursal inventarios(Set<Inventario> inventarios) {
        this.setInventarios(inventarios);
        return this;
    }

    public Sucursal addInventario(Inventario inventario) {
        this.inventarios.add(inventario);
        inventario.setSucursal(this);
        return this;
    }

    public Sucursal removeInventario(Inventario inventario) {
        this.inventarios.remove(inventario);
        inventario.setSucursal(null);
        return this;
    }

    public Set<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        if (this.empleados != null) {
            this.empleados.forEach(i -> i.setSucursal(null));
        }
        if (empleados != null) {
            empleados.forEach(i -> i.setSucursal(this));
        }
        this.empleados = empleados;
    }

    public Sucursal empleados(Set<Empleado> empleados) {
        this.setEmpleados(empleados);
        return this;
    }

    public Sucursal addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
        empleado.setSucursal(this);
        return this;
    }

    public Sucursal removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
        empleado.setSucursal(null);
        return this;
    }

    public PersonaMoral getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(PersonaMoral personaMoral) {
        this.empresa = personaMoral;
    }

    public Sucursal empresa(PersonaMoral personaMoral) {
        this.setEmpresa(personaMoral);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sucursal)) {
            return false;
        }
        return id != null && id.equals(((Sucursal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sucursal{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipoSucursal='" + getTipoSucursal() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", status='" + getStatus() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
