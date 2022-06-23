import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListaPedido } from '@/shared/model/lista-pedido.model';
import ListaPedidoService from './lista-pedido.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ListaPedidoDetails extends Vue {
  @Inject('listaPedidoService') private listaPedidoService: () => ListaPedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  public listaPedido: IListaPedido = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listaPedidoId) {
        vm.retrieveListaPedido(to.params.listaPedidoId);
      }
    });
  }

  public retrieveListaPedido(listaPedidoId) {
    this.listaPedidoService()
      .find(listaPedidoId)
      .then(res => {
        this.listaPedido = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
