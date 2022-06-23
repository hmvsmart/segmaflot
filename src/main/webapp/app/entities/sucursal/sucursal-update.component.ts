import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import EmpleadoService from '@/entities/empleado/empleado.service';
import { IEmpleado } from '@/shared/model/empleado.model';

import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

import { ISucursal, Sucursal } from '@/shared/model/sucursal.model';
import SucursalService from './sucursal.service';

const validations: any = {
  sucursal: {
    nombre: {},
    tipoSucursal: {},
    telefono: {},
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
export default class SucursalUpdate extends Vue {
  @Inject('sucursalService') private sucursalService: () => SucursalService;
  @Inject('alertService') private alertService: () => AlertService;

  public sucursal: ISucursal = new Sucursal();

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];

  @Inject('empleadoService') private empleadoService: () => EmpleadoService;

  public empleados: IEmpleado[] = [];

  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;

  public personaMorals: IPersonaMoral[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sucursalId) {
        vm.retrieveSucursal(to.params.sucursalId);
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
    if (this.sucursal.id) {
      this.sucursalService()
        .update(this.sucursal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.sucursal.updated', { param: param.id });
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
      this.sucursalService()
        .create(this.sucursal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.sucursal.created', { param: param.id });
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
      this.sucursal[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.sucursal[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.sucursal[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.sucursal[field] = null;
    }
  }

  public retrieveSucursal(sucursalId): void {
    this.sucursalService()
      .find(sucursalId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.sucursal = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
    this.empleadoService()
      .retrieve()
      .then(res => {
        this.empleados = res.data;
      });
    this.personaMoralService()
      .retrieve()
      .then(res => {
        this.personaMorals = res.data;
      });
  }
}
