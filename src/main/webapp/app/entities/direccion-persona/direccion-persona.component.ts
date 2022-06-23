import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDireccionPersona } from '@/shared/model/direccion-persona.model';

import DireccionPersonaService from './direccion-persona.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DireccionPersona extends Vue {
  @Inject('direccionPersonaService') private direccionPersonaService: () => DireccionPersonaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public direccionPersonas: IDireccionPersona[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDireccionPersonas();
  }

  public clear(): void {
    this.retrieveAllDireccionPersonas();
  }

  public retrieveAllDireccionPersonas(): void {
    this.isFetching = true;
    this.direccionPersonaService()
      .retrieve()
      .then(
        res => {
          this.direccionPersonas = res.data;
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

  public prepareRemove(instance: IDireccionPersona): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDireccionPersona(): void {
    this.direccionPersonaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.direccionPersona.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDireccionPersonas();
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
