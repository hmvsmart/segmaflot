import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';
import { IAditamentoUnidad } from '@/shared/model/aditamento-unidad.model';

import { IAditamento, Aditamento } from '@/shared/model/aditamento.model';
import AditamentoService from './aditamento.service';

const validations: any = {
  aditamento: {
    nombre: {},
    numeroSerie: {},
    descripcion: {},
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
export default class AditamentoUpdate extends mixins(JhiDataUtils) {
  @Inject('aditamentoService') private aditamentoService: () => AditamentoService;
  @Inject('alertService') private alertService: () => AlertService;

  public aditamento: IAditamento = new Aditamento();

  @Inject('aditamentoUnidadService') private aditamentoUnidadService: () => AditamentoUnidadService;

  public aditamentoUnidads: IAditamentoUnidad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aditamentoId) {
        vm.retrieveAditamento(to.params.aditamentoId);
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
    if (this.aditamento.id) {
      this.aditamentoService()
        .update(this.aditamento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.aditamento.updated', { param: param.id });
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
      this.aditamentoService()
        .create(this.aditamento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.aditamento.created', { param: param.id });
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
      this.aditamento[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.aditamento[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.aditamento[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.aditamento[field] = null;
    }
  }

  public retrieveAditamento(aditamentoId): void {
    this.aditamentoService()
      .find(aditamentoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.aditamento = res;
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
  }
}
