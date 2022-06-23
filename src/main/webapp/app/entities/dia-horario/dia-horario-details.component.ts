import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDiaHorario } from '@/shared/model/dia-horario.model';
import DiaHorarioService from './dia-horario.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DiaHorarioDetails extends Vue {
  @Inject('diaHorarioService') private diaHorarioService: () => DiaHorarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public diaHorario: IDiaHorario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.diaHorarioId) {
        vm.retrieveDiaHorario(to.params.diaHorarioId);
      }
    });
  }

  public retrieveDiaHorario(diaHorarioId) {
    this.diaHorarioService()
      .find(diaHorarioId)
      .then(res => {
        this.diaHorario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
