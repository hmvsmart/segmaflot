import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ILicenciaManejo } from '@/shared/model/licencia-manejo.model';

import LicenciaManejoService from './licencia-manejo.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class LicenciaManejo extends Vue {
  @Inject('licenciaManejoService') private licenciaManejoService: () => LicenciaManejoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public licenciaManejos: ILicenciaManejo[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllLicenciaManejos();
  }

  public clear(): void {
    this.retrieveAllLicenciaManejos();
  }

  public retrieveAllLicenciaManejos(): void {
    this.isFetching = true;
    this.licenciaManejoService()
      .retrieve()
      .then(
        res => {
          this.licenciaManejos = res.data;
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

  public prepareRemove(instance: ILicenciaManejo): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeLicenciaManejo(): void {
    this.licenciaManejoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.licenciaManejo.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllLicenciaManejos();
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
