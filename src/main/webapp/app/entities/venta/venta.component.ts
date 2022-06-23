import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVenta } from '@/shared/model/venta.model';

import VentaService from './venta.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Venta extends Vue {
  @Inject('ventaService') private ventaService: () => VentaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public ventas: IVenta[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVentas();
  }

  public clear(): void {
    this.retrieveAllVentas();
  }

  public retrieveAllVentas(): void {
    this.isFetching = true;
    this.ventaService()
      .retrieve()
      .then(
        res => {
          this.ventas = res.data;
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

  public prepareRemove(instance: IVenta): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVenta(): void {
    this.ventaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.venta.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVentas();
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
