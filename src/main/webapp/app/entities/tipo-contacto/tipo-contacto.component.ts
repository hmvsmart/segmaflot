import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITipoContacto } from '@/shared/model/tipo-contacto.model';

import TipoContactoService from './tipo-contacto.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TipoContacto extends Vue {
  @Inject('tipoContactoService') private tipoContactoService: () => TipoContactoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public tipoContactos: ITipoContacto[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTipoContactos();
  }

  public clear(): void {
    this.retrieveAllTipoContactos();
  }

  public retrieveAllTipoContactos(): void {
    this.isFetching = true;
    this.tipoContactoService()
      .retrieve()
      .then(
        res => {
          this.tipoContactos = res.data;
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

  public prepareRemove(instance: ITipoContacto): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTipoContacto(): void {
    this.tipoContactoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.tipoContacto.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTipoContactos();
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
