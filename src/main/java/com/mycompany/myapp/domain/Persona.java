package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "persona")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sucursals", "reportes", "pedidos", "persona" }, allowSetters = true)
    private Set<PersonaMoral> personaMorals = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "direccion", "persona" }, allowSetters = true)
    private Set<DireccionPersona> direccionPersonas = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "empleadoTipoVehiculos", "experienciaHabilidads", "licenciaManejos", "horarios", "operadorUnidads", "persona", "sucursal",
        },
        allowSetters = true
    )
    private Set<Empleado> empleados = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehiculoClientes", "unidadServicios", "persona" }, allowSetters = true)
    private Set<Cliente> clientes = new HashSet<>();

    @OneToMany(mappedBy = "persona")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoContacto", "persona" }, allowSetters = true)
    private Set<Contacto> contactos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Persona id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PersonaMoral> getPersonaMorals() {
        return this.personaMorals;
    }

    public void setPersonaMorals(Set<PersonaMoral> personaMorals) {
        if (this.personaMorals != null) {
            this.personaMorals.forEach(i -> i.setPersona(null));
        }
        if (personaMorals != null) {
            personaMorals.forEach(i -> i.setPersona(this));
        }
        this.personaMorals = personaMorals;
    }

    public Persona personaMorals(Set<PersonaMoral> personaMorals) {
        this.setPersonaMorals(personaMorals);
        return this;
    }

    public Persona addPersonaMoral(PersonaMoral personaMoral) {
        this.personaMorals.add(personaMoral);
        personaMoral.setPersona(this);
        return this;
    }

    public Persona removePersonaMoral(PersonaMoral personaMoral) {
        this.personaMorals.remove(personaMoral);
        personaMoral.setPersona(null);
        return this;
    }

    public Set<DireccionPersona> getDireccionPersonas() {
        return this.direccionPersonas;
    }

    public void setDireccionPersonas(Set<DireccionPersona> direccionPersonas) {
        if (this.direccionPersonas != null) {
            this.direccionPersonas.forEach(i -> i.setPersona(null));
        }
        if (direccionPersonas != null) {
            direccionPersonas.forEach(i -> i.setPersona(this));
        }
        this.direccionPersonas = direccionPersonas;
    }

    public Persona direccionPersonas(Set<DireccionPersona> direccionPersonas) {
        this.setDireccionPersonas(direccionPersonas);
        return this;
    }

    public Persona addDireccionPersona(DireccionPersona direccionPersona) {
        this.direccionPersonas.add(direccionPersona);
        direccionPersona.setPersona(this);
        return this;
    }

    public Persona removeDireccionPersona(DireccionPersona direccionPersona) {
        this.direccionPersonas.remove(direccionPersona);
        direccionPersona.setPersona(null);
        return this;
    }

    public Set<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        if (this.empleados != null) {
            this.empleados.forEach(i -> i.setPersona(null));
        }
        if (empleados != null) {
            empleados.forEach(i -> i.setPersona(this));
        }
        this.empleados = empleados;
    }

    public Persona empleados(Set<Empleado> empleados) {
        this.setEmpleados(empleados);
        return this;
    }

    public Persona addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
        empleado.setPersona(this);
        return this;
    }

    public Persona removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
        empleado.setPersona(null);
        return this;
    }

    public Set<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        if (this.clientes != null) {
            this.clientes.forEach(i -> i.setPersona(null));
        }
        if (clientes != null) {
            clientes.forEach(i -> i.setPersona(this));
        }
        this.clientes = clientes;
    }

    public Persona clientes(Set<Cliente> clientes) {
        this.setClientes(clientes);
        return this;
    }

    public Persona addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setPersona(this);
        return this;
    }

    public Persona removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setPersona(null);
        return this;
    }

    public Set<Contacto> getContactos() {
        return this.contactos;
    }

    public void setContactos(Set<Contacto> contactos) {
        if (this.contactos != null) {
            this.contactos.forEach(i -> i.setPersona(null));
        }
        if (contactos != null) {
            contactos.forEach(i -> i.setPersona(this));
        }
        this.contactos = contactos;
    }

    public Persona contactos(Set<Contacto> contactos) {
        this.setContactos(contactos);
        return this;
    }

    public Persona addContacto(Contacto contacto) {
        this.contactos.add(contacto);
        contacto.setPersona(this);
        return this;
    }

    public Persona removeContacto(Contacto contacto) {
        this.contactos.remove(contacto);
        contacto.setPersona(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persona)) {
            return false;
        }
        return id != null && id.equals(((Persona) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            "}";
    }
}
