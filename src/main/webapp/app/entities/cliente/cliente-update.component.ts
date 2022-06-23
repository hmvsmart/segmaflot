import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import VehiculoClienteService from '@/entities/vehiculo-cliente/vehiculo-cliente.service';
import { IVehiculoCliente } from '@/shared/model/vehiculo-cliente.model';

import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';
import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';

import PersonaService from '@/entities/persona/persona.service';
import { IPersona } from '@/shared/model/persona.model';

import { ICliente, Cliente } from '@/shared/model/cliente.model';
import ClienteService from './cliente.service';

const validations: any = {
  cliente: {
    fecha: {},
    observaciones: {},
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
export default class ClienteUpdate extends mixins(JhiDataUtils) {
  @Inject('clienteService') private clienteService: () => ClienteService;
  @Inject('alertService') private alertService: () => AlertService;

  public cliente: ICliente = new Cliente();

  @Inject('vehiculoClienteService') private vehiculoClienteService: () => VehiculoClienteService;

  public vehiculoClientes: IVehiculoCliente[] = [];

  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;

  public unidadServicios: IUnidadServicio[] = [];

  @Inject('personaService') private personaService: () => PersonaService;

  public personas: IPersona[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clienteId) {
        vm.retrieveCliente(to.params.clienteId);
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
    if (this.cliente.id) {
      this.clienteService()
        .update(this.cliente)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.cliente.updated', { param: param.id });
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
      this.clienteService()
        .create(this.cliente)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.cliente.created', { param: param.id });
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
      this.cliente[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.cliente[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.cliente[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.cliente[field] = null;
    }
  }

  public retrieveCliente(clienteId): void {
    this.clienteService()
      .find(clienteId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.cliente = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.vehiculoClienteService()
      .retrieve()
      .then(res => {
        this.vehiculoClientes = res.data;
      });
    this.unidadServicioService()
      .retrieve()
      .then(res => {
        this.unidadServicios = res.data;
      });
    this.personaService()
      .retrieve()
      .then(res => {
        this.personas = res.data;
      });
  }
}
