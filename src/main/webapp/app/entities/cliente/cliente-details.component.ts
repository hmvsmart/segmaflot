import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ICliente } from '@/shared/model/cliente.model';
import ClienteService from './cliente.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ClienteDetails extends mixins(JhiDataUtils) {
  @Inject('clienteService') private clienteService: () => ClienteService;
  @Inject('alertService') private alertService: () => AlertService;

  public cliente: ICliente = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clienteId) {
        vm.retrieveCliente(to.params.clienteId);
      }
    });
  }

  public retrieveCliente(clienteId) {
    this.clienteService()
      .find(clienteId)
      .then(res => {
        this.cliente = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
