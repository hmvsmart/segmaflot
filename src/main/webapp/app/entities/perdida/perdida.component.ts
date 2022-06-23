import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPerdida } from '@/shared/model/perdida.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import PerdidaService from './perdida.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Perdida extends mixins(JhiDataUtils) {
  @Inject('perdidaService') private perdidaService: () => PerdidaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public perdidas: IPerdida[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPerdidas();
  }

  public clear(): void {
    this.retrieveAllPerdidas();
  }

  public retrieveAllPerdidas(): void {
    this.isFetching = true;
    this.perdidaService()
      .retrieve()
      .then(
        res => {
          this.perdidas = res.data;
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

  public prepareRemove(instance: IPerdida): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePerdida(): void {
    this.perdidaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.perdida.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPerdidas();
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
