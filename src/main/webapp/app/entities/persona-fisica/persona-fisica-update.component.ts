import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PersonaService from '@/entities/persona/persona.service';
import { IPersona } from '@/shared/model/persona.model';

import { IPersonaFisica, PersonaFisica } from '@/shared/model/persona-fisica.model';
import PersonaFisicaService from './persona-fisica.service';

const validations: any = {
  personaFisica: {
    nombre: {},
    apaterno: {},
    amaterno: {},
    fechaNacimiento: {},
    curp: {},
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
export default class PersonaFisicaUpdate extends Vue {
  @Inject('personaFisicaService') private personaFisicaService: () => PersonaFisicaService;
  @Inject('alertService') private alertService: () => AlertService;

  public personaFisica: IPersonaFisica = new PersonaFisica();

  @Inject('personaService') private personaService: () => PersonaService;

  public personas: IPersona[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personaFisicaId) {
        vm.retrievePersonaFisica(to.params.personaFisicaId);
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
    if (this.personaFisica.id) {
      this.personaFisicaService()
        .update(this.personaFisica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.personaFisica.updated', { param: param.id });
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
      this.personaFisicaService()
        .create(this.personaFisica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.personaFisica.created', { param: param.id });
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
      this.personaFisica[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.personaFisica[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.personaFisica[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.personaFisica[field] = null;
    }
  }

  public retrievePersonaFisica(personaFisicaId): void {
    this.personaFisicaService()
      .find(personaFisicaId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.personaFisica = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.personaService()
      .retrieve()
      .then(res => {
        this.personas = res.data;
      });
  }
}
