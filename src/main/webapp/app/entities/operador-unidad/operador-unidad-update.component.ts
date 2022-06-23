import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';
import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';

import EmpleadoService from '@/entities/empleado/empleado.service';
import { IEmpleado } from '@/shared/model/empleado.model';

import { IOperadorUnidad, OperadorUnidad } from '@/shared/model/operador-unidad.model';
import OperadorUnidadService from './operador-unidad.service';

const validations: any = {
  operadorUnidad: {
    fecha: {},
    rol: {},
    status: {},
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
export default class OperadorUnidadUpdate extends Vue {
  @Inject('operadorUnidadService') private operadorUnidadService: () => OperadorUnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public operadorUnidad: IOperadorUnidad = new OperadorUnidad();

  @Inject('unidadViajeService') private unidadViajeService: () => UnidadViajeService;

  public unidadViajes: IUnidadViaje[] = [];

  @Inject('empleadoService') private empleadoService: () => EmpleadoService;

  public empleados: IEmpleado[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operadorUnidadId) {
        vm.retrieveOperadorUnidad(to.params.operadorUnidadId);
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
    if (this.operadorUnidad.id) {
      this.operadorUnidadService()
        .update(this.operadorUnidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.operadorUnidad.updated', { param: param.id });
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
      this.operadorUnidadService()
        .create(this.operadorUnidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.operadorUnidad.created', { param: param.id });
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
      this.operadorUnidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.operadorUnidad[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.operadorUnidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.operadorUnidad[field] = null;
    }
  }

  public retrieveOperadorUnidad(operadorUnidadId): void {
    this.operadorUnidadService()
      .find(operadorUnidadId)
      .then(res => {
        res.fecha = new Date(res.fecha);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.operadorUnidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.unidadViajeService()
      .retrieve()
      .then(res => {
        this.unidadViajes = res.data;
      });
    this.empleadoService()
      .retrieve()
      .then(res => {
        this.empleados = res.data;
      });
  }
}
