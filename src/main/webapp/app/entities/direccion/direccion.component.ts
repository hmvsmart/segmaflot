import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDireccion } from '@/shared/model/direccion.model';

import DireccionService from './direccion.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Direccion extends Vue {
  @Inject('direccionService') private direccionService: () => DireccionService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public direccions: IDireccion[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDireccions();
  }

  public clear(): void {
    this.retrieveAllDireccions();
  }

  public retrieveAllDireccions(): void {
    this.isFetching = true;
    this.direccionService()
      .retrieve()
      .then(
        res => {
          this.direccions = res.data;
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

  public prepareRemove(instance: IDireccion): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDireccion(): void {
    this.direccionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.direccion.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDireccions();
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
