import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import RenglonVentaService from '@/entities/renglon-venta/renglon-venta.service';
import { IRenglonVenta } from '@/shared/model/renglon-venta.model';

import VentaPedidoService from '@/entities/venta-pedido/venta-pedido.service';
import { IVentaPedido } from '@/shared/model/venta-pedido.model';

import { IVenta, Venta } from '@/shared/model/venta.model';
import VentaService from './venta.service';

const validations: any = {
  venta: {
    fecha: {},
    total: {},
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
export default class VentaUpdate extends Vue {
  @Inject('ventaService') private ventaService: () => VentaService;
  @Inject('alertService') private alertService: () => AlertService;

  public venta: IVenta = new Venta();

  @Inject('renglonVentaService') private renglonVentaService: () => RenglonVentaService;

  public renglonVentas: IRenglonVenta[] = [];

  @Inject('ventaPedidoService') private ventaPedidoService: () => VentaPedidoService;

  public ventaPedidos: IVentaPedido[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ventaId) {
        vm.retrieveVenta(to.params.ventaId);
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
    if (this.venta.id) {
      this.ventaService()
        .update(this.venta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.venta.updated', { param: param.id });
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
      this.ventaService()
        .create(this.venta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.venta.created', { param: param.id });
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
      this.venta[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.venta[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.venta[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.venta[field] = null;
    }
  }

  public retrieveVenta(ventaId): void {
    this.ventaService()
      .find(ventaId)
      .then(res => {
        res.fecha = new Date(res.fecha);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.venta = res;
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
    this.ventaPedidoService()
      .retrieve()
      .then(res => {
        this.ventaPedidos = res.data;
      });
  }
}
