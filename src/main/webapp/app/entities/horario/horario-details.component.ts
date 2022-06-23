import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHorario } from '@/shared/model/horario.model';
import HorarioService from './horario.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class HorarioDetails extends Vue {
  @Inject('horarioService') private horarioService: () => HorarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public horario: IHorario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.horarioId) {
        vm.retrieveHorario(to.params.horarioId);
      }
    });
  }

  public retrieveHorario(horarioId) {
    this.horarioService()
      .find(horarioId)
      .then(res => {
        this.horario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
