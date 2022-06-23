import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrecioServicio } from '@/shared/model/precio-servicio.model';
import PrecioServicioService from './precio-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PrecioServicioDetails extends Vue {
  @Inject('precioServicioService') private precioServicioService: () => PrecioServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public precioServicio: IPrecioServicio = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.precioServicioId) {
        vm.retrievePrecioServicio(to.params.precioServicioId);
      }
    });
  }

  public retrievePrecioServicio(precioServicioId) {
    this.precioServicioService()
      .find(precioServicioId)
      .then(res => {
        this.precioServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
