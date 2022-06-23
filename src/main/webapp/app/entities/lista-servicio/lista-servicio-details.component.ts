import { Component, Vue, Inject } from 'vue-property-decorator';

import { IListaServicio } from '@/shared/model/lista-servicio.model';
import ListaServicioService from './lista-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ListaServicioDetails extends Vue {
  @Inject('listaServicioService') private listaServicioService: () => ListaServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public listaServicio: IListaServicio = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.listaServicioId) {
        vm.retrieveListaServicio(to.params.listaServicioId);
      }
    });
  }

  public retrieveListaServicio(listaServicioId) {
    this.listaServicioService()
      .find(listaServicioId)
      .then(res => {
        this.listaServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
