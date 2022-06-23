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
 * A Resurtir.
 */
@Entity
@Table(name = "resurtir")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resurtir implements Serializable {

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

    @OneToMany(mappedBy = "resurtir")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resurtir" }, allowSetters = true)
    private Set<RenglonResurtir> renglonResurtirs = new HashSet<>();

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

    public Resurtir id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Resurtir fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Resurtir total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Resurtir createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Resurtir createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Resurtir modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Resurtir modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Resurtir auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<RenglonResurtir> getRenglonResurtirs() {
        return this.renglonResurtirs;
    }

    public void setRenglonResurtirs(Set<RenglonResurtir> renglonResurtirs) {
        if (this.renglonResurtirs != null) {
            this.renglonResurtirs.forEach(i -> i.setResurtir(null));
        }
        if (renglonResurtirs != null) {
            renglonResurtirs.forEach(i -> i.setResurtir(this));
        }
        this.renglonResurtirs = renglonResurtirs;
    }

    public Resurtir renglonResurtirs(Set<RenglonResurtir> renglonResurtirs) {
        this.setRenglonResurtirs(renglonResurtirs);
        return this;
    }

    public Resurtir addRenglonResurtir(RenglonResurtir renglonResurtir) {
        this.renglonResurtirs.add(renglonResurtir);
        renglonResurtir.setResurtir(this);
        return this;
    }

    public Resurtir removeRenglonResurtir(RenglonResurtir renglonResurtir) {
        this.renglonResurtirs.remove(renglonResurtir);
        renglonResurtir.setResurtir(null);
        return this;
    }

    public Inventario getInventario() {
        return this.inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Resurtir inventario(Inventario inventario) {
        this.setInventario(inventario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resurtir)) {
            return false;
        }
        return id != null && id.equals(((Resurtir) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resurtir{" +
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
