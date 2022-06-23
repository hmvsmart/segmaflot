import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import CitaService from '@/entities/cita/cita.service';
import { ICita } from '@/shared/model/cita.model';

import { IStatusCita, StatusCita } from '@/shared/model/status-cita.model';
import StatusCitaService from './status-cita.service';
import { TipoStatusCita } from '@/shared/model/enumerations/tipo-status-cita.model';

const validations: any = {
  statusCita: {
    fecha: {},
    status: {},
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
export default class StatusCitaUpdate extends Vue {
  @Inject('statusCitaService') private statusCitaService: () => StatusCitaService;
  @Inject('alertService') private alertService: () => AlertService;

  public statusCita: IStatusCita = new StatusCita();

  @Inject('citaService') private citaService: () => CitaService;

  public citas: ICita[] = [];
  public tipoStatusCitaValues: string[] = Object.keys(TipoStatusCita);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.statusCitaId) {
        vm.retrieveStatusCita(to.params.statusCitaId);
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
    if (this.statusCita.id) {
      this.statusCitaService()
        .update(this.statusCita)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.statusCita.updated', { param: param.id });
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
      this.statusCitaService()
        .create(this.statusCita)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.statusCita.created', { param: param.id });
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
      this.statusCita[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.statusCita[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.statusCita[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.statusCita[field] = null;
    }
  }

  public retrieveStatusCita(statusCitaId): void {
    this.statusCitaService()
      .find(statusCitaId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.statusCita = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.citaService()
      .retrieve()
      .then(res => {
        this.citas = res.data;
      });
  }
}
