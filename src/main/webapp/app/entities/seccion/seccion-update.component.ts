import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import UbicacionInventarioService from '@/entities/ubicacion-inventario/ubicacion-inventario.service';
import { IUbicacionInventario } from '@/shared/model/ubicacion-inventario.model';

import NivelService from '@/entities/nivel/nivel.service';
import { INivel } from '@/shared/model/nivel.model';

import { ISeccion, Seccion } from '@/shared/model/seccion.model';
import SeccionService from './seccion.service';

const validations: any = {
  seccion: {
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
export default class SeccionUpdate extends Vue {
  @Inject('seccionService') private seccionService: () => SeccionService;
  @Inject('alertService') private alertService: () => AlertService;

  public seccion: ISeccion = new Seccion();

  @Inject('ubicacionInventarioService') private ubicacionInventarioService: () => UbicacionInventarioService;

  public ubicacionInventarios: IUbicacionInventario[] = [];

  @Inject('nivelService') private nivelService: () => NivelService;

  public nivels: INivel[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.seccionId) {
        vm.retrieveSeccion(to.params.seccionId);
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
    if (this.seccion.id) {
      this.seccionService()
        .update(this.seccion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.seccion.updated', { param: param.id });
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
      this.seccionService()
        .create(this.seccion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.seccion.created', { param: param.id });
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
      this.seccion[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.seccion[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.seccion[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.seccion[field] = null;
    }
  }

  public retrieveSeccion(seccionId): void {
    this.seccionService()
      .find(seccionId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.seccion = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ubicacionInventarioService()
      .retrieve()
      .then(res => {
        this.ubicacionInventarios = res.data;
      });
    this.nivelService()
      .retrieve()
      .then(res => {
        this.nivels = res.data;
      });
  }
}
