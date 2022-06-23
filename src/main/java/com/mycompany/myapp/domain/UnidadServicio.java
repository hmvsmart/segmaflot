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
 * A UnidadServicio.
 */
@Entity
@Table(name = "unidad_servicio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UnidadServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "total_estimado", precision = 21, scale = 2)
    private BigDecimal totalEstimado;

    @Column(name = "total_real", precision = 21, scale = 2)
    private BigDecimal totalReal;

    @Lob
    @Column(name = "observaciones_generales")
    private byte[] observacionesGenerales;

    @Column(name = "observaciones_generales_content_type")
    private String observacionesGeneralesContentType;

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

    @OneToMany(mappedBy = "unidadServicio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicio", "unidadServicio" }, allowSetters = true)
    private Set<ListaServicio> listaServicios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "vehiculoClientes", "unidadServicios", "persona" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UnidadServicio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public UnidadServicio fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalEstimado() {
        return this.totalEstimado;
    }

    public UnidadServicio totalEstimado(BigDecimal totalEstimado) {
        this.setTotalEstimado(totalEstimado);
        return this;
    }

    public void setTotalEstimado(BigDecimal totalEstimado) {
        this.totalEstimado = totalEstimado;
    }

    public BigDecimal getTotalReal() {
        return this.totalReal;
    }

    public UnidadServicio totalReal(BigDecimal totalReal) {
        this.setTotalReal(totalReal);
        return this;
    }

    public void setTotalReal(BigDecimal totalReal) {
        this.totalReal = totalReal;
    }

    public byte[] getObservacionesGenerales() {
        return this.observacionesGenerales;
    }

    public UnidadServicio observacionesGenerales(byte[] observacionesGenerales) {
        this.setObservacionesGenerales(observacionesGenerales);
        return this;
    }

    public void setObservacionesGenerales(byte[] observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
    }

    public String getObservacionesGeneralesContentType() {
        return this.observacionesGeneralesContentType;
    }

    public UnidadServicio observacionesGeneralesContentType(String observacionesGeneralesContentType) {
        this.observacionesGeneralesContentType = observacionesGeneralesContentType;
        return this;
    }

    public void setObservacionesGeneralesContentType(String observacionesGeneralesContentType) {
        this.observacionesGeneralesContentType = observacionesGeneralesContentType;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public UnidadServicio createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public UnidadServicio createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public UnidadServicio modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public UnidadServicio modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public UnidadServicio auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<ListaServicio> getListaServicios() {
        return this.listaServicios;
    }

    public void setListaServicios(Set<ListaServicio> listaServicios) {
        if (this.listaServicios != null) {
            this.listaServicios.forEach(i -> i.setUnidadServicio(null));
        }
        if (listaServicios != null) {
            listaServicios.forEach(i -> i.setUnidadServicio(this));
        }
        this.listaServicios = listaServicios;
    }

    public UnidadServicio listaServicios(Set<ListaServicio> listaServicios) {
        this.setListaServicios(listaServicios);
        return this;
    }

    public UnidadServicio addListaServicio(ListaServicio listaServicio) {
        this.listaServicios.add(listaServicio);
        listaServicio.setUnidadServicio(this);
        return this;
    }

    public UnidadServicio removeListaServicio(ListaServicio listaServicio) {
        this.listaServicios.remove(listaServicio);
        listaServicio.setUnidadServicio(null);
        return this;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public UnidadServicio cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadServicio)) {
            return false;
        }
        return id != null && id.equals(((UnidadServicio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnidadServicio{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", totalEstimado=" + getTotalEstimado() +
            ", totalReal=" + getTotalReal() +
            ", observacionesGenerales='" + getObservacionesGenerales() + "'" +
            ", observacionesGeneralesContentType='" + getObservacionesGeneralesContentType() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
