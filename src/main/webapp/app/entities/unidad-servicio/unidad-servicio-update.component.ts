import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ListaServicioService from '@/entities/lista-servicio/lista-servicio.service';
import { IListaServicio } from '@/shared/model/lista-servicio.model';

import ClienteService from '@/entities/cliente/cliente.service';
import { ICliente } from '@/shared/model/cliente.model';

import { IUnidadServicio, UnidadServicio } from '@/shared/model/unidad-servicio.model';
import UnidadServicioService from './unidad-servicio.service';

const validations: any = {
  unidadServicio: {
    fecha: {},
    totalEstimado: {},
    totalReal: {},
    observacionesGenerales: {},
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
export default class UnidadServicioUpdate extends mixins(JhiDataUtils) {
  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidadServicio: IUnidadServicio = new UnidadServicio();

  @Inject('listaServicioService') private listaServicioService: () => ListaServicioService;

  public listaServicios: IListaServicio[] = [];

  @Inject('clienteService') private clienteService: () => ClienteService;

  public clientes: ICliente[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadServicioId) {
        vm.retrieveUnidadServicio(to.params.unidadServicioId);
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
    if (this.unidadServicio.id) {
      this.unidadServicioService()
        .update(this.unidadServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.unidadServicio.updated', { param: param.id });
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
      this.unidadServicioService()
        .create(this.unidadServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.unidadServicio.created', { param: param.id });
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
      this.unidadServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.unidadServicio[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.unidadServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.unidadServicio[field] = null;
    }
  }

  public retrieveUnidadServicio(unidadServicioId): void {
    this.unidadServicioService()
      .find(unidadServicioId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.unidadServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listaServicioService()
      .retrieve()
      .then(res => {
        this.listaServicios = res.data;
      });
    this.clienteService()
      .retrieve()
      .then(res => {
        this.clientes = res.data;
      });
  }
}
