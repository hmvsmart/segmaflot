import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IViaje } from '@/shared/model/viaje.model';
import ViajeService from './viaje.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ViajeDetails extends mixins(JhiDataUtils) {
  @Inject('viajeService') private viajeService: () => ViajeService;
  @Inject('alertService') private alertService: () => AlertService;

  public viaje: IViaje = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.viajeId) {
        vm.retrieveViaje(to.params.viajeId);
      }
    });
  }

  public retrieveViaje(viajeId) {
    this.viajeService()
      .find(viajeId)
      .then(res => {
        this.viaje = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
