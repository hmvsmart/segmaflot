import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUbicacionInventario } from '@/shared/model/ubicacion-inventario.model';
import UbicacionInventarioService from './ubicacion-inventario.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UbicacionInventarioDetails extends Vue {
  @Inject('ubicacionInventarioService') private ubicacionInventarioService: () => UbicacionInventarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public ubicacionInventario: IUbicacionInventario = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ubicacionInventarioId) {
        vm.retrieveUbicacionInventario(to.params.ubicacionInventarioId);
      }
    });
  }

  public retrieveUbicacionInventario(ubicacionInventarioId) {
    this.ubicacionInventarioService()
      .find(ubicacionInventarioId)
      .then(res => {
        this.ubicacionInventario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
