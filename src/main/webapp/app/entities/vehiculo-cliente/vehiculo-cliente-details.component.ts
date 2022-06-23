import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVehiculoCliente } from '@/shared/model/vehiculo-cliente.model';
import VehiculoClienteService from './vehiculo-cliente.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VehiculoClienteDetails extends Vue {
  @Inject('vehiculoClienteService') private vehiculoClienteService: () => VehiculoClienteService;
  @Inject('alertService') private alertService: () => AlertService;

  public vehiculoCliente: IVehiculoCliente = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vehiculoClienteId) {
        vm.retrieveVehiculoCliente(to.params.vehiculoClienteId);
      }
    });
  }

  public retrieveVehiculoCliente(vehiculoClienteId) {
    this.vehiculoClienteService()
      .find(vehiculoClienteId)
      .then(res => {
        this.vehiculoCliente = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
