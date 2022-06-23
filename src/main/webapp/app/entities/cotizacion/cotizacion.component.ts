import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICotizacion } from '@/shared/model/cotizacion.model';

import CotizacionService from './cotizacion.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cotizacion extends Vue {
  @Inject('cotizacionService') private cotizacionService: () => CotizacionService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public cotizacions: ICotizacion[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCotizacions();
  }

  public clear(): void {
    this.retrieveAllCotizacions();
  }

  public retrieveAllCotizacions(): void {
    this.isFetching = true;
    this.cotizacionService()
      .retrieve()
      .then(
        res => {
          this.cotizacions = res.data;
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

  public prepareRemove(instance: ICotizacion): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCotizacion(): void {
    this.cotizacionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.cotizacion.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCotizacions();
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
