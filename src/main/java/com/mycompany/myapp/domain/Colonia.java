package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Colonia.
 */
@Entity
@Table(name = "colonia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Colonia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "colonia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "direccionPersonas", "viajes", "colonia" }, allowSetters = true)
    private Set<Direccion> direccions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "colonias", "ciudad" }, allowSetters = true)
    private CP cp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Colonia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Colonia nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Direccion> getDireccions() {
        return this.direccions;
    }

    public void setDireccions(Set<Direccion> direccions) {
        if (this.direccions != null) {
            this.direccions.forEach(i -> i.setColonia(null));
        }
        if (direccions != null) {
            direccions.forEach(i -> i.setColonia(this));
        }
        this.direccions = direccions;
    }

    public Colonia direccions(Set<Direccion> direccions) {
        this.setDireccions(direccions);
        return this;
    }

    public Colonia addDireccion(Direccion direccion) {
        this.direccions.add(direccion);
        direccion.setColonia(this);
        return this;
    }

    public Colonia removeDireccion(Direccion direccion) {
        this.direccions.remove(direccion);
        direccion.setColonia(null);
        return this;
    }

    public CP getCp() {
        return this.cp;
    }

    public void setCp(CP cP) {
        this.cp = cP;
    }

    public Colonia cp(CP cP) {
        this.setCp(cP);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colonia)) {
            return false;
        }
        return id != null && id.equals(((Colonia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colonia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
