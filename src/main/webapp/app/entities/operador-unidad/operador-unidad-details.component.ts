import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOperadorUnidad } from '@/shared/model/operador-unidad.model';
import OperadorUnidadService from './operador-unidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OperadorUnidadDetails extends Vue {
  @Inject('operadorUnidadService') private operadorUnidadService: () => OperadorUnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public operadorUnidad: IOperadorUnidad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operadorUnidadId) {
        vm.retrieveOperadorUnidad(to.params.operadorUnidadId);
      }
    });
  }

  public retrieveOperadorUnidad(operadorUnidadId) {
    this.operadorUnidadService()
      .find(operadorUnidadId)
      .then(res => {
        this.operadorUnidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
