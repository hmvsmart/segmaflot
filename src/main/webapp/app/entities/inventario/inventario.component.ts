import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IInventario } from '@/shared/model/inventario.model';

import InventarioService from './inventario.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Inventario extends Vue {
  @Inject('inventarioService') private inventarioService: () => InventarioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public inventarios: IInventario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllInventarios();
  }

  public clear(): void {
    this.retrieveAllInventarios();
  }

  public retrieveAllInventarios(): void {
    this.isFetching = true;
    this.inventarioService()
      .retrieve()
      .then(
        res => {
          this.inventarios = res.data;
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

  public prepareRemove(instance: IInventario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeInventario(): void {
    this.inventarioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.inventario.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllInventarios();
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
