import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';

import UnidadViajeService from './unidad-viaje.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class UnidadViaje extends Vue {
  @Inject('unidadViajeService') private unidadViajeService: () => UnidadViajeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public unidadViajes: IUnidadViaje[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllUnidadViajes();
  }

  public clear(): void {
    this.retrieveAllUnidadViajes();
  }

  public retrieveAllUnidadViajes(): void {
    this.isFetching = true;
    this.unidadViajeService()
      .retrieve()
      .then(
        res => {
          this.unidadViajes = res.data;
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

  public prepareRemove(instance: IUnidadViaje): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeUnidadViaje(): void {
    this.unidadViajeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.unidadViaje.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllUnidadViajes();
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
