import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEmpleado } from '@/shared/model/empleado.model';
import EmpleadoService from './empleado.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EmpleadoDetails extends Vue {
  @Inject('empleadoService') private empleadoService: () => EmpleadoService;
  @Inject('alertService') private alertService: () => AlertService;

  public empleado: IEmpleado = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.empleadoId) {
        vm.retrieveEmpleado(to.params.empleadoId);
      }
    });
  }

  public retrieveEmpleado(empleadoId) {
    this.empleadoService()
      .find(empleadoId)
      .then(res => {
        this.empleado = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
