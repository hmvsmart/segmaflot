import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IAditamento } from '@/shared/model/aditamento.model';
import AditamentoService from './aditamento.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AditamentoDetails extends mixins(JhiDataUtils) {
  @Inject('aditamentoService') private aditamentoService: () => AditamentoService;
  @Inject('alertService') private alertService: () => AlertService;

  public aditamento: IAditamento = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.aditamentoId) {
        vm.retrieveAditamento(to.params.aditamentoId);
      }
    });
  }

  public retrieveAditamento(aditamentoId) {
    this.aditamentoService()
      .find(aditamentoId)
      .then(res => {
        this.aditamento = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
