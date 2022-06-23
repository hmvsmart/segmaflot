import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import { IProducto, Producto } from '@/shared/model/producto.model';
import ProductoService from './producto.service';

const validations: any = {
  producto: {
    nombre: {},
    marca: {},
    tipo: {},
    cantidad: {},
    unidadMedida: {},
    material: {},
    color: {},
    uso: {},
    descripcion: {},
    otros: {},
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
export default class ProductoUpdate extends mixins(JhiDataUtils) {
  @Inject('productoService') private productoService: () => ProductoService;
  @Inject('alertService') private alertService: () => AlertService;

  public producto: IProducto = new Producto();

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productoId) {
        vm.retrieveProducto(to.params.productoId);
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
    if (this.producto.id) {
      this.productoService()
        .update(this.producto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.producto.updated', { param: param.id });
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
      this.productoService()
        .create(this.producto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.producto.created', { param: param.id });
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
      this.producto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.producto[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.producto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.producto[field] = null;
    }
  }

  public retrieveProducto(productoId): void {
    this.productoService()
      .find(productoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.producto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
  }
}
