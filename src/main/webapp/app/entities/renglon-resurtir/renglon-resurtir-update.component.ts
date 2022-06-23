import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ResurtirService from '@/entities/resurtir/resurtir.service';
import { IResurtir } from '@/shared/model/resurtir.model';

import { IRenglonResurtir, RenglonResurtir } from '@/shared/model/renglon-resurtir.model';
import RenglonResurtirService from './renglon-resurtir.service';

const validations: any = {
  renglonResurtir: {
    fechaCaducidad: {},
    cantidad: {},
    precioCompra: {},
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
export default class RenglonResurtirUpdate extends Vue {
  @Inject('renglonResurtirService') private renglonResurtirService: () => RenglonResurtirService;
  @Inject('alertService') private alertService: () => AlertService;

  public renglonResurtir: IRenglonResurtir = new RenglonResurtir();

  @Inject('resurtirService') private resurtirService: () => ResurtirService;

  public resurtirs: IResurtir[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.renglonResurtirId) {
        vm.retrieveRenglonResurtir(to.params.renglonResurtirId);
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
    if (this.renglonResurtir.id) {
      this.renglonResurtirService()
        .update(this.renglonResurtir)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.renglonResurtir.updated', { param: param.id });
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
      this.renglonResurtirService()
        .create(this.renglonResurtir)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.renglonResurtir.created', { param: param.id });
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
      this.renglonResurtir[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.renglonResurtir[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.renglonResurtir[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.renglonResurtir[field] = null;
    }
  }

  public retrieveRenglonResurtir(renglonResurtirId): void {
    this.renglonResurtirService()
      .find(renglonResurtirId)
      .then(res => {
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.renglonResurtir = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.resurtirService()
      .retrieve()
      .then(res => {
        this.resurtirs = res.data;
      });
  }
}
