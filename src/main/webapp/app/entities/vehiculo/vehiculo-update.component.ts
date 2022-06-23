import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UnidadService from '@/entities/unidad/unidad.service';
import { IUnidad } from '@/shared/model/unidad.model';

import { IVehiculo, Vehiculo } from '@/shared/model/vehiculo.model';
import VehiculoService from './vehiculo.service';

const validations: any = {
  vehiculo: {
    marca: {},
    submarca: {},
    modelo: {},
    generacion: {},
    tipoVehiculo: {},
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
export default class VehiculoUpdate extends Vue {
  @Inject('vehiculoService') private vehiculoService: () => VehiculoService;
  @Inject('alertService') private alertService: () => AlertService;

  public vehiculo: IVehiculo = new Vehiculo();

  @Inject('unidadService') private unidadService: () => UnidadService;

  public unidads: IUnidad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vehiculoId) {
        vm.retrieveVehiculo(to.params.vehiculoId);
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
    if (this.vehiculo.id) {
      this.vehiculoService()
        .update(this.vehiculo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.vehiculo.updated', { param: param.id });
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
      this.vehiculoService()
        .create(this.vehiculo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.vehiculo.created', { param: param.id });
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
      this.vehiculo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.vehiculo[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.vehiculo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.vehiculo[field] = null;
    }
  }

  public retrieveVehiculo(vehiculoId): void {
    this.vehiculoService()
      .find(vehiculoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.vehiculo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.unidadService()
      .retrieve()
      .then(res => {
        this.unidads = res.data;
      });
  }
}
