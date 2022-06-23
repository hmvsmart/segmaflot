import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRenglonResurtir } from '@/shared/model/renglon-resurtir.model';

import RenglonResurtirService from './renglon-resurtir.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RenglonResurtir extends Vue {
  @Inject('renglonResurtirService') private renglonResurtirService: () => RenglonResurtirService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public renglonResurtirs: IRenglonResurtir[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRenglonResurtirs();
  }

  public clear(): void {
    this.retrieveAllRenglonResurtirs();
  }

  public retrieveAllRenglonResurtirs(): void {
    this.isFetching = true;
    this.renglonResurtirService()
      .retrieve()
      .then(
        res => {
          this.renglonResurtirs = res.data;
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

  public prepareRemove(instance: IRenglonResurtir): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRenglonResurtir(): void {
    this.renglonResurtirService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.renglonResurtir.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRenglonResurtirs();
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
