import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISeccion } from '@/shared/model/seccion.model';
import SeccionService from './seccion.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SeccionDetails extends Vue {
  @Inject('seccionService') private seccionService: () => SeccionService;
  @Inject('alertService') private alertService: () => AlertService;

  public seccion: ISeccion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.seccionId) {
        vm.retrieveSeccion(to.params.seccionId);
      }
    });
  }

  public retrieveSeccion(seccionId) {
    this.seccionService()
      .find(seccionId)
      .then(res => {
        this.seccion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
