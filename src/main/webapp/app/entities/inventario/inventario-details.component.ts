import { Component, Vue, Inject } from 'vue-property-decorator';

import { IInventario } from '@/shared/model/inventario.model';
import InventarioService from './inventario.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class InventarioDetails extends Vue {
  @Inject('inventarioService') private inventarioService: () => InventarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public inventario: IInventario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.inventarioId) {
        vm.retrieveInventario(to.params.inventarioId);
      }
    });
  }

  public retrieveInventario(inventarioId) {
    this.inventarioService()
      .find(inventarioId)
      .then(res => {
        this.inventario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
