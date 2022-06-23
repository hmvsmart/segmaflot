import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import HorarioService from '@/entities/horario/horario.service';
import { IHorario } from '@/shared/model/horario.model';

import { IDiaHorario, DiaHorario } from '@/shared/model/dia-horario.model';
import DiaHorarioService from './dia-horario.service';

const validations: any = {
  diaHorario: {
    dia: {},
    horaEntrada: {},
    horaSalida: {},
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
export default class DiaHorarioUpdate extends Vue {
  @Inject('diaHorarioService') private diaHorarioService: () => DiaHorarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public diaHorario: IDiaHorario = new DiaHorario();

  @Inject('horarioService') private horarioService: () => HorarioService;

  public horarios: IHorario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.diaHorarioId) {
        vm.retrieveDiaHorario(to.params.diaHorarioId);
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
    if (this.diaHorario.id) {
      this.diaHorarioService()
        .update(this.diaHorario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.diaHorario.updated', { param: param.id });
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
      this.diaHorarioService()
        .create(this.diaHorario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.diaHorario.created', { param: param.id });
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
      this.diaHorario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.diaHorario[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.diaHorario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.diaHorario[field] = null;
    }
  }

  public retrieveDiaHorario(diaHorarioId): void {
    this.diaHorarioService()
      .find(diaHorarioId)
      .then(res => {
        res.horaEntrada = new Date(res.horaEntrada);
        res.horaSalida = new Date(res.horaSalida);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.diaHorario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.horarioService()
      .retrieve()
      .then(res => {
        this.horarios = res.data;
      });
  }
}
