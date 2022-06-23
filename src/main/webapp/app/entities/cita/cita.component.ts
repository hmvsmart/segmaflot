import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICita } from '@/shared/model/cita.model';

import CitaService from './cita.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cita extends Vue {
  @Inject('citaService') private citaService: () => CitaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public citas: ICita[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCitas();
  }

  public clear(): void {
    this.retrieveAllCitas();
  }

  public retrieveAllCitas(): void {
    this.isFetching = true;
    this.citaService()
      .retrieve()
      .then(
        res => {
          this.citas = res.data;
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

  public prepareRemove(instance: ICita): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCita(): void {
    this.citaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.cita.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCitas();
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
