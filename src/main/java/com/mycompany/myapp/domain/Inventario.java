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
 * A Inventario.
 */
@Entity
@Table(name = "inventario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "cantidad_minima_est")
    private Integer cantidadMinimaEst;

    @Column(name = "cantidad_maxima_est")
    private Integer cantidadMaximaEst;

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

    @OneToMany(mappedBy = "inventario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "seccion", "inventario" }, allowSetters = true)
    private Set<UbicacionInventario> ubicacionInventarios = new HashSet<>();

    @OneToMany(mappedBy = "inventario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "renglonResurtirs", "inventario" }, allowSetters = true)
    private Set<Resurtir> resurtirs = new HashSet<>();

    @OneToMany(mappedBy = "inventario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "renglonVentas", "inventario" }, allowSetters = true)
    private Set<PrecioVenta> precioVentas = new HashSet<>();

    @OneToMany(mappedBy = "inventario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inventario", "servicio" }, allowSetters = true)
    private Set<MaterialServicio> materialServicios = new HashSet<>();

    @OneToMany(mappedBy = "inventario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inventario" }, allowSetters = true)
    private Set<Perdida> perdidas = new HashSet<>();

    @OneToMany(mappedBy = "inventario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedido", "inventario" }, allowSetters = true)
    private Set<ListaPedido> listaPedidos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "inventarios" }, allowSetters = true)
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inventarios", "empleados", "empresa" }, allowSetters = true)
    private Sucursal sucursal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inventario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Inventario cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadMinimaEst() {
        return this.cantidadMinimaEst;
    }

    public Inventario cantidadMinimaEst(Integer cantidadMinimaEst) {
        this.setCantidadMinimaEst(cantidadMinimaEst);
        return this;
    }

    public void setCantidadMinimaEst(Integer cantidadMinimaEst) {
        this.cantidadMinimaEst = cantidadMinimaEst;
    }

    public Integer getCantidadMaximaEst() {
        return this.cantidadMaximaEst;
    }

    public Inventario cantidadMaximaEst(Integer cantidadMaximaEst) {
        this.setCantidadMaximaEst(cantidadMaximaEst);
        return this;
    }

    public void setCantidadMaximaEst(Integer cantidadMaximaEst) {
        this.cantidadMaximaEst = cantidadMaximaEst;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Inventario status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Inventario createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Inventario createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Inventario modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Inventario modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Inventario auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<UbicacionInventario> getUbicacionInventarios() {
        return this.ubicacionInventarios;
    }

    public void setUbicacionInventarios(Set<UbicacionInventario> ubicacionInventarios) {
        if (this.ubicacionInventarios != null) {
            this.ubicacionInventarios.forEach(i -> i.setInventario(null));
        }
        if (ubicacionInventarios != null) {
            ubicacionInventarios.forEach(i -> i.setInventario(this));
        }
        this.ubicacionInventarios = ubicacionInventarios;
    }

    public Inventario ubicacionInventarios(Set<UbicacionInventario> ubicacionInventarios) {
        this.setUbicacionInventarios(ubicacionInventarios);
        return this;
    }

    public Inventario addUbicacionInventario(UbicacionInventario ubicacionInventario) {
        this.ubicacionInventarios.add(ubicacionInventario);
        ubicacionInventario.setInventario(this);
        return this;
    }

    public Inventario removeUbicacionInventario(UbicacionInventario ubicacionInventario) {
        this.ubicacionInventarios.remove(ubicacionInventario);
        ubicacionInventario.setInventario(null);
        return this;
    }

    public Set<Resurtir> getResurtirs() {
        return this.resurtirs;
    }

    public void setResurtirs(Set<Resurtir> resurtirs) {
        if (this.resurtirs != null) {
            this.resurtirs.forEach(i -> i.setInventario(null));
        }
        if (resurtirs != null) {
            resurtirs.forEach(i -> i.setInventario(this));
        }
        this.resurtirs = resurtirs;
    }

    public Inventario resurtirs(Set<Resurtir> resurtirs) {
        this.setResurtirs(resurtirs);
        return this;
    }

    public Inventario addResurtir(Resurtir resurtir) {
        this.resurtirs.add(resurtir);
        resurtir.setInventario(this);
        return this;
    }

    public Inventario removeResurtir(Resurtir resurtir) {
        this.resurtirs.remove(resurtir);
        resurtir.setInventario(null);
        return this;
    }

    public Set<PrecioVenta> getPrecioVentas() {
        return this.precioVentas;
    }

    public void setPrecioVentas(Set<PrecioVenta> precioVentas) {
        if (this.precioVentas != null) {
            this.precioVentas.forEach(i -> i.setInventario(null));
        }
        if (precioVentas != null) {
            precioVentas.forEach(i -> i.setInventario(this));
        }
        this.precioVentas = precioVentas;
    }

    public Inventario precioVentas(Set<PrecioVenta> precioVentas) {
        this.setPrecioVentas(precioVentas);
        return this;
    }

    public Inventario addPrecioVenta(PrecioVenta precioVenta) {
        this.precioVentas.add(precioVenta);
        precioVenta.setInventario(this);
        return this;
    }

    public Inventario removePrecioVenta(PrecioVenta precioVenta) {
        this.precioVentas.remove(precioVenta);
        precioVenta.setInventario(null);
        return this;
    }

    public Set<MaterialServicio> getMaterialServicios() {
        return this.materialServicios;
    }

    public void setMaterialServicios(Set<MaterialServicio> materialServicios) {
        if (this.materialServicios != null) {
            this.materialServicios.forEach(i -> i.setInventario(null));
        }
        if (materialServicios != null) {
            materialServicios.forEach(i -> i.setInventario(this));
        }
        this.materialServicios = materialServicios;
    }

    public Inventario materialServicios(Set<MaterialServicio> materialServicios) {
        this.setMaterialServicios(materialServicios);
        return this;
    }

    public Inventario addMaterialServicio(MaterialServicio materialServicio) {
        this.materialServicios.add(materialServicio);
        materialServicio.setInventario(this);
        return this;
    }

    public Inventario removeMaterialServicio(MaterialServicio materialServicio) {
        this.materialServicios.remove(materialServicio);
        materialServicio.setInventario(null);
        return this;
    }

    public Set<Perdida> getPerdidas() {
        return this.perdidas;
    }

    public void setPerdidas(Set<Perdida> perdidas) {
        if (this.perdidas != null) {
            this.perdidas.forEach(i -> i.setInventario(null));
        }
        if (perdidas != null) {
            perdidas.forEach(i -> i.setInventario(this));
        }
        this.perdidas = perdidas;
    }

    public Inventario perdidas(Set<Perdida> perdidas) {
        this.setPerdidas(perdidas);
        return this;
    }

    public Inventario addPerdida(Perdida perdida) {
        this.perdidas.add(perdida);
        perdida.setInventario(this);
        return this;
    }

    public Inventario removePerdida(Perdida perdida) {
        this.perdidas.remove(perdida);
        perdida.setInventario(null);
        return this;
    }

    public Set<ListaPedido> getListaPedidos() {
        return this.listaPedidos;
    }

    public void setListaPedidos(Set<ListaPedido> listaPedidos) {
        if (this.listaPedidos != null) {
            this.listaPedidos.forEach(i -> i.setInventario(null));
        }
        if (listaPedidos != null) {
            listaPedidos.forEach(i -> i.setInventario(this));
        }
        this.listaPedidos = listaPedidos;
    }

    public Inventario listaPedidos(Set<ListaPedido> listaPedidos) {
        this.setListaPedidos(listaPedidos);
        return this;
    }

    public Inventario addListaPedido(ListaPedido listaPedido) {
        this.listaPedidos.add(listaPedido);
        listaPedido.setInventario(this);
        return this;
    }

    public Inventario removeListaPedido(ListaPedido listaPedido) {
        this.listaPedidos.remove(listaPedido);
        listaPedido.setInventario(null);
        return this;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Inventario producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public Sucursal getSucursal() {
        return this.sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Inventario sucursal(Sucursal sucursal) {
        this.setSucursal(sucursal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventario)) {
            return false;
        }
        return id != null && id.equals(((Inventario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventario{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", cantidadMinimaEst=" + getCantidadMinimaEst() +
            ", cantidadMaximaEst=" + getCantidadMaximaEst() +
            ", status='" + getStatus() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
