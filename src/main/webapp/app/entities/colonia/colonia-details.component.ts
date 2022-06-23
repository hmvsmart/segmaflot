import { Component, Vue, Inject } from 'vue-property-decorator';

import { IColonia } from '@/shared/model/colonia.model';
import ColoniaService from './colonia.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ColoniaDetails extends Vue {
  @Inject('coloniaService') private coloniaService: () => ColoniaService;
  @Inject('alertService') private alertService: () => AlertService;

  public colonia: IColonia = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.coloniaId) {
        vm.retrieveColonia(to.params.coloniaId);
      }
    });
  }

  public retrieveColonia(coloniaId) {
    this.coloniaService()
      .find(coloniaId)
      .then(res => {
        this.colonia = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
