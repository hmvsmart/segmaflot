import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import StatusCitaService from '@/entities/status-cita/status-cita.service';
import { IStatusCita } from '@/shared/model/status-cita.model';

import { ICita, Cita } from '@/shared/model/cita.model';
import CitaService from './cita.service';

const validations: any = {
  cita: {
    fecha: {},
    horaInicio: {},
    horaFin: {},
    area: {},
    identificador: {},
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
export default class CitaUpdate extends Vue {
  @Inject('citaService') private citaService: () => CitaService;
  @Inject('alertService') private alertService: () => AlertService;

  public cita: ICita = new Cita();

  @Inject('statusCitaService') private statusCitaService: () => StatusCitaService;

  public statusCitas: IStatusCita[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.citaId) {
        vm.retrieveCita(to.params.citaId);
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
    if (this.cita.id) {
      this.citaService()
        .update(this.cita)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.cita.updated', { param: param.id });
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
      this.citaService()
        .create(this.cita)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.cita.created', { param: param.id });
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
      this.cita[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.cita[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cita[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.cita[field] = null;
    }
  }

  public retrieveCita(citaId): void {
    this.citaService()
      .find(citaId)
      .then(res => {
        res.horaInicio = new Date(res.horaInicio);
        res.horaFin = new Date(res.horaFin);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.cita = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.statusCitaService()
      .retrieve()
      .then(res => {
        this.statusCitas = res.data;
      });
  }
}
