package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estado.
 */
@Entity
@Table(name = "estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cPS", "estado" }, allowSetters = true)
    private Set<Ciudad> ciudads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Estado nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Ciudad> getCiudads() {
        return this.ciudads;
    }

    public void setCiudads(Set<Ciudad> ciudads) {
        if (this.ciudads != null) {
            this.ciudads.forEach(i -> i.setEstado(null));
        }
        if (ciudads != null) {
            ciudads.forEach(i -> i.setEstado(this));
        }
        this.ciudads = ciudads;
    }

    public Estado ciudads(Set<Ciudad> ciudads) {
        this.setCiudads(ciudads);
        return this;
    }

    public Estado addCiudad(Ciudad ciudad) {
        this.ciudads.add(ciudad);
        ciudad.setEstado(this);
        return this;
    }

    public Estado removeCiudad(Ciudad ciudad) {
        this.ciudads.remove(ciudad);
        ciudad.setEstado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estado)) {
            return false;
        }
        return id != null && id.equals(((Estado) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
