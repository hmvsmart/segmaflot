import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import ServicioService from '@/entities/servicio/servicio.service';
import { IServicio } from '@/shared/model/servicio.model';

import { IMaterialServicio, MaterialServicio } from '@/shared/model/material-servicio.model';
import MaterialServicioService from './material-servicio.service';

const validations: any = {
  materialServicio: {
    fecha: {},
    cantidad: {},
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
export default class MaterialServicioUpdate extends Vue {
  @Inject('materialServicioService') private materialServicioService: () => MaterialServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public materialServicio: IMaterialServicio = new MaterialServicio();

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];

  @Inject('servicioService') private servicioService: () => ServicioService;

  public servicios: IServicio[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.materialServicioId) {
        vm.retrieveMaterialServicio(to.params.materialServicioId);
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
    if (this.materialServicio.id) {
      this.materialServicioService()
        .update(this.materialServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.materialServicio.updated', { param: param.id });
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
      this.materialServicioService()
        .create(this.materialServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.materialServicio.created', { param: param.id });
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
      this.materialServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.materialServicio[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.materialServicio[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.materialServicio[field] = null;
    }
  }

  public retrieveMaterialServicio(materialServicioId): void {
    this.materialServicioService()
      .find(materialServicioId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.materialServicio = res;
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
    this.servicioService()
      .retrieve()
      .then(res => {
        this.servicios = res.data;
      });
  }
}
