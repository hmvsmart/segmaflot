package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CP.
 */
@Entity
@Table(name = "cp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cp")
    private Integer cp;

    @OneToMany(mappedBy = "cp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "direccions", "cp" }, allowSetters = true)
    private Set<Colonia> colonias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "cPS", "estado" }, allowSetters = true)
    private Ciudad ciudad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CP id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCp() {
        return this.cp;
    }

    public CP cp(Integer cp) {
        this.setCp(cp);
        return this;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Set<Colonia> getColonias() {
        return this.colonias;
    }

    public void setColonias(Set<Colonia> colonias) {
        if (this.colonias != null) {
            this.colonias.forEach(i -> i.setCp(null));
        }
        if (colonias != null) {
            colonias.forEach(i -> i.setCp(this));
        }
        this.colonias = colonias;
    }

    public CP colonias(Set<Colonia> colonias) {
        this.setColonias(colonias);
        return this;
    }

    public CP addColonia(Colonia colonia) {
        this.colonias.add(colonia);
        colonia.setCp(this);
        return this;
    }

    public CP removeColonia(Colonia colonia) {
        this.colonias.remove(colonia);
        colonia.setCp(null);
        return this;
    }

    public Ciudad getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public CP ciudad(Ciudad ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CP)) {
            return false;
        }
        return id != null && id.equals(((CP) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CP{" +
            "id=" + getId() +
            ", cp=" + getCp() +
            "}";
    }
}
