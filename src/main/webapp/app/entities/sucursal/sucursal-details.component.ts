import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISucursal } from '@/shared/model/sucursal.model';
import SucursalService from './sucursal.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SucursalDetails extends Vue {
  @Inject('sucursalService') private sucursalService: () => SucursalService;
  @Inject('alertService') private alertService: () => AlertService;

  public sucursal: ISucursal = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sucursalId) {
        vm.retrieveSucursal(to.params.sucursalId);
      }
    });
  }

  public retrieveSucursal(sucursalId) {
    this.sucursalService()
      .find(sucursalId)
      .then(res => {
        this.sucursal = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
