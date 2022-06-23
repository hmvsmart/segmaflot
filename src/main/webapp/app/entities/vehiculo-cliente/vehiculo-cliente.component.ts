import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVehiculoCliente } from '@/shared/model/vehiculo-cliente.model';

import VehiculoClienteService from './vehiculo-cliente.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class VehiculoCliente extends Vue {
  @Inject('vehiculoClienteService') private vehiculoClienteService: () => VehiculoClienteService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public vehiculoClientes: IVehiculoCliente[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVehiculoClientes();
  }

  public clear(): void {
    this.retrieveAllVehiculoClientes();
  }

  public retrieveAllVehiculoClientes(): void {
    this.isFetching = true;
    this.vehiculoClienteService()
      .retrieve()
      .then(
        res => {
          this.vehiculoClientes = res.data;
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

  public prepareRemove(instance: IVehiculoCliente): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVehiculoCliente(): void {
    this.vehiculoClienteService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.vehiculoCliente.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVehiculoClientes();
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
