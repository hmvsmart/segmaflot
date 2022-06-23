import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IOperadorUnidad } from '@/shared/model/operador-unidad.model';

import OperadorUnidadService from './operador-unidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class OperadorUnidad extends Vue {
  @Inject('operadorUnidadService') private operadorUnidadService: () => OperadorUnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public operadorUnidads: IOperadorUnidad[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllOperadorUnidads();
  }

  public clear(): void {
    this.retrieveAllOperadorUnidads();
  }

  public retrieveAllOperadorUnidads(): void {
    this.isFetching = true;
    this.operadorUnidadService()
      .retrieve()
      .then(
        res => {
          this.operadorUnidads = res.data;
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

  public prepareRemove(instance: IOperadorUnidad): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeOperadorUnidad(): void {
    this.operadorUnidadService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.operadorUnidad.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllOperadorUnidads();
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
