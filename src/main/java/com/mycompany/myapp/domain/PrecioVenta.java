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
 * A PrecioVenta.
 */
@Entity
@Table(name = "precio_venta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrecioVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "ppu", precision = 21, scale = 2)
    private BigDecimal ppu;

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

    @OneToMany(mappedBy = "precioventa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venta", "precioventa" }, allowSetters = true)
    private Set<RenglonVenta> renglonVentas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "ubicacionInventarios", "resurtirs", "precioVentas", "materialServicios", "perdidas", "listaPedidos", "producto", "sucursal",
        },
        allowSetters = true
    )
    private Inventario inventario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PrecioVenta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public PrecioVenta fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPpu() {
        return this.ppu;
    }

    public PrecioVenta ppu(BigDecimal ppu) {
        this.setPpu(ppu);
        return this;
    }

    public void setPpu(BigDecimal ppu) {
        this.ppu = ppu;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public PrecioVenta createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public PrecioVenta createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public PrecioVenta modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public PrecioVenta modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public PrecioVenta auditStatus(String auditStatus) {
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
            this.renglonVentas.forEach(i -> i.setPrecioventa(null));
        }
        if (renglonVentas != null) {
            renglonVentas.forEach(i -> i.setPrecioventa(this));
        }
        this.renglonVentas = renglonVentas;
    }

    public PrecioVenta renglonVentas(Set<RenglonVenta> renglonVentas) {
        this.setRenglonVentas(renglonVentas);
        return this;
    }

    public PrecioVenta addRenglonVenta(RenglonVenta renglonVenta) {
        this.renglonVentas.add(renglonVenta);
        renglonVenta.setPrecioventa(this);
        return this;
    }

    public PrecioVenta removeRenglonVenta(RenglonVenta renglonVenta) {
        this.renglonVentas.remove(renglonVenta);
        renglonVenta.setPrecioventa(null);
        return this;
    }

    public Inventario getInventario() {
        return this.inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public PrecioVenta inventario(Inventario inventario) {
        this.setInventario(inventario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrecioVenta)) {
            return false;
        }
        return id != null && id.equals(((PrecioVenta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrecioVenta{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", ppu=" + getPpu() +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
