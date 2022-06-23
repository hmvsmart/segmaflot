import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILicenciaManejo } from '@/shared/model/licencia-manejo.model';
import LicenciaManejoService from './licencia-manejo.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class LicenciaManejoDetails extends Vue {
  @Inject('licenciaManejoService') private licenciaManejoService: () => LicenciaManejoService;
  @Inject('alertService') private alertService: () => AlertService;

  public licenciaManejo: ILicenciaManejo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.licenciaManejoId) {
        vm.retrieveLicenciaManejo(to.params.licenciaManejoId);
      }
    });
  }

  public retrieveLicenciaManejo(licenciaManejoId) {
    this.licenciaManejoService()
      .find(licenciaManejoId)
      .then(res => {
        this.licenciaManejo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
