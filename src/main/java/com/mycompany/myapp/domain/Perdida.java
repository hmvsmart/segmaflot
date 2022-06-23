package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Perdida.
 */
@Entity
@Table(name = "perdida")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Perdida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Lob
    @Column(name = "observaciones")
    private byte[] observaciones;

    @Column(name = "observaciones_content_type")
    private String observacionesContentType;

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

    public Perdida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return this.fecha;
    }

    public Perdida fecha(Instant fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Perdida cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public byte[] getObservaciones() {
        return this.observaciones;
    }

    public Perdida observaciones(byte[] observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(byte[] observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservacionesContentType() {
        return this.observacionesContentType;
    }

    public Perdida observacionesContentType(String observacionesContentType) {
        this.observacionesContentType = observacionesContentType;
        return this;
    }

    public void setObservacionesContentType(String observacionesContentType) {
        this.observacionesContentType = observacionesContentType;
    }

    public Inventario getInventario() {
        return this.inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Perdida inventario(Inventario inventario) {
        this.setInventario(inventario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Perdida)) {
            return false;
        }
        return id != null && id.equals(((Perdida) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Perdida{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", cantidad=" + getCantidad() +
            ", observaciones='" + getObservaciones() + "'" +
            ", observacionesContentType='" + getObservacionesContentType() + "'" +
            "}";
    }
}
