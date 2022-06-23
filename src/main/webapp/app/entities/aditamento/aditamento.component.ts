import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAditamento } from '@/shared/model/aditamento.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import AditamentoService from './aditamento.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Aditamento extends mixins(JhiDataUtils) {
  @Inject('aditamentoService') private aditamentoService: () => AditamentoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public aditamentos: IAditamento[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAditamentos();
  }

  public clear(): void {
    this.retrieveAllAditamentos();
  }

  public retrieveAllAditamentos(): void {
    this.isFetching = true;
    this.aditamentoService()
      .retrieve()
      .then(
        res => {
          this.aditamentos = res.data;
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

  public prepareRemove(instance: IAditamento): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAditamento(): void {
    this.aditamentoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.aditamento.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAditamentos();
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
