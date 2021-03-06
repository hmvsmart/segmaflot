import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IExperienciaHabilidad } from '@/shared/model/experiencia-habilidad.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ExperienciaHabilidadService from './experiencia-habilidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ExperienciaHabilidad extends mixins(JhiDataUtils) {
  @Inject('experienciaHabilidadService') private experienciaHabilidadService: () => ExperienciaHabilidadService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public experienciaHabilidads: IExperienciaHabilidad[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllExperienciaHabilidads();
  }

  public clear(): void {
    this.retrieveAllExperienciaHabilidads();
  }

  public retrieveAllExperienciaHabilidads(): void {
    this.isFetching = true;
    this.experienciaHabilidadService()
      .retrieve()
      .then(
        res => {
          this.experienciaHabilidads = res.data;
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

  public prepareRemove(instance: IExperienciaHabilidad): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeExperienciaHabilidad(): void {
    this.experienciaHabilidadService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.experienciaHabilidad.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllExperienciaHabilidads();
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
