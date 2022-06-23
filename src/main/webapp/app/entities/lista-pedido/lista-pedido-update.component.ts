import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PedidoService from '@/entities/pedido/pedido.service';
import { IPedido } from '@/shared/model/pedido.model';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import { IListaPedido, ListaPedido } from '@/shared/model/lista-pedido.model';
import ListaPedidoService from './lista-pedido.service';

const validations: any = {
  listaPedido: {
    cantidad: {},
  },
};

@Component({
  validations,
})
export default class ListaPedidoUpdate extends Vue {
  @Inject('listaPedidoService') private listaPedidoService: () => ListaPedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  public listaPedido: IListaPedido = new ListaPedido();

  @Inject('pedidoService') private pedidoService: () => PedidoService;

  public pedidos: IPedido[] = [];

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listaPedidoId) {
        vm.retrieveListaPedido(to.params.listaPedidoId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.listaPedido.id) {
      this.listaPedidoService()
        .update(this.listaPedido)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.listaPedido.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.listaPedidoService()
        .create(this.listaPedido)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.listaPedido.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveListaPedido(listaPedidoId): void {
    this.listaPedidoService()
      .find(listaPedidoId)
      .then(res => {
        this.listaPedido = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.pedidoService()
      .retrieve()
      .then(res => {
        this.pedidos = res.data;
      });
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
  }
}
