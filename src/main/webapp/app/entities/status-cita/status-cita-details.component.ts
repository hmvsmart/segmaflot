import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStatusCita } from '@/shared/model/status-cita.model';
import StatusCitaService from './status-cita.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class StatusCitaDetails extends Vue {
  @Inject('statusCitaService') private statusCitaService: () => StatusCitaService;
  @Inject('alertService') private alertService: () => AlertService;

  public statusCita: IStatusCita = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.statusCitaId) {
        vm.retrieveStatusCita(to.params.statusCitaId);
      }
    });
  }

  public retrieveStatusCita(statusCitaId) {
    this.statusCitaService()
      .find(statusCitaId)
      .then(res => {
        this.statusCita = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
