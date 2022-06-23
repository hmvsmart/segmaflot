import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPago } from '@/shared/model/pago.model';

import PagoService from './pago.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Pago extends Vue {
  @Inject('pagoService') private pagoService: () => PagoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public pagos: IPago[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPagos();
  }

  public clear(): void {
    this.retrieveAllPagos();
  }

  public retrieveAllPagos(): void {
    this.isFetching = true;
    this.pagoService()
      .retrieve()
      .then(
        res => {
          this.pagos = res.data;
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

  public prepareRemove(instance: IPago): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePago(): void {
    this.pagoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.pago.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPagos();
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
