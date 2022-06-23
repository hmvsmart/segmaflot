import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

import { IReporte, Reporte } from '@/shared/model/reporte.model';
import ReporteService from './reporte.service';

const validations: any = {
  reporte: {
    fecha: {},
    nombre: {},
    documento: {},
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
export default class ReporteUpdate extends mixins(JhiDataUtils) {
  @Inject('reporteService') private reporteService: () => ReporteService;
  @Inject('alertService') private alertService: () => AlertService;

  public reporte: IReporte = new Reporte();

  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;

  public personaMorals: IPersonaMoral[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reporteId) {
        vm.retrieveReporte(to.params.reporteId);
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
    if (this.reporte.id) {
      this.reporteService()
        .update(this.reporte)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.reporte.updated', { param: param.id });
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
      this.reporteService()
        .create(this.reporte)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.reporte.created', { param: param.id });
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
      this.reporte[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.reporte[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.reporte[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.reporte[field] = null;
    }
  }

  public retrieveReporte(reporteId): void {
    this.reporteService()
      .find(reporteId)
      .then(res => {
        res.fecha = new Date(res.fecha);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.reporte = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.personaMoralService()
      .retrieve()
      .then(res => {
        this.personaMorals = res.data;
      });
  }
}
