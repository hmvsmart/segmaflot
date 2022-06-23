package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ciudad.
 */
@Entity
@Table(name = "ciudad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "ciudad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "colonias", "ciudad" }, allowSetters = true)
    private Set<CP> cPS = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ciudads" }, allowSetters = true)
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ciudad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Ciudad nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<CP> getCPS() {
        return this.cPS;
    }

    public void setCPS(Set<CP> cPS) {
        if (this.cPS != null) {
            this.cPS.forEach(i -> i.setCiudad(null));
        }
        if (cPS != null) {
            cPS.forEach(i -> i.setCiudad(this));
        }
        this.cPS = cPS;
    }

    public Ciudad cPS(Set<CP> cPS) {
        this.setCPS(cPS);
        return this;
    }

    public Ciudad addCP(CP cP) {
        this.cPS.add(cP);
        cP.setCiudad(this);
        return this;
    }

    public Ciudad removeCP(CP cP) {
        this.cPS.remove(cP);
        cP.setCiudad(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Ciudad estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ciudad)) {
            return false;
        }
        return id != null && id.equals(((Ciudad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ciudad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
