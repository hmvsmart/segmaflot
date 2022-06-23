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
 * A Aditamento.
 */
@Entity
@Table(name = "aditamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aditamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Lob
    @Column(name = "descripcion")
    private byte[] descripcion;

    @Column(name = "descripcion_content_type")
    private String descripcionContentType;

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

    @OneToMany(mappedBy = "aditamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "aditamento", "unidad" }, allowSetters = true)
    private Set<AditamentoUnidad> aditamentoUnidads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aditamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Aditamento nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Aditamento numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public byte[] getDescripcion() {
        return this.descripcion;
    }

    public Aditamento descripcion(byte[] descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(byte[] descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionContentType() {
        return this.descripcionContentType;
    }

    public Aditamento descripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
        return this;
    }

    public void setDescripcionContentType(String descripcionContentType) {
        this.descripcionContentType = descripcionContentType;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Aditamento createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Aditamento createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Aditamento modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Aditamento modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Aditamento auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<AditamentoUnidad> getAditamentoUnidads() {
        return this.aditamentoUnidads;
    }

    public void setAditamentoUnidads(Set<AditamentoUnidad> aditamentoUnidads) {
        if (this.aditamentoUnidads != null) {
            this.aditamentoUnidads.forEach(i -> i.setAditamento(null));
        }
        if (aditamentoUnidads != null) {
            aditamentoUnidads.forEach(i -> i.setAditamento(this));
        }
        this.aditamentoUnidads = aditamentoUnidads;
    }

    public Aditamento aditamentoUnidads(Set<AditamentoUnidad> aditamentoUnidads) {
        this.setAditamentoUnidads(aditamentoUnidads);
        return this;
    }

    public Aditamento addAditamentoUnidad(AditamentoUnidad aditamentoUnidad) {
        this.aditamentoUnidads.add(aditamentoUnidad);
        aditamentoUnidad.setAditamento(this);
        return this;
    }

    public Aditamento removeAditamentoUnidad(AditamentoUnidad aditamentoUnidad) {
        this.aditamentoUnidads.remove(aditamentoUnidad);
        aditamentoUnidad.setAditamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aditamento)) {
            return false;
        }
        return id != null && id.equals(((Aditamento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aditamento{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", descripcionContentType='" + getDescripcionContentType() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
