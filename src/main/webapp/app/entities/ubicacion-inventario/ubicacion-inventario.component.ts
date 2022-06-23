import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUbicacionInventario } from '@/shared/model/ubicacion-inventario.model';

import UbicacionInventarioService from './ubicacion-inventario.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class UbicacionInventario extends Vue {
  @Inject('ubicacionInventarioService') private ubicacionInventarioService: () => UbicacionInventarioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public ubicacionInventarios: IUbicacionInventario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllUbicacionInventarios();
  }

  public clear(): void {
    this.retrieveAllUbicacionInventarios();
  }

  public retrieveAllUbicacionInventarios(): void {
    this.isFetching = true;
    this.ubicacionInventarioService()
      .retrieve()
      .then(
        res => {
          this.ubicacionInventarios = res.data;
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

  public prepareRemove(instance: IUbicacionInventario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeUbicacionInventario(): void {
    this.ubicacionInventarioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.ubicacionInventario.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllUbicacionInventarios();
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
