import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import DireccionService from '@/entities/direccion/direccion.service';
import { IDireccion } from '@/shared/model/direccion.model';

import PersonaService from '@/entities/persona/persona.service';
import { IPersona } from '@/shared/model/persona.model';

import { IDireccionPersona, DireccionPersona } from '@/shared/model/direccion-persona.model';
import DireccionPersonaService from './direccion-persona.service';

const validations: any = {
  direccionPersona: {
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
export default class DireccionPersonaUpdate extends Vue {
  @Inject('direccionPersonaService') private direccionPersonaService: () => DireccionPersonaService;
  @Inject('alertService') private alertService: () => AlertService;

  public direccionPersona: IDireccionPersona = new DireccionPersona();

  @Inject('direccionService') private direccionService: () => DireccionService;

  public direccions: IDireccion[] = [];

  @Inject('personaService') private personaService: () => PersonaService;

  public personas: IPersona[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.direccionPersonaId) {
        vm.retrieveDireccionPersona(to.params.direccionPersonaId);
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
    if (this.direccionPersona.id) {
      this.direccionPersonaService()
        .update(this.direccionPersona)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.direccionPersona.updated', { param: param.id });
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
      this.direccionPersonaService()
        .create(this.direccionPersona)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.direccionPersona.created', { param: param.id });
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
      this.direccionPersona[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.direccionPersona[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.direccionPersona[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.direccionPersona[field] = null;
    }
  }

  public retrieveDireccionPersona(direccionPersonaId): void {
    this.direccionPersonaService()
      .find(direccionPersonaId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.direccionPersona = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.direccionService()
      .retrieve()
      .then(res => {
        this.direccions = res.data;
      });
    this.personaService()
      .retrieve()
      .then(res => {
        this.personas = res.data;
      });
  }
}
