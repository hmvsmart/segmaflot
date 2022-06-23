import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPersonaFisica } from '@/shared/model/persona-fisica.model';
import PersonaFisicaService from './persona-fisica.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PersonaFisicaDetails extends Vue {
  @Inject('personaFisicaService') private personaFisicaService: () => PersonaFisicaService;
  @Inject('alertService') private alertService: () => AlertService;

  public personaFisica: IPersonaFisica = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personaFisicaId) {
        vm.retrievePersonaFisica(to.params.personaFisicaId);
      }
    });
  }

  public retrievePersonaFisica(personaFisicaId) {
    this.personaFisicaService()
      .find(personaFisicaId)
      .then(res => {
        this.personaFisica = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
