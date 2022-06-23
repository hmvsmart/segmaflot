import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import DireccionPersonaService from '@/entities/direccion-persona/direccion-persona.service';
import { IDireccionPersona } from '@/shared/model/direccion-persona.model';

import ViajeService from '@/entities/viaje/viaje.service';
import { IViaje } from '@/shared/model/viaje.model';

import ColoniaService from '@/entities/colonia/colonia.service';
import { IColonia } from '@/shared/model/colonia.model';

import { IDireccion, Direccion } from '@/shared/model/direccion.model';
import DireccionService from './direccion.service';

const validations: any = {
  direccion: {
    calle: {},
    numeroExterior: {},
    numeroInterior: {},
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
export default class DireccionUpdate extends Vue {
  @Inject('direccionService') private direccionService: () => DireccionService;
  @Inject('alertService') private alertService: () => AlertService;

  public direccion: IDireccion = new Direccion();

  @Inject('direccionPersonaService') private direccionPersonaService: () => DireccionPersonaService;

  public direccionPersonas: IDireccionPersona[] = [];

  @Inject('viajeService') private viajeService: () => ViajeService;

  public viajes: IViaje[] = [];

  @Inject('coloniaService') private coloniaService: () => ColoniaService;

  public colonias: IColonia[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.direccionId) {
        vm.retrieveDireccion(to.params.direccionId);
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
    if (this.direccion.id) {
      this.direccionService()
        .update(this.direccion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.direccion.updated', { param: param.id });
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
      this.direccionService()
        .create(this.direccion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.direccion.created', { param: param.id });
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
      this.direccion[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.direccion[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.direccion[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.direccion[field] = null;
    }
  }

  public retrieveDireccion(direccionId): void {
    this.direccionService()
      .find(direccionId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.direccion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.direccionPersonaService()
      .retrieve()
      .then(res => {
        this.direccionPersonas = res.data;
      });
    this.viajeService()
      .retrieve()
      .then(res => {
        this.viajes = res.data;
      });
    this.coloniaService()
      .retrieve()
      .then(res => {
        this.colonias = res.data;
      });
  }
}
