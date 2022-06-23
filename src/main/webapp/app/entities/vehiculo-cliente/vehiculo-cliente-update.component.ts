import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ClienteService from '@/entities/cliente/cliente.service';
import { ICliente } from '@/shared/model/cliente.model';

import { IVehiculoCliente, VehiculoCliente } from '@/shared/model/vehiculo-cliente.model';
import VehiculoClienteService from './vehiculo-cliente.service';

const validations: any = {
  vehiculoCliente: {
    fecha: {},
    numeroSerie: {},
    color: {},
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
export default class VehiculoClienteUpdate extends Vue {
  @Inject('vehiculoClienteService') private vehiculoClienteService: () => VehiculoClienteService;
  @Inject('alertService') private alertService: () => AlertService;

  public vehiculoCliente: IVehiculoCliente = new VehiculoCliente();

  @Inject('clienteService') private clienteService: () => ClienteService;

  public clientes: ICliente[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vehiculoClienteId) {
        vm.retrieveVehiculoCliente(to.params.vehiculoClienteId);
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
    if (this.vehiculoCliente.id) {
      this.vehiculoClienteService()
        .update(this.vehiculoCliente)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.vehiculoCliente.updated', { param: param.id });
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
      this.vehiculoClienteService()
        .create(this.vehiculoCliente)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.vehiculoCliente.created', { param: param.id });
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
      this.vehiculoCliente[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.vehiculoCliente[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.vehiculoCliente[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.vehiculoCliente[field] = null;
    }
  }

  public retrieveVehiculoCliente(vehiculoClienteId): void {
    this.vehiculoClienteService()
      .find(vehiculoClienteId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.vehiculoCliente = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.clienteService()
      .retrieve()
      .then(res => {
        this.clientes = res.data;
      });
  }
}
