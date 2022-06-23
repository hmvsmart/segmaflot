import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import { IPerdida, Perdida } from '@/shared/model/perdida.model';
import PerdidaService from './perdida.service';

const validations: any = {
  perdida: {
    fecha: {},
    cantidad: {},
    observaciones: {},
  },
};

@Component({
  validations,
})
export default class PerdidaUpdate extends mixins(JhiDataUtils) {
  @Inject('perdidaService') private perdidaService: () => PerdidaService;
  @Inject('alertService') private alertService: () => AlertService;

  public perdida: IPerdida = new Perdida();

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.perdidaId) {
        vm.retrievePerdida(to.params.perdidaId);
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
    if (this.perdida.id) {
      this.perdidaService()
        .update(this.perdida)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.perdida.updated', { param: param.id });
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
      this.perdidaService()
        .create(this.perdida)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.perdida.created', { param: param.id });
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
      this.perdida[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.perdida[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.perdida[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.perdida[field] = null;
    }
  }

  public retrievePerdida(perdidaId): void {
    this.perdidaService()
      .find(perdidaId)
      .then(res => {
        res.fecha = new Date(res.fecha);
        this.perdida = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
  }
}
