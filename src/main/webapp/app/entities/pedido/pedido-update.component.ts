import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ListaPedidoService from '@/entities/lista-pedido/lista-pedido.service';
import { IListaPedido } from '@/shared/model/lista-pedido.model';

import VentaPedidoService from '@/entities/venta-pedido/venta-pedido.service';
import { IVentaPedido } from '@/shared/model/venta-pedido.model';

import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

import { IPedido, Pedido } from '@/shared/model/pedido.model';
import PedidoService from './pedido.service';

const validations: any = {
  pedido: {
    fecha: {},
    createByUser: {},
    createdOn: {},
    modifyByUser: {},
    modifiedOn: {},
    auditStatus: {},
  },
};

@Component({
  validations,
})
export default class PedidoUpdate extends Vue {
  @Inject('pedidoService') private pedidoService: () => PedidoService;
  @Inject('alertService') private alertService: () => AlertService;

  public pedido: IPedido = new Pedido();

  @Inject('listaPedidoService') private listaPedidoService: () => ListaPedidoService;

  public listaPedidos: IListaPedido[] = [];

  @Inject('ventaPedidoService') private ventaPedidoService: () => VentaPedidoService;

  public ventaPedidos: IVentaPedido[] = [];

  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;

  public personaMorals: IPersonaMoral[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pedidoId) {
        vm.retrievePedido(to.params.pedidoId);
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
    if (this.pedido.id) {
      this.pedidoService()
        .update(this.pedido)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.pedido.updated', { param: param.id });
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
      this.pedidoService()
        .create(this.pedido)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.pedido.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.pedido[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.pedido[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.pedido[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.pedido[field] = null;
    }
  }

  public retrievePedido(pedidoId): void {
    this.pedidoService()
      .find(pedidoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.pedido = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.listaPedidoService()
      .retrieve()
      .then(res => {
        this.listaPedidos = res.data;
      });
    this.ventaPedidoService()
      .retrieve()
      .then(res => {
        this.ventaPedidos = res.data;
      });
    this.personaMoralService()
      .retrieve()
      .then(res => {
        this.personaMorals = res.data;
      });
  }
}
