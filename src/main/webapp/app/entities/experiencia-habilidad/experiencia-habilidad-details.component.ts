import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IExperienciaHabilidad } from '@/shared/model/experiencia-habilidad.model';
import ExperienciaHabilidadService from './experiencia-habilidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ExperienciaHabilidadDetails extends mixins(JhiDataUtils) {
  @Inject('experienciaHabilidadService') private experienciaHabilidadService: () => ExperienciaHabilidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public experienciaHabilidad: IExperienciaHabilidad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.experienciaHabilidadId) {
        vm.retrieveExperienciaHabilidad(to.params.experienciaHabilidadId);
      }
    });
  }

  public retrieveExperienciaHabilidad(experienciaHabilidadId) {
    this.experienciaHabilidadService()
      .find(experienciaHabilidadId)
      .then(res => {
        this.experienciaHabilidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
