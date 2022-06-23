import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IServicio } from '@/shared/model/servicio.model';
import ServicioService from './servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ServicioDetails extends mixins(JhiDataUtils) {
  @Inject('servicioService') private servicioService: () => ServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public servicio: IServicio = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.servicioId) {
        vm.retrieveServicio(to.params.servicioId);
      }
    });
  }

  public retrieveServicio(servicioId) {
    this.servicioService()
      .find(servicioId)
      .then(res => {
        this.servicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
