import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import SeccionService from '@/entities/seccion/seccion.service';
import { ISeccion } from '@/shared/model/seccion.model';

import EstanteService from '@/entities/estante/estante.service';
import { IEstante } from '@/shared/model/estante.model';

import { INivel, Nivel } from '@/shared/model/nivel.model';
import NivelService from './nivel.service';

const validations: any = {
  nivel: {
    nombre: {},
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
export default class NivelUpdate extends Vue {
  @Inject('nivelService') private nivelService: () => NivelService;
  @Inject('alertService') private alertService: () => AlertService;

  public nivel: INivel = new Nivel();

  @Inject('seccionService') private seccionService: () => SeccionService;

  public seccions: ISeccion[] = [];

  @Inject('estanteService') private estanteService: () => EstanteService;

  public estantes: IEstante[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nivelId) {
        vm.retrieveNivel(to.params.nivelId);
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
    if (this.nivel.id) {
      this.nivelService()
        .update(this.nivel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.nivel.updated', { param: param.id });
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
      this.nivelService()
        .create(this.nivel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.nivel.created', { param: param.id });
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
      this.nivel[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.nivel[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.nivel[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.nivel[field] = null;
    }
  }

  public retrieveNivel(nivelId): void {
    this.nivelService()
      .find(nivelId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.nivel = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.seccionService()
      .retrieve()
      .then(res => {
        this.seccions = res.data;
      });
    this.estanteService()
      .retrieve()
      .then(res => {
        this.estantes = res.data;
      });
  }
}
