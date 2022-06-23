import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISeccion } from '@/shared/model/seccion.model';

import SeccionService from './seccion.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Seccion extends Vue {
  @Inject('seccionService') private seccionService: () => SeccionService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public seccions: ISeccion[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSeccions();
  }

  public clear(): void {
    this.retrieveAllSeccions();
  }

  public retrieveAllSeccions(): void {
    this.isFetching = true;
    this.seccionService()
      .retrieve()
      .then(
        res => {
          this.seccions = res.data;
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

  public prepareRemove(instance: ISeccion): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSeccion(): void {
    this.seccionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.seccion.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSeccions();
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
