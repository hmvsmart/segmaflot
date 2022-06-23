import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UbicacionInventarioService from '@/entities/ubicacion-inventario/ubicacion-inventario.service';
import { IUbicacionInventario } from '@/shared/model/ubicacion-inventario.model';

import ResurtirService from '@/entities/resurtir/resurtir.service';
import { IResurtir } from '@/shared/model/resurtir.model';

import PrecioVentaService from '@/entities/precio-venta/precio-venta.service';
import { IPrecioVenta } from '@/shared/model/precio-venta.model';

import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';
import { IMaterialServicio } from '@/shared/model/material-servicio.model';

import PerdidaService from '@/entities/perdida/perdida.service';
import { IPerdida } from '@/shared/model/perdida.model';

import ListaPedidoService from '@/entities/lista-pedido/lista-pedido.service';
import { IListaPedido } from '@/shared/model/lista-pedido.model';

import ProductoService from '@/entities/producto/producto.service';
import { IProducto } from '@/shared/model/producto.model';

import SucursalService from '@/entities/sucursal/sucursal.service';
import { ISucursal } from '@/shared/model/sucursal.model';

import { IInventario, Inventario } from '@/shared/model/inventario.model';
import InventarioService from './inventario.service';

const validations: any = {
  inventario: {
    cantidad: {},
    cantidadMinimaEst: {},
    cantidadMaximaEst: {},
    status: {},
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
export default class InventarioUpdate extends Vue {
  @Inject('inventarioService') private inventarioService: () => InventarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public inventario: IInventario = new Inventario();

  @Inject('ubicacionInventarioService') private ubicacionInventarioService: () => UbicacionInventarioService;

  public ubicacionInventarios: IUbicacionInventario[] = [];

  @Inject('resurtirService') private resurtirService: () => ResurtirService;

  public resurtirs: IResurtir[] = [];

  @Inject('precioVentaService') private precioVentaService: () => PrecioVentaService;

  public precioVentas: IPrecioVenta[] = [];

  @Inject('materialServicioService') private materialServicioService: () => MaterialServicioService;

  public materialServicios: IMaterialServicio[] = [];

  @Inject('perdidaService') private perdidaService: () => PerdidaService;

  public perdidas: IPerdida[] = [];

  @Inject('listaPedidoService') private listaPedidoService: () => ListaPedidoService;

  public listaPedidos: IListaPedido[] = [];

  @Inject('productoService') private productoService: () => ProductoService;

  public productos: IProducto[] = [];

  @Inject('sucursalService') private sucursalService: () => SucursalService;

  public sucursals: ISucursal[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.inventarioId) {
        vm.retrieveInventario(to.params.inventarioId);
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
    if (this.inventario.id) {
      this.inventarioService()
        .update(this.inventario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.inventario.updated', { param: param.id });
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
      this.inventarioService()
        .create(this.inventario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.inventario.created', { param: param.id });
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
      this.inventario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.inventario[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.inventario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.inventario[field] = null;
    }
  }

  public retrieveInventario(inventarioId): void {
    this.inventarioService()
      .find(inventarioId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.inventario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ubicacionInventarioService()
      .retrieve()
      .then(res => {
        this.ubicacionInventarios = res.data;
      });
    this.resurtirService()
      .retrieve()
      .then(res => {
        this.resurtirs = res.data;
      });
    this.precioVentaService()
      .retrieve()
      .then(res => {
        this.precioVentas = res.data;
      });
    this.materialServicioService()
      .retrieve()
      .then(res => {
        this.materialServicios = res.data;
      });
    this.perdidaService()
      .retrieve()
      .then(res => {
        this.perdidas = res.data;
      });
    this.listaPedidoService()
      .retrieve()
      .then(res => {
        this.listaPedidos = res.data;
      });
    this.productoService()
      .retrieve()
      .then(res => {
        this.productos = res.data;
      });
    this.sucursalService()
      .retrieve()
      .then(res => {
        this.sucursals = res.data;
      });
  }
}
