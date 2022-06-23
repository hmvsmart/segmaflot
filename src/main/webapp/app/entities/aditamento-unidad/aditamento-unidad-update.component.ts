import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import AditamentoService from '@/entities/aditamento/aditamento.service';
import { IAditamento } from '@/shared/model/aditamento.model';

import UnidadService from '@/entities/unidad/unidad.service';
import { IUnidad } from '@/shared/model/unidad.model';

import { IAditamentoUnidad, AditamentoUnidad } from '@/shared/model/aditamento-unidad.model';
import AditamentoUnidadService from './aditamento-unidad.service';

const validations: any = {
  aditamentoUnidad: {
    fecha: {},
    numeroSerie: {},
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
export default class AditamentoUnidadUpdate extends Vue {
  @Inject('aditamentoUnidadService') private aditamentoUnidadService: () => AditamentoUnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public aditamentoUnidad: IAditamentoUnidad = new AditamentoUnidad();

  @Inject('aditamentoService') private aditamentoService: () => AditamentoService;

  public aditamentos: IAditamento[] = [];

  @Inject('unidadService') private unidadService: () => UnidadService;

  public unidads: IUnidad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aditamentoUnidadId) {
        vm.retrieveAditamentoUnidad(to.params.aditamentoUnidadId);
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
    if (this.aditamentoUnidad.id) {
      this.aditamentoUnidadService()
        .update(this.aditamentoUnidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.aditamentoUnidad.updated', { param: param.id });
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
      this.aditamentoUnidadService()
        .create(this.aditamentoUnidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.aditamentoUnidad.created', { param: param.id });
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
      this.aditamentoUnidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.aditamentoUnidad[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.aditamentoUnidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.aditamentoUnidad[field] = null;
    }
  }

  public retrieveAditamentoUnidad(aditamentoUnidadId): void {
    this.aditamentoUnidadService()
      .find(aditamentoUnidadId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.aditamentoUnidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.aditamentoService()
      .retrieve()
      .then(res => {
        this.aditamentos = res.data;
      });
    this.unidadService()
      .retrieve()
      .then(res => {
        this.unidads = res.data;
      });
  }
}
