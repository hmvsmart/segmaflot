import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ColoniaService from '@/entities/colonia/colonia.service';
import { IColonia } from '@/shared/model/colonia.model';

import CiudadService from '@/entities/ciudad/ciudad.service';
import { ICiudad } from '@/shared/model/ciudad.model';

import { ICP, CP } from '@/shared/model/cp.model';
import CPService from './cp.service';

const validations: any = {
  cP: {
    cp: {},
  },
};

@Component({
  validations,
})
export default class CPUpdate extends Vue {
  @Inject('cPService') private cPService: () => CPService;
  @Inject('alertService') private alertService: () => AlertService;

  public cP: ICP = new CP();

  @Inject('coloniaService') private coloniaService: () => ColoniaService;

  public colonias: IColonia[] = [];

  @Inject('ciudadService') private ciudadService: () => CiudadService;

  public ciudads: ICiudad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cPId) {
        vm.retrieveCP(to.params.cPId);
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
    if (this.cP.id) {
      this.cPService()
        .update(this.cP)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.cP.updated', { param: param.id });
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
      this.cPService()
        .create(this.cP)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.cP.created', { param: param.id });
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

  public retrieveCP(cPId): void {
    this.cPService()
      .find(cPId)
      .then(res => {
        this.cP = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.coloniaService()
      .retrieve()
      .then(res => {
        this.colonias = res.data;
      });
    this.ciudadService()
      .retrieve()
      .then(res => {
        this.ciudads = res.data;
      });
  }
}
