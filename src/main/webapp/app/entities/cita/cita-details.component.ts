import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICita } from '@/shared/model/cita.model';
import CitaService from './cita.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CitaDetails extends Vue {
  @Inject('citaService') private citaService: () => CitaService;
  @Inject('alertService') private alertService: () => AlertService;

  public cita: ICita = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citaId) {
        vm.retrieveCita(to.params.citaId);
      }
    });
  }

  public retrieveCita(citaId) {
    this.citaService()
      .find(citaId)
      .then(res => {
        this.cita = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
