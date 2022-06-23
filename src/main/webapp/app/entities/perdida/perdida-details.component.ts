import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IPerdida } from '@/shared/model/perdida.model';
import PerdidaService from './perdida.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PerdidaDetails extends mixins(JhiDataUtils) {
  @Inject('perdidaService') private perdidaService: () => PerdidaService;
  @Inject('alertService') private alertService: () => AlertService;

  public perdida: IPerdida = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.perdidaId) {
        vm.retrievePerdida(to.params.perdidaId);
      }
    });
  }

  public retrievePerdida(perdidaId) {
    this.perdidaService()
      .find(perdidaId)
      .then(res => {
        this.perdida = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
