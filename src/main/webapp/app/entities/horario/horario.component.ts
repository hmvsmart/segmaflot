import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHorario } from '@/shared/model/horario.model';

import HorarioService from './horario.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Horario extends Vue {
  @Inject('horarioService') private horarioService: () => HorarioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public horarios: IHorario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllHorarios();
  }

  public clear(): void {
    this.retrieveAllHorarios();
  }

  public retrieveAllHorarios(): void {
    this.isFetching = true;
    this.horarioService()
      .retrieve()
      .then(
        res => {
          this.horarios = res.data;
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

  public prepareRemove(instance: IHorario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeHorario(): void {
    this.horarioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.horario.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllHorarios();
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
