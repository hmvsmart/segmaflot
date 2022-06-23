import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import TipoContactoService from '@/entities/tipo-contacto/tipo-contacto.service';
import { ITipoContacto } from '@/shared/model/tipo-contacto.model';

import PersonaService from '@/entities/persona/persona.service';
import { IPersona } from '@/shared/model/persona.model';

import { IContacto, Contacto } from '@/shared/model/contacto.model';
import ContactoService from './contacto.service';

const validations: any = {
  contacto: {
    valor: {},
    etiqueta: {},
    string: {},
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
export default class ContactoUpdate extends Vue {
  @Inject('contactoService') private contactoService: () => ContactoService;
  @Inject('alertService') private alertService: () => AlertService;

  public contacto: IContacto = new Contacto();

  @Inject('tipoContactoService') private tipoContactoService: () => TipoContactoService;

  public tipoContactos: ITipoContacto[] = [];

  @Inject('personaService') private personaService: () => PersonaService;

  public personas: IPersona[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactoId) {
        vm.retrieveContacto(to.params.contactoId);
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
    if (this.contacto.id) {
      this.contactoService()
        .update(this.contacto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.contacto.updated', { param: param.id });
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
      this.contactoService()
        .create(this.contacto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.contacto.created', { param: param.id });
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
      this.contacto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.contacto[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.contacto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.contacto[field] = null;
    }
  }

  public retrieveContacto(contactoId): void {
    this.contactoService()
      .find(contactoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.contacto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tipoContactoService()
      .retrieve()
      .then(res => {
        this.tipoContactos = res.data;
      });
    this.personaService()
      .retrieve()
      .then(res => {
        this.personas = res.data;
      });
  }
}
