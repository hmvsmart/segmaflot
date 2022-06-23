import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IColonia } from '@/shared/model/colonia.model';

import ColoniaService from './colonia.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Colonia extends Vue {
  @Inject('coloniaService') private coloniaService: () => ColoniaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public colonias: IColonia[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllColonias();
  }

  public clear(): void {
    this.retrieveAllColonias();
  }

  public retrieveAllColonias(): void {
    this.isFetching = true;
    this.coloniaService()
      .retrieve()
      .then(
        res => {
          this.colonias = res.data;
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

  public prepareRemove(instance: IColonia): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeColonia(): void {
    this.coloniaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.colonia.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllColonias();
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
