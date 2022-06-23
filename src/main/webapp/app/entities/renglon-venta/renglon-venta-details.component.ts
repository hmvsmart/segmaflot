import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRenglonVenta } from '@/shared/model/renglon-venta.model';
import RenglonVentaService from './renglon-venta.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RenglonVentaDetails extends Vue {
  @Inject('renglonVentaService') private renglonVentaService: () => RenglonVentaService;
  @Inject('alertService') private alertService: () => AlertService;

  public renglonVenta: IRenglonVenta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.renglonVentaId) {
        vm.retrieveRenglonVenta(to.params.renglonVentaId);
      }
    });
  }

  public retrieveRenglonVenta(renglonVentaId) {
    this.renglonVentaService()
      .find(renglonVentaId)
      .then(res => {
        this.renglonVenta = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
