import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import SeccionService from '@/entities/seccion/seccion.service';
import { ISeccion } from '@/shared/model/seccion.model';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import { IUbicacionInventario, UbicacionInventario } from '@/shared/model/ubicacion-inventario.model';
import UbicacionInventarioService from './ubicacion-inventario.service';

const validations: any = {
  ubicacionInventario: {
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
export default class UbicacionInventarioUpdate extends Vue {
  @Inject('ubicacionInventarioService') private ubicacionInventarioService: () => UbicacionInventarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public ubicacionInventario: IUbicacionInventario = new UbicacionInventario();

  @Inject('seccionService') private seccionService: () => SeccionService;

  public seccions: ISeccion[] = [];

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ubicacionInventarioId) {
        vm.retrieveUbicacionInventario(to.params.ubicacionInventarioId);
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
    if (this.ubicacionInventario.id) {
      this.ubicacionInventarioService()
        .update(this.ubicacionInventario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.ubicacionInventario.updated', { param: param.id });
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
      this.ubicacionInventarioService()
        .create(this.ubicacionInventario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.ubicacionInventario.created', { param: param.id });
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
      this.ubicacionInventario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.ubicacionInventario[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.ubicacionInventario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.ubicacionInventario[field] = null;
    }
  }

  public retrieveUbicacionInventario(ubicacionInventarioId): void {
    this.ubicacionInventarioService()
      .find(ubicacionInventarioId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.ubicacionInventario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.seccionService()
      .retrieve()
      .then(res => {
        this.seccions = res.data;
      });
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
  }
}
