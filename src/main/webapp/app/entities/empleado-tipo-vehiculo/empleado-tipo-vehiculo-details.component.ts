import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmpleadoTipoVehiculo } from '@/shared/model/empleado-tipo-vehiculo.model';
import EmpleadoTipoVehiculoService from './empleado-tipo-vehiculo.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EmpleadoTipoVehiculoDetails extends Vue {
  @Inject('empleadoTipoVehiculoService') private empleadoTipoVehiculoService: () => EmpleadoTipoVehiculoService;
  @Inject('alertService') private alertService: () => AlertService;

  public empleadoTipoVehiculo: IEmpleadoTipoVehiculo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.empleadoTipoVehiculoId) {
        vm.retrieveEmpleadoTipoVehiculo(to.params.empleadoTipoVehiculoId);
      }
    });
  }

  public retrieveEmpleadoTipoVehiculo(empleadoTipoVehiculoId) {
    this.empleadoTipoVehiculoService()
      .find(empleadoTipoVehiculoId)
      .then(res => {
        this.empleadoTipoVehiculo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
