import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAditamentoUnidad } from '@/shared/model/aditamento-unidad.model';

import AditamentoUnidadService from './aditamento-unidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class AditamentoUnidad extends Vue {
  @Inject('aditamentoUnidadService') private aditamentoUnidadService: () => AditamentoUnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public aditamentoUnidads: IAditamentoUnidad[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAditamentoUnidads();
  }

  public clear(): void {
    this.retrieveAllAditamentoUnidads();
  }

  public retrieveAllAditamentoUnidads(): void {
    this.isFetching = true;
    this.aditamentoUnidadService()
      .retrieve()
      .then(
        res => {
          this.aditamentoUnidads = res.data;
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

  public prepareRemove(instance: IAditamentoUnidad): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAditamentoUnidad(): void {
    this.aditamentoUnidadService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.aditamentoUnidad.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAditamentoUnidads();
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
