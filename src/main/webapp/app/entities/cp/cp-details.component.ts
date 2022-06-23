import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICP } from '@/shared/model/cp.model';
import CPService from './cp.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CPDetails extends Vue {
  @Inject('cPService') private cPService: () => CPService;
  @Inject('alertService') private alertService: () => AlertService;

  public cP: ICP = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cPId) {
        vm.retrieveCP(to.params.cPId);
      }
    });
  }

  public retrieveCP(cPId) {
    this.cPService()
      .find(cPId)
      .then(res => {
        this.cP = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
