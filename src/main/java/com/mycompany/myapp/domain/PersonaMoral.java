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
 * A PersonaMoral.
 */
@Entity
@Table(name = "persona_moral")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonaMoral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "rfc")
    private String rfc;

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

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inventarios", "empleados", "empresa" }, allowSetters = true)
    private Set<Sucursal> sucursals = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<Reporte> reportes = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listaPedidos", "ventaPedidos", "empresa" }, allowSetters = true)
    private Set<Pedido> pedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "personaMorals", "direccionPersonas", "empleados", "clientes", "contactos" }, allowSetters = true)
    private Persona persona;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonaMoral id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public PersonaMoral razonSocial(String razonSocial) {
        this.setRazonSocial(razonSocial);
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return this.rfc;
    }

    public PersonaMoral rfc(String rfc) {
        this.setRfc(rfc);
        return this;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public PersonaMoral createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public PersonaMoral createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public PersonaMoral modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public PersonaMoral modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public PersonaMoral auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<Sucursal> getSucursals() {
        return this.sucursals;
    }

    public void setSucursals(Set<Sucursal> sucursals) {
        if (this.sucursals != null) {
            this.sucursals.forEach(i -> i.setEmpresa(null));
        }
        if (sucursals != null) {
            sucursals.forEach(i -> i.setEmpresa(this));
        }
        this.sucursals = sucursals;
    }

    public PersonaMoral sucursals(Set<Sucursal> sucursals) {
        this.setSucursals(sucursals);
        return this;
    }

    public PersonaMoral addSucursal(Sucursal sucursal) {
        this.sucursals.add(sucursal);
        sucursal.setEmpresa(this);
        return this;
    }

    public PersonaMoral removeSucursal(Sucursal sucursal) {
        this.sucursals.remove(sucursal);
        sucursal.setEmpresa(null);
        return this;
    }

    public Set<Reporte> getReportes() {
        return this.reportes;
    }

    public void setReportes(Set<Reporte> reportes) {
        if (this.reportes != null) {
            this.reportes.forEach(i -> i.setEmpresa(null));
        }
        if (reportes != null) {
            reportes.forEach(i -> i.setEmpresa(this));
        }
        this.reportes = reportes;
    }

    public PersonaMoral reportes(Set<Reporte> reportes) {
        this.setReportes(reportes);
        return this;
    }

    public PersonaMoral addReporte(Reporte reporte) {
        this.reportes.add(reporte);
        reporte.setEmpresa(this);
        return this;
    }

    public PersonaMoral removeReporte(Reporte reporte) {
        this.reportes.remove(reporte);
        reporte.setEmpresa(null);
        return this;
    }

    public Set<Pedido> getPedidos() {
        return this.pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        if (this.pedidos != null) {
            this.pedidos.forEach(i -> i.setEmpresa(null));
        }
        if (pedidos != null) {
            pedidos.forEach(i -> i.setEmpresa(this));
        }
        this.pedidos = pedidos;
    }

    public PersonaMoral pedidos(Set<Pedido> pedidos) {
        this.setPedidos(pedidos);
        return this;
    }

    public PersonaMoral addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setEmpresa(this);
        return this;
    }

    public PersonaMoral removePedido(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setEmpresa(null);
        return this;
    }

    public Persona getPersona() {
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public PersonaMoral persona(Persona persona) {
        this.setPersona(persona);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonaMoral)) {
            return false;
        }
        return id != null && id.equals(((PersonaMoral) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonaMoral{" +
            "id=" + getId() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", rfc='" + getRfc() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
