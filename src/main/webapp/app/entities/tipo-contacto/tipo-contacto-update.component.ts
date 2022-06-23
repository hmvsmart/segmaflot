import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ContactoService from '@/entities/contacto/contacto.service';
import { IContacto } from '@/shared/model/contacto.model';

import { ITipoContacto, TipoContacto } from '@/shared/model/tipo-contacto.model';
import TipoContactoService from './tipo-contacto.service';

const validations: any = {
  tipoContacto: {
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
export default class TipoContactoUpdate extends Vue {
  @Inject('tipoContactoService') private tipoContactoService: () => TipoContactoService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoContacto: ITipoContacto = new TipoContacto();

  @Inject('contactoService') private contactoService: () => ContactoService;

  public contactos: IContacto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoContactoId) {
        vm.retrieveTipoContacto(to.params.tipoContactoId);
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
    if (this.tipoContacto.id) {
      this.tipoContactoService()
        .update(this.tipoContacto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.tipoContacto.updated', { param: param.id });
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
      this.tipoContactoService()
        .create(this.tipoContacto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.tipoContacto.created', { param: param.id });
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
      this.tipoContacto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tipoContacto[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.tipoContacto[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.tipoContacto[field] = null;
    }
  }

  public retrieveTipoContacto(tipoContactoId): void {
    this.tipoContactoService()
      .find(tipoContactoId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.tipoContacto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.contactoService()
      .retrieve()
      .then(res => {
        this.contactos = res.data;
      });
  }
}
