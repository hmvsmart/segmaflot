import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import EmpleadoService from '@/entities/empleado/empleado.service';
import { IEmpleado } from '@/shared/model/empleado.model';

import { IEmpleadoTipoVehiculo, EmpleadoTipoVehiculo } from '@/shared/model/empleado-tipo-vehiculo.model';
import EmpleadoTipoVehiculoService from './empleado-tipo-vehiculo.service';

const validations: any = {
  empleadoTipoVehiculo: {
    fecha: {},
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
export default class EmpleadoTipoVehiculoUpdate extends Vue {
  @Inject('empleadoTipoVehiculoService') private empleadoTipoVehiculoService: () => EmpleadoTipoVehiculoService;
  @Inject('alertService') private alertService: () => AlertService;

  public empleadoTipoVehiculo: IEmpleadoTipoVehiculo = new EmpleadoTipoVehiculo();

  @Inject('empleadoService') private empleadoService: () => EmpleadoService;

  public empleados: IEmpleado[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.empleadoTipoVehiculoId) {
        vm.retrieveEmpleadoTipoVehiculo(to.params.empleadoTipoVehiculoId);
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
    if (this.empleadoTipoVehiculo.id) {
      this.empleadoTipoVehiculoService()
        .update(this.empleadoTipoVehiculo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.empleadoTipoVehiculo.updated', { param: param.id });
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
      this.empleadoTipoVehiculoService()
        .create(this.empleadoTipoVehiculo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.empleadoTipoVehiculo.created', { param: param.id });
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
      this.empleadoTipoVehiculo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.empleadoTipoVehiculo[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.empleadoTipoVehiculo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.empleadoTipoVehiculo[field] = null;
    }
  }

  public retrieveEmpleadoTipoVehiculo(empleadoTipoVehiculoId): void {
    this.empleadoTipoVehiculoService()
      .find(empleadoTipoVehiculoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.empleadoTipoVehiculo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.empleadoService()
      .retrieve()
      .then(res => {
        this.empleados = res.data;
      });
  }
}
