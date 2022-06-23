import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRenglonVenta } from '@/shared/model/renglon-venta.model';

import RenglonVentaService from './renglon-venta.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RenglonVenta extends Vue {
  @Inject('renglonVentaService') private renglonVentaService: () => RenglonVentaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public renglonVentas: IRenglonVenta[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRenglonVentas();
  }

  public clear(): void {
    this.retrieveAllRenglonVentas();
  }

  public retrieveAllRenglonVentas(): void {
    this.isFetching = true;
    this.renglonVentaService()
      .retrieve()
      .then(
        res => {
          this.renglonVentas = res.data;
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

  public prepareRemove(instance: IRenglonVenta): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRenglonVenta(): void {
    this.renglonVentaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.renglonVenta.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRenglonVentas();
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
