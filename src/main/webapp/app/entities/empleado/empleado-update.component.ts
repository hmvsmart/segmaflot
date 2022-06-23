import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import EmpleadoTipoVehiculoService from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo.service';
import { IEmpleadoTipoVehiculo } from '@/shared/model/empleado-tipo-vehiculo.model';

import ExperienciaHabilidadService from '@/entities/experiencia-habilidad/experiencia-habilidad.service';
import { IExperienciaHabilidad } from '@/shared/model/experiencia-habilidad.model';

import LicenciaManejoService from '@/entities/licencia-manejo/licencia-manejo.service';
import { ILicenciaManejo } from '@/shared/model/licencia-manejo.model';

import HorarioService from '@/entities/horario/horario.service';
import { IHorario } from '@/shared/model/horario.model';

import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';
import { IOperadorUnidad } from '@/shared/model/operador-unidad.model';

import PersonaService from '@/entities/persona/persona.service';
import { IPersona } from '@/shared/model/persona.model';

import SucursalService from '@/entities/sucursal/sucursal.service';
import { ISucursal } from '@/shared/model/sucursal.model';

import { IEmpleado, Empleado } from '@/shared/model/empleado.model';
import EmpleadoService from './empleado.service';

const validations: any = {
  empleado: {
    rfc: {},
    nss: {},
    finicio: {},
    puesto: {},
    salario: {},
    diaPago: {},
    tipoPago: {},
    createByUser: {},
    createdOn: {},
    modifyByUser: {},
    modifiedOn: {},
    auditStatus: {},
  },
};

@Component({
  validations,
})
export default class EmpleadoUpdate extends Vue {
  @Inject('empleadoService') private empleadoService: () => EmpleadoService;
  @Inject('alertService') private alertService: () => AlertService;

  public empleado: IEmpleado = new Empleado();

  @Inject('empleadoTipoVehiculoService') private empleadoTipoVehiculoService: () => EmpleadoTipoVehiculoService;

  public empleadoTipoVehiculos: IEmpleadoTipoVehiculo[] = [];

  @Inject('experienciaHabilidadService') private experienciaHabilidadService: () => ExperienciaHabilidadService;

  public experienciaHabilidads: IExperienciaHabilidad[] = [];

  @Inject('licenciaManejoService') private licenciaManejoService: () => LicenciaManejoService;

  public licenciaManejos: ILicenciaManejo[] = [];

  @Inject('horarioService') private horarioService: () => HorarioService;

  public horarios: IHorario[] = [];

  @Inject('operadorUnidadService') private operadorUnidadService: () => OperadorUnidadService;

  public operadorUnidads: IOperadorUnidad[] = [];

  @Inject('personaService') private personaService: () => PersonaService;

  public personas: IPersona[] = [];

  @Inject('sucursalService') private sucursalService: () => SucursalService;

  public sucursals: ISucursal[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.empleadoId) {
        vm.retrieveEmpleado(to.params.empleadoId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.empleado.id) {
      this.empleadoService()
        .update(this.empleado)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.empleado.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.empleadoService()
        .create(this.empleado)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.empleado.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.empleado[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.empleado[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.empleado[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.empleado[field] = null;
    }
  }

  public retrieveEmpleado(empleadoId): void {
    this.empleadoService()
      .find(empleadoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.empleado = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.empleadoTipoVehiculoService()
      .retrieve()
      .then(res => {
        this.empleadoTipoVehiculos = res.data;
      });
    this.experienciaHabilidadService()
      .retrieve()
      .then(res => {
        this.experienciaHabilidads = res.data;
      });
    this.licenciaManejoService()
      .retrieve()
      .then(res => {
        this.licenciaManejos = res.data;
      });
    this.horarioService()
      .retrieve()
      .then(res => {
        this.horarios = res.data;
      });
    this.operadorUnidadService()
      .retrieve()
      .then(res => {
        this.operadorUnidads = res.data;
      });
    this.personaService()
      .retrieve()
      .then(res => {
        this.personas = res.data;
      });
    this.sucursalService()
      .retrieve()
      .then(res => {
        this.sucursals = res.data;
      });
  }
}
