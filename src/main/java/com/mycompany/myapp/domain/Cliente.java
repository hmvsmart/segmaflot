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
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Lob
    @Column(name = "observaciones")
    private byte[] observaciones;

    @Column(name = "observaciones_content_type")
    private String observacionesContentType;

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

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cliente" }, allowSetters = true)
    private Set<VehiculoCliente> vehiculoClientes = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listaServicios", "cliente" }, allowSetters = true)
    private Set<UnidadServicio> unidadServicios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "personaMorals", "direccionPersonas", "empleados", "clientes", "contactos" }, allowSetters = true)
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Cliente fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public byte[] getObservaciones() {
        return this.observaciones;
    }

    public Cliente observaciones(byte[] observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(byte[] observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservacionesContentType() {
        return this.observacionesContentType;
    }

    public Cliente observacionesContentType(String observacionesContentType) {
        this.observacionesContentType = observacionesContentType;
        return this;
    }

    public void setObservacionesContentType(String observacionesContentType) {
        this.observacionesContentType = observacionesContentType;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Cliente createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Cliente createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Cliente modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Cliente modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Cliente auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<VehiculoCliente> getVehiculoClientes() {
        return this.vehiculoClientes;
    }

    public void setVehiculoClientes(Set<VehiculoCliente> vehiculoClientes) {
        if (this.vehiculoClientes != null) {
            this.vehiculoClientes.forEach(i -> i.setCliente(null));
        }
        if (vehiculoClientes != null) {
            vehiculoClientes.forEach(i -> i.setCliente(this));
        }
        this.vehiculoClientes = vehiculoClientes;
    }

    public Cliente vehiculoClientes(Set<VehiculoCliente> vehiculoClientes) {
        this.setVehiculoClientes(vehiculoClientes);
        return this;
    }

    public Cliente addVehiculoCliente(VehiculoCliente vehiculoCliente) {
        this.vehiculoClientes.add(vehiculoCliente);
        vehiculoCliente.setCliente(this);
        return this;
    }

    public Cliente removeVehiculoCliente(VehiculoCliente vehiculoCliente) {
        this.vehiculoClientes.remove(vehiculoCliente);
        vehiculoCliente.setCliente(null);
        return this;
    }

    public Set<UnidadServicio> getUnidadServicios() {
        return this.unidadServicios;
    }

    public void setUnidadServicios(Set<UnidadServicio> unidadServicios) {
        if (this.unidadServicios != null) {
            this.unidadServicios.forEach(i -> i.setCliente(null));
        }
        if (unidadServicios != null) {
            unidadServicios.forEach(i -> i.setCliente(this));
        }
        this.unidadServicios = unidadServicios;
    }

    public Cliente unidadServicios(Set<UnidadServicio> unidadServicios) {
        this.setUnidadServicios(unidadServicios);
        return this;
    }

    public Cliente addUnidadServicio(UnidadServicio unidadServicio) {
        this.unidadServicios.add(unidadServicio);
        unidadServicio.setCliente(this);
        return this;
    }

    public Cliente removeUnidadServicio(UnidadServicio unidadServicio) {
        this.unidadServicios.remove(unidadServicio);
        unidadServicio.setCliente(null);
        return this;
    }

    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Cliente persona(Persona persona) {
        this.setPersona(persona);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", observacionesContentType='" + getObservacionesContentType() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
