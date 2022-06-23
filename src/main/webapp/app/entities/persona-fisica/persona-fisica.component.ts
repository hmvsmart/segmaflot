import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPersonaFisica } from '@/shared/model/persona-fisica.model';

import PersonaFisicaService from './persona-fisica.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PersonaFisica extends Vue {
  @Inject('personaFisicaService') private personaFisicaService: () => PersonaFisicaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public personaFisicas: IPersonaFisica[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPersonaFisicas();
  }

  public clear(): void {
    this.retrieveAllPersonaFisicas();
  }

  public retrieveAllPersonaFisicas(): void {
    this.isFetching = true;
    this.personaFisicaService()
      .retrieve()
      .then(
        res => {
          this.personaFisicas = res.data;
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

  public prepareRemove(instance: IPersonaFisica): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePersonaFisica(): void {
    this.personaFisicaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.personaFisica.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPersonaFisicas();
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
