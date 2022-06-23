import { Component, Vue, Inject } from 'vue-property-decorator';

import { IItinerario } from '@/shared/model/itinerario.model';
import ItinerarioService from './itinerario.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ItinerarioDetails extends Vue {
  @Inject('itinerarioService') private itinerarioService: () => ItinerarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public itinerario: IItinerario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.itinerarioId) {
        vm.retrieveItinerario(to.params.itinerarioId);
      }
    });
  }

  public retrieveItinerario(itinerarioId) {
    this.itinerarioService()
      .find(itinerarioId)
      .then(res => {
        this.itinerario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
