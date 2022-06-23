import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDireccion } from '@/shared/model/direccion.model';
import DireccionService from './direccion.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DireccionDetails extends Vue {
  @Inject('direccionService') private direccionService: () => DireccionService;
  @Inject('alertService') private alertService: () => AlertService;

  public direccion: IDireccion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.direccionId) {
        vm.retrieveDireccion(to.params.direccionId);
      }
    });
  }

  public retrieveDireccion(direccionId) {
    this.direccionService()
      .find(direccionId)
      .then(res => {
        this.direccion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
