import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ItinerarioService from '@/entities/itinerario/itinerario.service';
import { IItinerario } from '@/shared/model/itinerario.model';

import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';
import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';

import DireccionService from '@/entities/direccion/direccion.service';
import { IDireccion } from '@/shared/model/direccion.model';

import { IViaje, Viaje } from '@/shared/model/viaje.model';
import ViajeService from './viaje.service';

const validations: any = {
  viaje: {
    fecha: {},
    fechaInicio: {},
    fechaFin: {},
    horaEmbarque: {},
    tipoCarga: {},
    pesoNeto: {},
    comentarios: {},
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
export default class ViajeUpdate extends mixins(JhiDataUtils) {
  @Inject('viajeService') private viajeService: () => ViajeService;
  @Inject('alertService') private alertService: () => AlertService;

  public viaje: IViaje = new Viaje();

  @Inject('itinerarioService') private itinerarioService: () => ItinerarioService;

  public itinerarios: IItinerario[] = [];

  @Inject('unidadViajeService') private unidadViajeService: () => UnidadViajeService;

  public unidadViajes: IUnidadViaje[] = [];

  @Inject('direccionService') private direccionService: () => DireccionService;

  public direccions: IDireccion[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.viajeId) {
        vm.retrieveViaje(to.params.viajeId);
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
    if (this.viaje.id) {
      this.viajeService()
        .update(this.viaje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.viaje.updated', { param: param.id });
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
      this.viajeService()
        .create(this.viaje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.viaje.created', { param: param.id });
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
      this.viaje[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.viaje[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.viaje[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.viaje[field] = null;
    }
  }

  public retrieveViaje(viajeId): void {
    this.viajeService()
      .find(viajeId)
      .then(res => {
        res.horaEmbarque = new Date(res.horaEmbarque);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.viaje = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.itinerarioService()
      .retrieve()
      .then(res => {
        this.itinerarios = res.data;
      });
    this.unidadViajeService()
      .retrieve()
      .then(res => {
        this.unidadViajes = res.data;
      });
    this.direccionService()
      .retrieve()
      .then(res => {
        this.direccions = res.data;
      });
  }
}
