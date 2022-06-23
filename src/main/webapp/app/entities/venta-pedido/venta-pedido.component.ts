import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVentaPedido } from '@/shared/model/venta-pedido.model';

import VentaPedidoService from './venta-pedido.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class VentaPedido extends Vue {
  @Inject('ventaPedidoService') private ventaPedidoService: () => VentaPedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public ventaPedidos: IVentaPedido[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVentaPedidos();
  }

  public clear(): void {
    this.retrieveAllVentaPedidos();
  }

  public retrieveAllVentaPedidos(): void {
    this.isFetching = true;
    this.ventaPedidoService()
      .retrieve()
      .then(
        res => {
          this.ventaPedidos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IVentaPedido): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVentaPedido(): void {
    this.ventaPedidoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.ventaPedido.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVentaPedidos();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
