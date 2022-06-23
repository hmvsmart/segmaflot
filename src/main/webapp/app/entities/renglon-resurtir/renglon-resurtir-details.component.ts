import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRenglonResurtir } from '@/shared/model/renglon-resurtir.model';
import RenglonResurtirService from './renglon-resurtir.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RenglonResurtirDetails extends Vue {
  @Inject('renglonResurtirService') private renglonResurtirService: () => RenglonResurtirService;
  @Inject('alertService') private alertService: () => AlertService;

  public renglonResurtir: IRenglonResurtir = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.renglonResurtirId) {
        vm.retrieveRenglonResurtir(to.params.renglonResurtirId);
      }
    });
  }

  public retrieveRenglonResurtir(renglonResurtirId) {
    this.renglonResurtirService()
      .find(renglonResurtirId)
      .then(res => {
        this.renglonResurtir = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
