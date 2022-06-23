import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICP } from '@/shared/model/cp.model';

import CPService from './cp.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CP extends Vue {
  @Inject('cPService') private cPService: () => CPService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public cPS: ICP[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCPs();
  }

  public clear(): void {
    this.retrieveAllCPs();
  }

  public retrieveAllCPs(): void {
    this.isFetching = true;
    this.cPService()
      .retrieve()
      .then(
        res => {
          this.cPS = res.data;
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

  public prepareRemove(instance: ICP): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCP(): void {
    this.cPService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.cP.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCPs();
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
