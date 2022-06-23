import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEstado } from '@/shared/model/estado.model';

import EstadoService from './estado.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Estado extends Vue {
  @Inject('estadoService') private estadoService: () => EstadoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public estados: IEstado[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEstados();
  }

  public clear(): void {
    this.retrieveAllEstados();
  }

  public retrieveAllEstados(): void {
    this.isFetching = true;
    this.estadoService()
      .retrieve()
      .then(
        res => {
          this.estados = res.data;
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

  public prepareRemove(instance: IEstado): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEstado(): void {
    this.estadoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.estado.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEstados();
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
