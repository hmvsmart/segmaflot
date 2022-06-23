import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPrecioServicio } from '@/shared/model/precio-servicio.model';

import PrecioServicioService from './precio-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PrecioServicio extends Vue {
  @Inject('precioServicioService') private precioServicioService: () => PrecioServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public precioServicios: IPrecioServicio[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPrecioServicios();
  }

  public clear(): void {
    this.retrieveAllPrecioServicios();
  }

  public retrieveAllPrecioServicios(): void {
    this.isFetching = true;
    this.precioServicioService()
      .retrieve()
      .then(
        res => {
          this.precioServicios = res.data;
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

  public prepareRemove(instance: IPrecioServicio): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePrecioServicio(): void {
    this.precioServicioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.precioServicio.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPrecioServicios();
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
