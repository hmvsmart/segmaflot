import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IReporte } from '@/shared/model/reporte.model';
import ReporteService from './reporte.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ReporteDetails extends mixins(JhiDataUtils) {
  @Inject('reporteService') private reporteService: () => ReporteService;
  @Inject('alertService') private alertService: () => AlertService;

  public reporte: IReporte = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reporteId) {
        vm.retrieveReporte(to.params.reporteId);
      }
    });
  }

  public retrieveReporte(reporteId) {
    this.reporteService()
      .find(reporteId)
      .then(res => {
        this.reporte = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
