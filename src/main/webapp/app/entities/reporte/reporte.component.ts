import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IReporte } from '@/shared/model/reporte.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ReporteService from './reporte.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Reporte extends mixins(JhiDataUtils) {
  @Inject('reporteService') private reporteService: () => ReporteService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public reportes: IReporte[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllReportes();
  }

  public clear(): void {
    this.retrieveAllReportes();
  }

  public retrieveAllReportes(): void {
    this.isFetching = true;
    this.reporteService()
      .retrieve()
      .then(
        res => {
          this.reportes = res.data;
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

  public prepareRemove(instance: IReporte): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeReporte(): void {
    this.reporteService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.reporte.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllReportes();
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
