import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDireccionPersona } from '@/shared/model/direccion-persona.model';
import DireccionPersonaService from './direccion-persona.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DireccionPersonaDetails extends Vue {
  @Inject('direccionPersonaService') private direccionPersonaService: () => DireccionPersonaService;
  @Inject('alertService') private alertService: () => AlertService;

  public direccionPersona: IDireccionPersona = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.direccionPersonaId) {
        vm.retrieveDireccionPersona(to.params.direccionPersonaId);
      }
    });
  }

  public retrieveDireccionPersona(direccionPersonaId) {
    this.direccionPersonaService()
      .find(direccionPersonaId)
      .then(res => {
        this.direccionPersona = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
