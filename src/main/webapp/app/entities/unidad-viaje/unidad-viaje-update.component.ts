import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';
import { IOperadorUnidad } from '@/shared/model/operador-unidad.model';

import ViajeService from '@/entities/viaje/viaje.service';
import { IViaje } from '@/shared/model/viaje.model';

import UnidadService from '@/entities/unidad/unidad.service';
import { IUnidad } from '@/shared/model/unidad.model';

import { IUnidadViaje, UnidadViaje } from '@/shared/model/unidad-viaje.model';
import UnidadViajeService from './unidad-viaje.service';

const validations: any = {
  unidadViaje: {
    fecha: {},
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
export default class UnidadViajeUpdate extends Vue {
  @Inject('unidadViajeService') private unidadViajeService: () => UnidadViajeService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidadViaje: IUnidadViaje = new UnidadViaje();

  @Inject('operadorUnidadService') private operadorUnidadService: () => OperadorUnidadService;

  public operadorUnidads: IOperadorUnidad[] = [];

  @Inject('viajeService') private viajeService: () => ViajeService;

  public viajes: IViaje[] = [];

  @Inject('unidadService') private unidadService: () => UnidadService;

  public unidads: IUnidad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadViajeId) {
        vm.retrieveUnidadViaje(to.params.unidadViajeId);
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
    if (this.unidadViaje.id) {
      this.unidadViajeService()
        .update(this.unidadViaje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.unidadViaje.updated', { param: param.id });
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
      this.unidadViajeService()
        .create(this.unidadViaje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.unidadViaje.created', { param: param.id });
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
      this.unidadViaje[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.unidadViaje[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.unidadViaje[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.unidadViaje[field] = null;
    }
  }

  public retrieveUnidadViaje(unidadViajeId): void {
    this.unidadViajeService()
      .find(unidadViajeId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.unidadViaje = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.operadorUnidadService()
      .retrieve()
      .then(res => {
        this.operadorUnidads = res.data;
      });
    this.viajeService()
      .retrieve()
      .then(res => {
        this.viajes = res.data;
      });
    this.unidadService()
      .retrieve()
      .then(res => {
        this.unidads = res.data;
      });
  }
}
