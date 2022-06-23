import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

import PersonaMoralService from './persona-moral.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PersonaMoral extends Vue {
  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public personaMorals: IPersonaMoral[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPersonaMorals();
  }

  public clear(): void {
    this.retrieveAllPersonaMorals();
  }

  public retrieveAllPersonaMorals(): void {
    this.isFetching = true;
    this.personaMoralService()
      .retrieve()
      .then(
        res => {
          this.personaMorals = res.data;
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

  public prepareRemove(instance: IPersonaMoral): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePersonaMoral(): void {
    this.personaMoralService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.personaMoral.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPersonaMorals();
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
