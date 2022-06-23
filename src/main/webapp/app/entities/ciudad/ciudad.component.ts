import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICiudad } from '@/shared/model/ciudad.model';

import CiudadService from './ciudad.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Ciudad extends Vue {
  @Inject('ciudadService') private ciudadService: () => CiudadService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public ciudads: ICiudad[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCiudads();
  }

  public clear(): void {
    this.retrieveAllCiudads();
  }

  public retrieveAllCiudads(): void {
    this.isFetching = true;
    this.ciudadService()
      .retrieve()
      .then(
        res => {
          this.ciudads = res.data;
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

  public prepareRemove(instance: ICiudad): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCiudad(): void {
    this.ciudadService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.ciudad.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCiudads();
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
