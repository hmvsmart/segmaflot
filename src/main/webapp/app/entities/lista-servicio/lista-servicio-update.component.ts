import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ServicioService from '@/entities/servicio/servicio.service';
import { IServicio } from '@/shared/model/servicio.model';

import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';
import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';

import { IListaServicio, ListaServicio } from '@/shared/model/lista-servicio.model';
import ListaServicioService from './lista-servicio.service';

const validations: any = {
  listaServicio: {
    cantidad: {},
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
export default class ListaServicioUpdate extends Vue {
  @Inject('listaServicioService') private listaServicioService: () => ListaServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public listaServicio: IListaServicio = new ListaServicio();

  @Inject('servicioService') private servicioService: () => ServicioService;

  public servicios: IServicio[] = [];

  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;

  public unidadServicios: IUnidadServicio[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listaServicioId) {
        vm.retrieveListaServicio(to.params.listaServicioId);
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
    if (this.listaServicio.id) {
      this.listaServicioService()
        .update(this.listaServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.listaServicio.updated', { param: param.id });
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
      this.listaServicioService()
        .create(this.listaServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.listaServicio.created', { param: param.id });
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
      this.listaServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.listaServicio[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.listaServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.listaServicio[field] = null;
    }
  }

  public retrieveListaServicio(listaServicioId): void {
    this.listaServicioService()
      .find(listaServicioId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.listaServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.servicioService()
      .retrieve()
      .then(res => {
        this.servicios = res.data;
      });
    this.unidadServicioService()
      .retrieve()
      .then(res => {
        this.unidadServicios = res.data;
      });
  }
}
