import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';
import { IAditamentoUnidad } from '@/shared/model/aditamento-unidad.model';

import PolizaService from '@/entities/poliza/poliza.service';
import { IPoliza } from '@/shared/model/poliza.model';

import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';
import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';

import VehiculoService from '@/entities/vehiculo/vehiculo.service';
import { IVehiculo } from '@/shared/model/vehiculo.model';

import { IUnidad, Unidad } from '@/shared/model/unidad.model';
import UnidadService from './unidad.service';

const validations: any = {
  unidad: {
    fecha: {},
    numeroSerie: {},
    kmActual: {},
    tipoAdquisicion: {},
    imagen: {},
    color: {},
    descripcion: {},
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
export default class UnidadUpdate extends mixins(JhiDataUtils) {
  @Inject('unidadService') private unidadService: () => UnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidad: IUnidad = new Unidad();

  @Inject('aditamentoUnidadService') private aditamentoUnidadService: () => AditamentoUnidadService;

  public aditamentoUnidads: IAditamentoUnidad[] = [];

  @Inject('polizaService') private polizaService: () => PolizaService;

  public polizas: IPoliza[] = [];

  @Inject('unidadViajeService') private unidadViajeService: () => UnidadViajeService;

  public unidadViajes: IUnidadViaje[] = [];

  @Inject('vehiculoService') private vehiculoService: () => VehiculoService;

  public vehiculos: IVehiculo[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadId) {
        vm.retrieveUnidad(to.params.unidadId);
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
    if (this.unidad.id) {
      this.unidadService()
        .update(this.unidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.unidad.updated', { param: param.id });
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
      this.unidadService()
        .create(this.unidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.unidad.created', { param: param.id });
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
      this.unidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.unidad[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.unidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.unidad[field] = null;
    }
  }

  public retrieveUnidad(unidadId): void {
    this.unidadService()
      .find(unidadId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.unidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.aditamentoUnidadService()
      .retrieve()
      .then(res => {
        this.aditamentoUnidads = res.data;
      });
    this.polizaService()
      .retrieve()
      .then(res => {
        this.polizas = res.data;
      });
    this.unidadViajeService()
      .retrieve()
      .then(res => {
        this.unidadViajes = res.data;
      });
    this.vehiculoService()
      .retrieve()
      .then(res => {
        this.vehiculos = res.data;
      });
  }
}
