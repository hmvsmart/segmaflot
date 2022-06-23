import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVehiculo } from '@/shared/model/vehiculo.model';
import VehiculoService from './vehiculo.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VehiculoDetails extends Vue {
  @Inject('vehiculoService') private vehiculoService: () => VehiculoService;
  @Inject('alertService') private alertService: () => AlertService;

  public vehiculo: IVehiculo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vehiculoId) {
        vm.retrieveVehiculo(to.params.vehiculoId);
      }
    });
  }

  public retrieveVehiculo(vehiculoId) {
    this.vehiculoService()
      .find(vehiculoId)
      .then(res => {
        this.vehiculo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
