import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVehiculo } from '@/shared/model/vehiculo.model';

import VehiculoService from './vehiculo.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Vehiculo extends Vue {
  @Inject('vehiculoService') private vehiculoService: () => VehiculoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public vehiculos: IVehiculo[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVehiculos();
  }

  public clear(): void {
    this.retrieveAllVehiculos();
  }

  public retrieveAllVehiculos(): void {
    this.isFetching = true;
    this.vehiculoService()
      .retrieve()
      .then(
        res => {
          this.vehiculos = res.data;
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

  public prepareRemove(instance: IVehiculo): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVehiculo(): void {
    this.vehiculoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.vehiculo.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVehiculos();
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
