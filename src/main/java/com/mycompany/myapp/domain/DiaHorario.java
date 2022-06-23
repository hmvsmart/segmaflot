package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DiaHorario.
 */
@Entity
@Table(name = "dia_horario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiaHorario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dia")
    private Integer dia;

    @Column(name = "hora_entrada")
    private Instant horaEntrada;

    @Column(name = "hora_salida")
    private Instant horaSalida;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "diaHorarios", "empleado" }, allowSetters = true)
    private Horario horario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DiaHorario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDia() {
        return this.dia;
    }

    public DiaHorario dia(Integer dia) {
        this.setDia(dia);
        return this;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Instant getHoraEntrada() {
        return this.horaEntrada;
    }

    public DiaHorario horaEntrada(Instant horaEntrada) {
        this.setHoraEntrada(horaEntrada);
        return this;
    }

    public void setHoraEntrada(Instant horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Instant getHoraSalida() {
        return this.horaSalida;
    }

    public DiaHorario horaSalida(Instant horaSalida) {
        this.setHoraSalida(horaSalida);
        return this;
    }

    public void setHoraSalida(Instant horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getCreateByUser() {
        return this.createByUser;
    }

    public DiaHorario createByUser(String createByUser) {
        this.setCreateByUser(createByUser);
        return this;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public DiaHorario createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifyByUser() {
        return this.modifyByUser;
    }

    public DiaHorario modifyByUser(String modifyByUser) {
        this.setModifyByUser(modifyByUser);
        return this;
    }

    public void setModifyByUser(String modifyByUser) {
        this.modifyByUser = modifyByUser;
    }

    public Instant getModifiedOn() {
        return this.modifiedOn;
    }

    public DiaHorario modifiedOn(Instant modifiedOn) {
        this.setModifiedOn(modifiedOn);
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getAuditStatus() {
        return this.auditStatus;
    }

    public DiaHorario auditStatus(String auditStatus) {
        this.setAuditStatus(auditStatus);
        return this;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Horario getHorario() {
        return this.horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public DiaHorario horario(Horario horario) {
        this.setHorario(horario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiaHorario)) {
            return false;
        }
        return id != null && id.equals(((DiaHorario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiaHorario{" +
            "id=" + getId() +
            ", dia=" + getDia() +
            ", horaEntrada='" + getHoraEntrada() + "'" +
            ", horaSalida='" + getHoraSalida() + "'" +
            ", createByUser='" + getCreateByUser() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifyByUser='" + getModifyByUser() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", auditStatus='" + getAuditStatus() + "'" +
            "}";
    }
}
