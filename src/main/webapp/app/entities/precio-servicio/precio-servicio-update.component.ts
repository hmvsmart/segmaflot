import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ServicioService from '@/entities/servicio/servicio.service';
import { IServicio } from '@/shared/model/servicio.model';

import { IPrecioServicio, PrecioServicio } from '@/shared/model/precio-servicio.model';
import PrecioServicioService from './precio-servicio.service';

const validations: any = {
  precioServicio: {
    fecha: {},
    costo: {},
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
export default class PrecioServicioUpdate extends Vue {
  @Inject('precioServicioService') private precioServicioService: () => PrecioServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public precioServicio: IPrecioServicio = new PrecioServicio();

  @Inject('servicioService') private servicioService: () => ServicioService;

  public servicios: IServicio[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.precioServicioId) {
        vm.retrievePrecioServicio(to.params.precioServicioId);
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
    if (this.precioServicio.id) {
      this.precioServicioService()
        .update(this.precioServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.precioServicio.updated', { param: param.id });
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
      this.precioServicioService()
        .create(this.precioServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.precioServicio.created', { param: param.id });
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
      this.precioServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.precioServicio[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.precioServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.precioServicio[field] = null;
    }
  }

  public retrievePrecioServicio(precioServicioId): void {
    this.precioServicioService()
      .find(precioServicioId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.precioServicio = res;
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
  }
}
