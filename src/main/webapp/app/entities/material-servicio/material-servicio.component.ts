import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMaterialServicio } from '@/shared/model/material-servicio.model';

import MaterialServicioService from './material-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MaterialServicio extends Vue {
  @Inject('materialServicioService') private materialServicioService: () => MaterialServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public materialServicios: IMaterialServicio[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMaterialServicios();
  }

  public clear(): void {
    this.retrieveAllMaterialServicios();
  }

  public retrieveAllMaterialServicios(): void {
    this.isFetching = true;
    this.materialServicioService()
      .retrieve()
      .then(
        res => {
          this.materialServicios = res.data;
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

  public prepareRemove(instance: IMaterialServicio): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMaterialServicio(): void {
    this.materialServicioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.materialServicio.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMaterialServicios();
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
