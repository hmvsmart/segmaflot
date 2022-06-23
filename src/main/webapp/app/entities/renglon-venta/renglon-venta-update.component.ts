import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import VentaService from '@/entities/venta/venta.service';
import { IVenta } from '@/shared/model/venta.model';

import PrecioVentaService from '@/entities/precio-venta/precio-venta.service';
import { IPrecioVenta } from '@/shared/model/precio-venta.model';

import { IRenglonVenta, RenglonVenta } from '@/shared/model/renglon-venta.model';
import RenglonVentaService from './renglon-venta.service';

const validations: any = {
  renglonVenta: {
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
export default class RenglonVentaUpdate extends Vue {
  @Inject('renglonVentaService') private renglonVentaService: () => RenglonVentaService;
  @Inject('alertService') private alertService: () => AlertService;

  public renglonVenta: IRenglonVenta = new RenglonVenta();

  @Inject('ventaService') private ventaService: () => VentaService;

  public ventas: IVenta[] = [];

  @Inject('precioVentaService') private precioVentaService: () => PrecioVentaService;

  public precioVentas: IPrecioVenta[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.renglonVentaId) {
        vm.retrieveRenglonVenta(to.params.renglonVentaId);
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
    if (this.renglonVenta.id) {
      this.renglonVentaService()
        .update(this.renglonVenta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.renglonVenta.updated', { param: param.id });
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
      this.renglonVentaService()
        .create(this.renglonVenta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.renglonVenta.created', { param: param.id });
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
      this.renglonVenta[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.renglonVenta[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.renglonVenta[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.renglonVenta[field] = null;
    }
  }

  public retrieveRenglonVenta(renglonVentaId): void {
    this.renglonVentaService()
      .find(renglonVentaId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.renglonVenta = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ventaService()
      .retrieve()
      .then(res => {
        this.ventas = res.data;
      });
    this.precioVentaService()
      .retrieve()
      .then(res => {
        this.precioVentas = res.data;
      });
  }
}
