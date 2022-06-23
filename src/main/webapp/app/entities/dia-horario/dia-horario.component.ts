import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDiaHorario } from '@/shared/model/dia-horario.model';

import DiaHorarioService from './dia-horario.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DiaHorario extends Vue {
  @Inject('diaHorarioService') private diaHorarioService: () => DiaHorarioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public diaHorarios: IDiaHorario[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDiaHorarios();
  }

  public clear(): void {
    this.retrieveAllDiaHorarios();
  }

  public retrieveAllDiaHorarios(): void {
    this.isFetching = true;
    this.diaHorarioService()
      .retrieve()
      .then(
        res => {
          this.diaHorarios = res.data;
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

  public prepareRemove(instance: IDiaHorario): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDiaHorario(): void {
    this.diaHorarioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.diaHorario.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDiaHorarios();
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
