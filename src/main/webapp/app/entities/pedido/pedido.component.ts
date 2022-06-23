import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPedido } from '@/shared/model/pedido.model';

import PedidoService from './pedido.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Pedido extends Vue {
  @Inject('pedidoService') private pedidoService: () => PedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public pedidos: IPedido[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPedidos();
  }

  public clear(): void {
    this.retrieveAllPedidos();
  }

  public retrieveAllPedidos(): void {
    this.isFetching = true;
    this.pedidoService()
      .retrieve()
      .then(
        res => {
          this.pedidos = res.data;
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

  public prepareRemove(instance: IPedido): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePedido(): void {
    this.pedidoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.pedido.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPedidos();
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
