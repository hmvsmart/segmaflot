import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPersona } from '@/shared/model/persona.model';
import PersonaService from './persona.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PersonaDetails extends Vue {
  @Inject('personaService') private personaService: () => PersonaService;
  @Inject('alertService') private alertService: () => AlertService;

  public persona: IPersona = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personaId) {
        vm.retrievePersona(to.params.personaId);
      }
    });
  }

  public retrievePersona(personaId) {
    this.personaService()
      .find(personaId)
      .then(res => {
        this.persona = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
