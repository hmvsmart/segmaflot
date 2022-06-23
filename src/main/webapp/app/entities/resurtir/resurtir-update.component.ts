import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import RenglonResurtirService from '@/entities/renglon-resurtir/renglon-resurtir.service';
import { IRenglonResurtir } from '@/shared/model/renglon-resurtir.model';

import InventarioService from '@/entities/inventario/inventario.service';
import { IInventario } from '@/shared/model/inventario.model';

import { IResurtir, Resurtir } from '@/shared/model/resurtir.model';
import ResurtirService from './resurtir.service';

const validations: any = {
  resurtir: {
    fecha: {},
    total: {},
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
export default class ResurtirUpdate extends Vue {
  @Inject('resurtirService') private resurtirService: () => ResurtirService;
  @Inject('alertService') private alertService: () => AlertService;

  public resurtir: IResurtir = new Resurtir();

  @Inject('renglonResurtirService') private renglonResurtirService: () => RenglonResurtirService;

  public renglonResurtirs: IRenglonResurtir[] = [];

  @Inject('inventarioService') private inventarioService: () => InventarioService;

  public inventarios: IInventario[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resurtirId) {
        vm.retrieveResurtir(to.params.resurtirId);
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
    if (this.resurtir.id) {
      this.resurtirService()
        .update(this.resurtir)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.resurtir.updated', { param: param.id });
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
      this.resurtirService()
        .create(this.resurtir)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.resurtir.created', { param: param.id });
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
      this.resurtir[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.resurtir[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.resurtir[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.resurtir[field] = null;
    }
  }

  public retrieveResurtir(resurtirId): void {
    this.resurtirService()
      .find(resurtirId)
      .then(res => {
        res.fecha = new Date(res.fecha);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.resurtir = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.renglonResurtirService()
      .retrieve()
      .then(res => {
        this.renglonResurtirs = res.data;
      });
    this.inventarioService()
      .retrieve()
      .then(res => {
        this.inventarios = res.data;
      });
  }
}
