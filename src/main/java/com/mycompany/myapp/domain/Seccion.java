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
 * A Seccion.
 */
@Entity
@Table(name = "seccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

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

    @OneToMany(mappedBy = "seccion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "seccion", "inventario" }, allowSetters = true)
    private Set<UbicacionInventario> ubicacionInventarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "seccions", "estante" }, allowSetters = true)
    private Nivel nivel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Seccion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Seccion nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Seccion createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Seccion createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Seccion modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Seccion modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Seccion auditStatus(String auditStatus) {
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
            this.ubicacionInventarios.forEach(i -> i.setSeccion(null));
        }
        if (ubicacionInventarios != null) {
            ubicacionInventarios.forEach(i -> i.setSeccion(this));
        }
        this.ubicacionInventarios = ubicacionInventarios;
    }

    public Seccion ubicacionInventarios(Set<UbicacionInventario> ubicacionInventarios) {
        this.setUbicacionInventarios(ubicacionInventarios);
        return this;
    }

    public Seccion addUbicacionInventario(UbicacionInventario ubicacionInventario) {
        this.ubicacionInventarios.add(ubicacionInventario);
        ubicacionInventario.setSeccion(this);
        return this;
    }

    public Seccion removeUbicacionInventario(UbicacionInventario ubicacionInventario) {
        this.ubicacionInventarios.remove(ubicacionInventario);
        ubicacionInventario.setSeccion(null);
        return this;
    }

    public Nivel getNivel() {
        return this.nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Seccion nivel(Nivel nivel) {
        this.setNivel(nivel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seccion)) {
            return false;
        }
        return id != null && id.equals(((Seccion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seccion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
