import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProducto } from '@/shared/model/producto.model';
import ProductoService from './producto.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ProductoDetails extends mixins(JhiDataUtils) {
  @Inject('productoService') private productoService: () => ProductoService;
  @Inject('alertService') private alertService: () => AlertService;

  public producto: IProducto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productoId) {
        vm.retrieveProducto(to.params.productoId);
      }
    });
  }

  public retrieveProducto(productoId) {
    this.productoService()
      .find(productoId)
      .then(res => {
        this.producto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
