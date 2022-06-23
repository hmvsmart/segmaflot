import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UnidadService from '@/entities/unidad/unidad.service';
import { IUnidad } from '@/shared/model/unidad.model';

import { IPoliza, Poliza } from '@/shared/model/poliza.model';
import PolizaService from './poliza.service';

const validations: any = {
  poliza: {
    fecha: {},
    fechaVencimiento: {},
    numeroPoliza: {},
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
export default class PolizaUpdate extends Vue {
  @Inject('polizaService') private polizaService: () => PolizaService;
  @Inject('alertService') private alertService: () => AlertService;

  public poliza: IPoliza = new Poliza();

  @Inject('unidadService') private unidadService: () => UnidadService;

  public unidads: IUnidad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.polizaId) {
        vm.retrievePoliza(to.params.polizaId);
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
    if (this.poliza.id) {
      this.polizaService()
        .update(this.poliza)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.poliza.updated', { param: param.id });
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
      this.polizaService()
        .create(this.poliza)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.poliza.created', { param: param.id });
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
      this.poliza[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.poliza[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.poliza[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.poliza[field] = null;
    }
  }

  public retrievePoliza(polizaId): void {
    this.polizaService()
      .find(polizaId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.poliza = res;
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
