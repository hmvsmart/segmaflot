import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAditamentoUnidad } from '@/shared/model/aditamento-unidad.model';
import AditamentoUnidadService from './aditamento-unidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AditamentoUnidadDetails extends Vue {
  @Inject('aditamentoUnidadService') private aditamentoUnidadService: () => AditamentoUnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public aditamentoUnidad: IAditamentoUnidad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aditamentoUnidadId) {
        vm.retrieveAditamentoUnidad(to.params.aditamentoUnidadId);
      }
    });
  }

  public retrieveAditamentoUnidad(aditamentoUnidadId) {
    this.aditamentoUnidadService()
      .find(aditamentoUnidadId)
      .then(res => {
        this.aditamentoUnidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
