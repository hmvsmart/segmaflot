import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMaterialServicio } from '@/shared/model/material-servicio.model';
import MaterialServicioService from './material-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MaterialServicioDetails extends Vue {
  @Inject('materialServicioService') private materialServicioService: () => MaterialServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public materialServicio: IMaterialServicio = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.materialServicioId) {
        vm.retrieveMaterialServicio(to.params.materialServicioId);
      }
    });
  }

  public retrieveMaterialServicio(materialServicioId) {
    this.materialServicioService()
      .find(materialServicioId)
      .then(res => {
        this.materialServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
