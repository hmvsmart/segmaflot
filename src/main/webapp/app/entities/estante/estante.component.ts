import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEstante } from '@/shared/model/estante.model';

import EstanteService from './estante.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Estante extends Vue {
  @Inject('estanteService') private estanteService: () => EstanteService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public estantes: IEstante[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEstantes();
  }

  public clear(): void {
    this.retrieveAllEstantes();
  }

  public retrieveAllEstantes(): void {
    this.isFetching = true;
    this.estanteService()
      .retrieve()
      .then(
        res => {
          this.estantes = res.data;
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

  public prepareRemove(instance: IEstante): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEstante(): void {
    this.estanteService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.estante.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEstantes();
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
