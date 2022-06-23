import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICotizacion } from '@/shared/model/cotizacion.model';
import CotizacionService from './cotizacion.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CotizacionDetails extends Vue {
  @Inject('cotizacionService') private cotizacionService: () => CotizacionService;
  @Inject('alertService') private alertService: () => AlertService;

  public cotizacion: ICotizacion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cotizacionId) {
        vm.retrieveCotizacion(to.params.cotizacionId);
      }
    });
  }

  public retrieveCotizacion(cotizacionId) {
    this.cotizacionService()
      .find(cotizacionId)
      .then(res => {
        this.cotizacion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
