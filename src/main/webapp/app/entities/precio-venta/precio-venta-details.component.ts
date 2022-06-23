import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrecioVenta } from '@/shared/model/precio-venta.model';
import PrecioVentaService from './precio-venta.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PrecioVentaDetails extends Vue {
  @Inject('precioVentaService') private precioVentaService: () => PrecioVentaService;
  @Inject('alertService') private alertService: () => AlertService;

  public precioVenta: IPrecioVenta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.precioVentaId) {
        vm.retrievePrecioVenta(to.params.precioVentaId);
      }
    });
  }

  public retrievePrecioVenta(precioVentaId) {
    this.precioVentaService()
      .find(precioVentaId)
      .then(res => {
        this.precioVenta = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
