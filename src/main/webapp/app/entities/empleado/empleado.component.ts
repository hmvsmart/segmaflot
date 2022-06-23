import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEmpleado } from '@/shared/model/empleado.model';

import EmpleadoService from './empleado.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Empleado extends Vue {
  @Inject('empleadoService') private empleadoService: () => EmpleadoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public empleados: IEmpleado[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEmpleados();
  }

  public clear(): void {
    this.retrieveAllEmpleados();
  }

  public retrieveAllEmpleados(): void {
    this.isFetching = true;
    this.empleadoService()
      .retrieve()
      .then(
        res => {
          this.empleados = res.data;
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

  public prepareRemove(instance: IEmpleado): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEmpleado(): void {
    this.empleadoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.empleado.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEmpleados();
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
