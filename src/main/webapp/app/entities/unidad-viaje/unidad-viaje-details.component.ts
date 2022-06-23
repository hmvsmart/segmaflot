import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';
import UnidadViajeService from './unidad-viaje.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UnidadViajeDetails extends Vue {
  @Inject('unidadViajeService') private unidadViajeService: () => UnidadViajeService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidadViaje: IUnidadViaje = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadViajeId) {
        vm.retrieveUnidadViaje(to.params.unidadViajeId);
      }
    });
  }

  public retrieveUnidadViaje(unidadViajeId) {
    this.unidadViajeService()
      .find(unidadViajeId)
      .then(res => {
        this.unidadViaje = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
