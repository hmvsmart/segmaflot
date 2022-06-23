package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Viaje.
 */
@Entity
@Table(name = "viaje")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "hora_embarque")
    private Instant horaEmbarque;

    @Column(name = "tipo_carga")
    private String tipoCarga;

    @Column(name = "peso_neto")
    private Double pesoNeto;

    @Lob
    @Column(name = "comentarios")
    private byte[] comentarios;

    @Column(name = "comentarios_content_type")
    private String comentariosContentType;

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

    @OneToMany(mappedBy = "viaje")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "viaje" }, allowSetters = true)
    private Set<Itinerario> itinerarios = new HashSet<>();

    @OneToMany(mappedBy = "viaje")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "operadorUnidads", "viaje", "unidad" }, allowSetters = true)
    private Set<UnidadViaje> unidadViajes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "direccionPersonas", "viajes", "colonia" }, allowSetters = true)
    private Direccion origen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Viaje id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Viaje fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Viaje fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public Viaje fechaFin(LocalDate fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Instant getHoraEmbarque() {
        return this.horaEmbarque;
    }

    public Viaje horaEmbarque(Instant horaEmbarque) {
        this.setHoraEmbarque(horaEmbarque);
        return this;
    }

    public void setHoraEmbarque(Instant horaEmbarque) {
        this.horaEmbarque = horaEmbarque;
    }

    public String getTipoCarga() {
        return this.tipoCarga;
    }

    public Viaje tipoCarga(String tipoCarga) {
        this.setTipoCarga(tipoCarga);
        return this;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public Double getPesoNeto() {
        return this.pesoNeto;
    }

    public Viaje pesoNeto(Double pesoNeto) {
        this.setPesoNeto(pesoNeto);
        return this;
    }

    public void setPesoNeto(Double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public byte[] getComentarios() {
        return this.comentarios;
    }

    public Viaje comentarios(byte[] comentarios) {
        this.setComentarios(comentarios);
        return this;
    }

    public void setComentarios(byte[] comentarios) {
        this.comentarios = comentarios;
    }

    public String getComentariosContentType() {
        return this.comentariosContentType;
    }

    public Viaje comentariosContentType(String comentariosContentType) {
        this.comentariosContentType = comentariosContentType;
        return this;
    }

    public void setComentariosContentType(String comentariosContentType) {
        this.comentariosContentType = comentariosContentType;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Viaje status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public Viaje createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Viaje createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public Viaje modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public Viaje modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public Viaje auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Set<Itinerario> getItinerarios() {
        return this.itinerarios;
    }

    public void setItinerarios(Set<Itinerario> itinerarios) {
        if (this.itinerarios != null) {
            this.itinerarios.forEach(i -> i.setViaje(null));
        }
        if (itinerarios != null) {
            itinerarios.forEach(i -> i.setViaje(this));
        }
        this.itinerarios = itinerarios;
    }

    public Viaje itinerarios(Set<Itinerario> itinerarios) {
        this.setItinerarios(itinerarios);
        return this;
    }

    public Viaje addItinerario(Itinerario itinerario) {
        this.itinerarios.add(itinerario);
        itinerario.setViaje(this);
        return this;
    }

    public Viaje removeItinerario(Itinerario itinerario) {
        this.itinerarios.remove(itinerario);
        itinerario.setViaje(null);
        return this;
    }

    public Set<UnidadViaje> getUnidadViajes() {
        return this.unidadViajes;
    }

    public void setUnidadViajes(Set<UnidadViaje> unidadViajes) {
        if (this.unidadViajes != null) {
            this.unidadViajes.forEach(i -> i.setViaje(null));
        }
        if (unidadViajes != null) {
            unidadViajes.forEach(i -> i.setViaje(this));
        }
        this.unidadViajes = unidadViajes;
    }

    public Viaje unidadViajes(Set<UnidadViaje> unidadViajes) {
        this.setUnidadViajes(unidadViajes);
        return this;
    }

    public Viaje addUnidadViaje(UnidadViaje unidadViaje) {
        this.unidadViajes.add(unidadViaje);
        unidadViaje.setViaje(this);
        return this;
    }

    public Viaje removeUnidadViaje(UnidadViaje unidadViaje) {
        this.unidadViajes.remove(unidadViaje);
        unidadViaje.setViaje(null);
        return this;
    }

    public Direccion getOrigen() {
        return this.origen;
    }

    public void setOrigen(Direccion direccion) {
        this.origen = direccion;
    }

    public Viaje origen(Direccion direccion) {
        this.setOrigen(direccion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Viaje)) {
            return false;
        }
        return id != null && id.equals(((Viaje) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Viaje{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", horaEmbarque='" + getHoraEmbarque() + "'" +
            ", tipoCarga='" + getTipoCarga() + "'" +
            ", pesoNeto=" + getPesoNeto() +
            ", comentarios='" + getComentarios() + "'" +
            ", comentariosContentType='" + getComentariosContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
