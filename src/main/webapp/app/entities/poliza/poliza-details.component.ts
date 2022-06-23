import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPoliza } from '@/shared/model/poliza.model';
import PolizaService from './poliza.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PolizaDetails extends Vue {
  @Inject('polizaService') private polizaService: () => PolizaService;
  @Inject('alertService') private alertService: () => AlertService;

  public poliza: IPoliza = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.polizaId) {
        vm.retrievePoliza(to.params.polizaId);
      }
    });
  }

  public retrievePoliza(polizaId) {
    this.polizaService()
      .find(polizaId)
      .then(res => {
        this.poliza = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
