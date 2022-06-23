import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVenta } from '@/shared/model/venta.model';
import VentaService from './venta.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VentaDetails extends Vue {
  @Inject('ventaService') private ventaService: () => VentaService;
  @Inject('alertService') private alertService: () => AlertService;

  public venta: IVenta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ventaId) {
        vm.retrieveVenta(to.params.ventaId);
      }
    });
  }

  public retrieveVenta(ventaId) {
    this.ventaService()
      .find(ventaId)
      .then(res => {
        this.venta = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
