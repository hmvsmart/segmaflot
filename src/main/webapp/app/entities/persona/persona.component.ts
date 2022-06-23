import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPersona } from '@/shared/model/persona.model';

import PersonaService from './persona.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Persona extends Vue {
  @Inject('personaService') private personaService: () => PersonaService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public personas: IPersona[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPersonas();
  }

  public clear(): void {
    this.retrieveAllPersonas();
  }

  public retrieveAllPersonas(): void {
    this.isFetching = true;
    this.personaService()
      .retrieve()
      .then(
        res => {
          this.personas = res.data;
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

  public prepareRemove(instance: IPersona): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePersona(): void {
    this.personaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.persona.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPersonas();
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
