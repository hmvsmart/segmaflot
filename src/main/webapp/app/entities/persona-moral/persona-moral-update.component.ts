import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import SucursalService from '@/entities/sucursal/sucursal.service';
import { ISucursal } from '@/shared/model/sucursal.model';

import ReporteService from '@/entities/reporte/reporte.service';
import { IReporte } from '@/shared/model/reporte.model';

import PedidoService from '@/entities/pedido/pedido.service';
import { IPedido } from '@/shared/model/pedido.model';

import PersonaService from '@/entities/persona/persona.service';
import { IPersona } from '@/shared/model/persona.model';

import { IPersonaMoral, PersonaMoral } from '@/shared/model/persona-moral.model';
import PersonaMoralService from './persona-moral.service';

const validations: any = {
  personaMoral: {
    razonSocial: {},
    rfc: {},
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
export default class PersonaMoralUpdate extends Vue {
  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;
  @Inject('alertService') private alertService: () => AlertService;

  public personaMoral: IPersonaMoral = new PersonaMoral();

  @Inject('sucursalService') private sucursalService: () => SucursalService;

  public sucursals: ISucursal[] = [];

  @Inject('reporteService') private reporteService: () => ReporteService;

  public reportes: IReporte[] = [];

  @Inject('pedidoService') private pedidoService: () => PedidoService;

  public pedidos: IPedido[] = [];

  @Inject('personaService') private personaService: () => PersonaService;

  public personas: IPersona[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personaMoralId) {
        vm.retrievePersonaMoral(to.params.personaMoralId);
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
    if (this.personaMoral.id) {
      this.personaMoralService()
        .update(this.personaMoral)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.personaMoral.updated', { param: param.id });
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
      this.personaMoralService()
        .create(this.personaMoral)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.personaMoral.created', { param: param.id });
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
      this.personaMoral[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.personaMoral[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.personaMoral[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.personaMoral[field] = null;
    }
  }

  public retrievePersonaMoral(personaMoralId): void {
    this.personaMoralService()
      .find(personaMoralId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.personaMoral = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.sucursalService()
      .retrieve()
      .then(res => {
        this.sucursals = res.data;
      });
    this.reporteService()
      .retrieve()
      .then(res => {
        this.reportes = res.data;
      });
    this.pedidoService()
      .retrieve()
      .then(res => {
        this.pedidos = res.data;
      });
    this.personaService()
      .retrieve()
      .then(res => {
        this.personas = res.data;
      });
  }
}
