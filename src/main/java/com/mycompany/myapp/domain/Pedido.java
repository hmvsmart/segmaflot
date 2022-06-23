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
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

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

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedido", "inventario" }, allowSetters = true)
    private Set<ListaPedido> listaPedidos = new HashSet<>();

    @OneToMany(mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "pedido" }, allowSetters = true)
    private Set<VentaPedido> ventaPedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "sucursals", "reportes", "pedidos", "persona" }, allowSetters = true)
    private PersonaMoral empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pedido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Pedido fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Pedido createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Pedido createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Pedido modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Pedido modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Pedido auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<ListaPedido> getListaPedidos() {
        return this.listaPedidos;
    }

    public void setListaPedidos(Set<ListaPedido> listaPedidos) {
        if (this.listaPedidos != null) {
            this.listaPedidos.forEach(i -> i.setPedido(null));
        }
        if (listaPedidos != null) {
            listaPedidos.forEach(i -> i.setPedido(this));
        }
        this.listaPedidos = listaPedidos;
    }

    public Pedido listaPedidos(Set<ListaPedido> listaPedidos) {
        this.setListaPedidos(listaPedidos);
        return this;
    }

    public Pedido addListaPedido(ListaPedido listaPedido) {
        this.listaPedidos.add(listaPedido);
        listaPedido.setPedido(this);
        return this;
    }

    public Pedido removeListaPedido(ListaPedido listaPedido) {
        this.listaPedidos.remove(listaPedido);
        listaPedido.setPedido(null);
        return this;
    }

    public Set<VentaPedido> getVentaPedidos() {
        return this.ventaPedidos;
    }

    public void setVentaPedidos(Set<VentaPedido> ventaPedidos) {
        if (this.ventaPedidos != null) {
            this.ventaPedidos.forEach(i -> i.setPedido(null));
        }
        if (ventaPedidos != null) {
            ventaPedidos.forEach(i -> i.setPedido(this));
        }
        this.ventaPedidos = ventaPedidos;
    }

    public Pedido ventaPedidos(Set<VentaPedido> ventaPedidos) {
        this.setVentaPedidos(ventaPedidos);
        return this;
    }

    public Pedido addVentaPedido(VentaPedido ventaPedido) {
        this.ventaPedidos.add(ventaPedido);
        ventaPedido.setPedido(this);
        return this;
    }

    public Pedido removeVentaPedido(VentaPedido ventaPedido) {
        this.ventaPedidos.remove(ventaPedido);
        ventaPedido.setPedido(null);
        return this;
    }

    public PersonaMoral getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(PersonaMoral personaMoral) {
        this.empresa = personaMoral;
    }

    public Pedido empresa(PersonaMoral personaMoral) {
        this.setEmpresa(personaMoral);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
