import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IListaServicio } from '@/shared/model/lista-servicio.model';

import ListaServicioService from './lista-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ListaServicio extends Vue {
  @Inject('listaServicioService') private listaServicioService: () => ListaServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public listaServicios: IListaServicio[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllListaServicios();
  }

  public clear(): void {
    this.retrieveAllListaServicios();
  }

  public retrieveAllListaServicios(): void {
    this.isFetching = true;
    this.listaServicioService()
      .retrieve()
      .then(
        res => {
          this.listaServicios = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IListaServicio): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeListaServicio(): void {
    this.listaServicioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.listaServicio.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllListaServicios();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
