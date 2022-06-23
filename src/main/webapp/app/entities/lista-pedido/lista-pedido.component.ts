import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListaPedido } from '@/shared/model/lista-pedido.model';

import ListaPedidoService from './lista-pedido.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ListaPedido extends Vue {
  @Inject('listaPedidoService') private listaPedidoService: () => ListaPedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public listaPedidos: IListaPedido[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllListaPedidos();
  }

  public clear(): void {
    this.retrieveAllListaPedidos();
  }

  public retrieveAllListaPedidos(): void {
    this.isFetching = true;
    this.listaPedidoService()
      .retrieve()
      .then(
        res => {
          this.listaPedidos = res.data;
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

  public prepareRemove(instance: IListaPedido): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeListaPedido(): void {
    this.listaPedidoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.listaPedido.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllListaPedidos();
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
