package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

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

    @OneToMany(mappedBy = "venta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "precioventa" }, allowSetters = true)
    private Set<RenglonVenta> renglonVentas = new HashSet<>();

    @OneToMany(mappedBy = "venta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "pedido" }, allowSetters = true)
    private Set<VentaPedido> ventaPedidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Venta fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Venta total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Venta createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Venta createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Venta modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Venta modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Venta auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<RenglonVenta> getRenglonVentas() {
        return this.renglonVentas;
    }

    public void setRenglonVentas(Set<RenglonVenta> renglonVentas) {
        if (this.renglonVentas != null) {
            this.renglonVentas.forEach(i -> i.setVenta(null));
        }
        if (renglonVentas != null) {
            renglonVentas.forEach(i -> i.setVenta(this));
        }
        this.renglonVentas = renglonVentas;
    }

    public Venta renglonVentas(Set<RenglonVenta> renglonVentas) {
        this.setRenglonVentas(renglonVentas);
        return this;
    }

    public Venta addRenglonVenta(RenglonVenta renglonVenta) {
        this.renglonVentas.add(renglonVenta);
        renglonVenta.setVenta(this);
        return this;
    }

    public Venta removeRenglonVenta(RenglonVenta renglonVenta) {
        this.renglonVentas.remove(renglonVenta);
        renglonVenta.setVenta(null);
        return this;
    }

    public Set<VentaPedido> getVentaPedidos() {
        return this.ventaPedidos;
    }

    public void setVentaPedidos(Set<VentaPedido> ventaPedidos) {
        if (this.ventaPedidos != null) {
            this.ventaPedidos.forEach(i -> i.setVenta(null));
        }
        if (ventaPedidos != null) {
            ventaPedidos.forEach(i -> i.setVenta(this));
        }
        this.ventaPedidos = ventaPedidos;
    }

    public Venta ventaPedidos(Set<VentaPedido> ventaPedidos) {
        this.setVentaPedidos(ventaPedidos);
        return this;
    }

    public Venta addVentaPedido(VentaPedido ventaPedido) {
        this.ventaPedidos.add(ventaPedido);
        ventaPedido.setVenta(this);
        return this;
    }

    public Venta removeVentaPedido(VentaPedido ventaPedido) {
        this.ventaPedidos.remove(ventaPedido);
        ventaPedido.setVenta(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return id != null && id.equals(((Venta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", total=" + getTotal() +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
