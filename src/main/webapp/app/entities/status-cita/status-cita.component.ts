import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStatusCita } from '@/shared/model/status-cita.model';

import StatusCitaService from './status-cita.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StatusCita extends Vue {
  @Inject('statusCitaService') private statusCitaService: () => StatusCitaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public statusCitas: IStatusCita[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStatusCitas();
  }

  public clear(): void {
    this.retrieveAllStatusCitas();
  }

  public retrieveAllStatusCitas(): void {
    this.isFetching = true;
    this.statusCitaService()
      .retrieve()
      .then(
        res => {
          this.statusCitas = res.data;
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

  public prepareRemove(instance: IStatusCita): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStatusCita(): void {
    this.statusCitaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.statusCita.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStatusCitas();
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
