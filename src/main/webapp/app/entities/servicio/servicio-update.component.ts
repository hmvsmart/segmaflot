import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';
import { IMaterialServicio } from '@/shared/model/material-servicio.model';

import PrecioServicioService from '@/entities/precio-servicio/precio-servicio.service';
import { IPrecioServicio } from '@/shared/model/precio-servicio.model';

import ListaServicioService from '@/entities/lista-servicio/lista-servicio.service';
import { IListaServicio } from '@/shared/model/lista-servicio.model';

import { IServicio, Servicio } from '@/shared/model/servicio.model';
import ServicioService from './servicio.service';

const validations: any = {
  servicio: {
    nombre: {},
    descripcion: {},
    duracionEstimada: {},
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
export default class ServicioUpdate extends mixins(JhiDataUtils) {
  @Inject('servicioService') private servicioService: () => ServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public servicio: IServicio = new Servicio();

  @Inject('materialServicioService') private materialServicioService: () => MaterialServicioService;

  public materialServicios: IMaterialServicio[] = [];

  @Inject('precioServicioService') private precioServicioService: () => PrecioServicioService;

  public precioServicios: IPrecioServicio[] = [];

  @Inject('listaServicioService') private listaServicioService: () => ListaServicioService;

  public listaServicios: IListaServicio[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.servicioId) {
        vm.retrieveServicio(to.params.servicioId);
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
    if (this.servicio.id) {
      this.servicioService()
        .update(this.servicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.servicio.updated', { param: param.id });
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
      this.servicioService()
        .create(this.servicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.servicio.created', { param: param.id });
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
      this.servicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.servicio[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.servicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.servicio[field] = null;
    }
  }

  public retrieveServicio(servicioId): void {
    this.servicioService()
      .find(servicioId)
      .then(res => {
        res.duracionEstimada = new Date(res.duracionEstimada);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.servicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.materialServicioService()
      .retrieve()
      .then(res => {
        this.materialServicios = res.data;
      });
    this.precioServicioService()
      .retrieve()
      .then(res => {
        this.precioServicios = res.data;
      });
    this.listaServicioService()
      .retrieve()
      .then(res => {
        this.listaServicios = res.data;
      });
  }
}
