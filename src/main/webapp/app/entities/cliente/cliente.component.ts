import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICliente } from '@/shared/model/cliente.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ClienteService from './cliente.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cliente extends mixins(JhiDataUtils) {
  @Inject('clienteService') private clienteService: () => ClienteService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public clientes: ICliente[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllClientes();
  }

  public clear(): void {
    this.retrieveAllClientes();
  }

  public retrieveAllClientes(): void {
    this.isFetching = true;
    this.clienteService()
      .retrieve()
      .then(
        res => {
          this.clientes = res.data;
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

  public prepareRemove(instance: ICliente): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCliente(): void {
    this.clienteService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.cliente.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllClientes();
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
