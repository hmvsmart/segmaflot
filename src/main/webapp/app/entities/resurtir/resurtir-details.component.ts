import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResurtir } from '@/shared/model/resurtir.model';
import ResurtirService from './resurtir.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ResurtirDetails extends Vue {
  @Inject('resurtirService') private resurtirService: () => ResurtirService;
  @Inject('alertService') private alertService: () => AlertService;

  public resurtir: IResurtir = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resurtirId) {
        vm.retrieveResurtir(to.params.resurtirId);
      }
    });
  }

  public retrieveResurtir(resurtirId) {
    this.resurtirService()
      .find(resurtirId)
      .then(res => {
        this.resurtir = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
