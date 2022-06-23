import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IViaje } from '@/shared/model/viaje.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ViajeService from './viaje.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Viaje extends mixins(JhiDataUtils) {
  @Inject('viajeService') private viajeService: () => ViajeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public viajes: IViaje[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllViajes();
  }

  public clear(): void {
    this.retrieveAllViajes();
  }

  public retrieveAllViajes(): void {
    this.isFetching = true;
    this.viajeService()
      .retrieve()
      .then(
        res => {
          this.viajes = res.data;
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

  public prepareRemove(instance: IViaje): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeViaje(): void {
    this.viajeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.viaje.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllViajes();
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
