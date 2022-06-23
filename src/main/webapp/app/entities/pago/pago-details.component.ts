import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPago } from '@/shared/model/pago.model';
import PagoService from './pago.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PagoDetails extends Vue {
  @Inject('pagoService') private pagoService: () => PagoService;
  @Inject('alertService') private alertService: () => AlertService;

  public pago: IPago = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pagoId) {
        vm.retrievePago(to.params.pagoId);
      }
    });
  }

  public retrievePago(pagoId) {
    this.pagoService()
      .find(pagoId)
      .then(res => {
        this.pago = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
