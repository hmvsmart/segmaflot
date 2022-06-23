import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISucursal } from '@/shared/model/sucursal.model';

import SucursalService from './sucursal.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Sucursal extends Vue {
  @Inject('sucursalService') private sucursalService: () => SucursalService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public sucursals: ISucursal[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSucursals();
  }

  public clear(): void {
    this.retrieveAllSucursals();
  }

  public retrieveAllSucursals(): void {
    this.isFetching = true;
    this.sucursalService()
      .retrieve()
      .then(
        res => {
          this.sucursals = res.data;
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

  public prepareRemove(instance: ISucursal): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSucursal(): void {
    this.sucursalService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.sucursal.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSucursals();
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
