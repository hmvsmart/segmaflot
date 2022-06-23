import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPrecioVenta } from '@/shared/model/precio-venta.model';

import PrecioVentaService from './precio-venta.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PrecioVenta extends Vue {
  @Inject('precioVentaService') private precioVentaService: () => PrecioVentaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public precioVentas: IPrecioVenta[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPrecioVentas();
  }

  public clear(): void {
    this.retrieveAllPrecioVentas();
  }

  public retrieveAllPrecioVentas(): void {
    this.isFetching = true;
    this.precioVentaService()
      .retrieve()
      .then(
        res => {
          this.precioVentas = res.data;
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

  public prepareRemove(instance: IPrecioVenta): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePrecioVenta(): void {
    this.precioVentaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.precioVenta.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPrecioVentas();
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
