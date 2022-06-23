import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import RenglonVentaService from '@/entities/renglon-venta/renglon-venta.service';
import { IRenglonVenta } from '@/shared/model/renglon-venta.model';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import { IPrecioVenta, PrecioVenta } from '@/shared/model/precio-venta.model';
import PrecioVentaService from './precio-venta.service';

const validations: any = {
  precioVenta: {
    fecha: {},
    ppu: {},
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
export default class PrecioVentaUpdate extends Vue {
  @Inject('precioVentaService') private precioVentaService: () => PrecioVentaService;
  @Inject('alertService') private alertService: () => AlertService;

  public precioVenta: IPrecioVenta = new PrecioVenta();

  @Inject('renglonVentaService') private renglonVentaService: () => RenglonVentaService;

  public renglonVentas: IRenglonVenta[] = [];

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.precioVentaId) {
        vm.retrievePrecioVenta(to.params.precioVentaId);
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
    if (this.precioVenta.id) {
      this.precioVentaService()
        .update(this.precioVenta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.precioVenta.updated', { param: param.id });
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
      this.precioVentaService()
        .create(this.precioVenta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.precioVenta.created', { param: param.id });
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
      this.precioVenta[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.precioVenta[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.precioVenta[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.precioVenta[field] = null;
    }
  }

  public retrievePrecioVenta(precioVentaId): void {
    this.precioVentaService()
      .find(precioVentaId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.precioVenta = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.renglonVentaService()
      .retrieve()
      .then(res => {
        this.renglonVentas = res.data;
      });
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
  }
}
