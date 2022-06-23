import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEstante } from '@/shared/model/estante.model';
import EstanteService from './estante.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EstanteDetails extends Vue {
  @Inject('estanteService') private estanteService: () => EstanteService;
  @Inject('alertService') private alertService: () => AlertService;

  public estante: IEstante = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estanteId) {
        vm.retrieveEstante(to.params.estanteId);
      }
    });
  }

  public retrieveEstante(estanteId) {
    this.estanteService()
      .find(estanteId)
      .then(res => {
        this.estante = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
