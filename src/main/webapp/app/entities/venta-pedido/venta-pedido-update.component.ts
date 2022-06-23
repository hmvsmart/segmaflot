import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import VentaService from '@/entities/venta/venta.service';
import { IVenta } from '@/shared/model/venta.model';

import PedidoService from '@/entities/pedido/pedido.service';
import { IPedido } from '@/shared/model/pedido.model';

import { IVentaPedido, VentaPedido } from '@/shared/model/venta-pedido.model';
import VentaPedidoService from './venta-pedido.service';

const validations: any = {
  ventaPedido: {
    fecha: {},
  },
};

@Component({
  validations,
})
export default class VentaPedidoUpdate extends Vue {
  @Inject('ventaPedidoService') private ventaPedidoService: () => VentaPedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  public ventaPedido: IVentaPedido = new VentaPedido();

  @Inject('ventaService') private ventaService: () => VentaService;

  public ventas: IVenta[] = [];

  @Inject('pedidoService') private pedidoService: () => PedidoService;

  public pedidos: IPedido[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ventaPedidoId) {
        vm.retrieveVentaPedido(to.params.ventaPedidoId);
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
    if (this.ventaPedido.id) {
      this.ventaPedidoService()
        .update(this.ventaPedido)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.ventaPedido.updated', { param: param.id });
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
      this.ventaPedidoService()
        .create(this.ventaPedido)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.ventaPedido.created', { param: param.id });
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

  public retrieveVentaPedido(ventaPedidoId): void {
    this.ventaPedidoService()
      .find(ventaPedidoId)
      .then(res => {
        this.ventaPedido = res;
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
    this.pedidoService()
      .retrieve()
      .then(res => {
        this.pedidos = res.data;
      });
  }
}
