import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import EmpleadoService from '@/entities/empleado/empleado.service';
import { IEmpleado } from '@/shared/model/empleado.model';

import { IExperienciaHabilidad, ExperienciaHabilidad } from '@/shared/model/experiencia-habilidad.model';
import ExperienciaHabilidadService from './experiencia-habilidad.service';

const validations: any = {
  experienciaHabilidad: {
    fecha: {},
    descripcion: {},
    createByUser: {},
    createdOn: {},
    modifyByUser: {},
    modifiedOn: {},
    auditStatus: {},
  },
};

@Component({
  validations,
})
export default class ExperienciaHabilidadUpdate extends mixins(JhiDataUtils) {
  @Inject('experienciaHabilidadService') private experienciaHabilidadService: () => ExperienciaHabilidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public experienciaHabilidad: IExperienciaHabilidad = new ExperienciaHabilidad();

  @Inject('empleadoService') private empleadoService: () => EmpleadoService;

  public empleados: IEmpleado[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.experienciaHabilidadId) {
        vm.retrieveExperienciaHabilidad(to.params.experienciaHabilidadId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.experienciaHabilidad.id) {
      this.experienciaHabilidadService()
        .update(this.experienciaHabilidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.experienciaHabilidad.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.experienciaHabilidadService()
        .create(this.experienciaHabilidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.experienciaHabilidad.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.experienciaHabilidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.experienciaHabilidad[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.experienciaHabilidad[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.experienciaHabilidad[field] = null;
    }
  }

  public retrieveExperienciaHabilidad(experienciaHabilidadId): void {
    this.experienciaHabilidadService()
      .find(experienciaHabilidadId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.experienciaHabilidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.empleadoService()
      .retrieve()
      .then(res => {
        this.empleados = res.data;
      });
  }
}
