import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITipoContacto } from '@/shared/model/tipo-contacto.model';
import TipoContactoService from './tipo-contacto.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TipoContactoDetails extends Vue {
  @Inject('tipoContactoService') private tipoContactoService: () => TipoContactoService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoContacto: ITipoContacto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoContactoId) {
        vm.retrieveTipoContacto(to.params.tipoContactoId);
      }
    });
  }

  public retrieveTipoContacto(tipoContactoId) {
    this.tipoContactoService()
      .find(tipoContactoId)
      .then(res => {
        this.tipoContacto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
