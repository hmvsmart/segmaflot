import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import EmpleadoService from '@/entities/empleado/empleado.service';
import { IEmpleado } from '@/shared/model/empleado.model';

import { ILicenciaManejo, LicenciaManejo } from '@/shared/model/licencia-manejo.model';
import LicenciaManejoService from './licencia-manejo.service';

const validations: any = {
  licenciaManejo: {
    fecha: {},
    tipo: {},
    status: {},
    fechaExpiracion: {},
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
export default class LicenciaManejoUpdate extends Vue {
  @Inject('licenciaManejoService') private licenciaManejoService: () => LicenciaManejoService;
  @Inject('alertService') private alertService: () => AlertService;

  public licenciaManejo: ILicenciaManejo = new LicenciaManejo();

  @Inject('empleadoService') private empleadoService: () => EmpleadoService;

  public empleados: IEmpleado[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.licenciaManejoId) {
        vm.retrieveLicenciaManejo(to.params.licenciaManejoId);
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
    if (this.licenciaManejo.id) {
      this.licenciaManejoService()
        .update(this.licenciaManejo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.licenciaManejo.updated', { param: param.id });
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
      this.licenciaManejoService()
        .create(this.licenciaManejo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.licenciaManejo.created', { param: param.id });
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
      this.licenciaManejo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.licenciaManejo[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.licenciaManejo[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.licenciaManejo[field] = null;
    }
  }

  public retrieveLicenciaManejo(licenciaManejoId): void {
    this.licenciaManejoService()
      .find(licenciaManejoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.licenciaManejo = res;
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
