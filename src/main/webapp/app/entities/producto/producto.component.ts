import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProducto } from '@/shared/model/producto.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ProductoService from './producto.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Producto extends mixins(JhiDataUtils) {
  @Inject('productoService') private productoService: () => ProductoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public productos: IProducto[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProductos();
  }

  public clear(): void {
    this.retrieveAllProductos();
  }

  public retrieveAllProductos(): void {
    this.isFetching = true;
    this.productoService()
      .retrieve()
      .then(
        res => {
          this.productos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IProducto): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProducto(): void {
    this.productoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.producto.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProductos();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
