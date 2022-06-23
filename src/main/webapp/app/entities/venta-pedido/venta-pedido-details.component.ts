import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVentaPedido } from '@/shared/model/venta-pedido.model';
import VentaPedidoService from './venta-pedido.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class VentaPedidoDetails extends Vue {
  @Inject('ventaPedidoService') private ventaPedidoService: () => VentaPedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  public ventaPedido: IVentaPedido = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ventaPedidoId) {
        vm.retrieveVentaPedido(to.params.ventaPedidoId);
      }
    });
  }

  public retrieveVentaPedido(ventaPedidoId) {
    this.ventaPedidoService()
      .find(ventaPedidoId)
      .then(res => {
        this.ventaPedido = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
