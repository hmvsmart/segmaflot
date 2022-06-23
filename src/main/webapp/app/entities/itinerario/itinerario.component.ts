import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IItinerario } from '@/shared/model/itinerario.model';

import ItinerarioService from './itinerario.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Itinerario extends Vue {
  @Inject('itinerarioService') private itinerarioService: () => ItinerarioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public itinerarios: IItinerario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllItinerarios();
  }

  public clear(): void {
    this.retrieveAllItinerarios();
  }

  public retrieveAllItinerarios(): void {
    this.isFetching = true;
    this.itinerarioService()
      .retrieve()
      .then(
        res => {
          this.itinerarios = res.data;
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

  public prepareRemove(instance: IItinerario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeItinerario(): void {
    this.itinerarioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.itinerario.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllItinerarios();
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
