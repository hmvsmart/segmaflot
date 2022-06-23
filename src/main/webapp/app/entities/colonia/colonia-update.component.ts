import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import DireccionService from '@/entities/direccion/direccion.service';
import { IDireccion } from '@/shared/model/direccion.model';

import CPService from '@/entities/cp/cp.service';
import { ICP } from '@/shared/model/cp.model';

import { IColonia, Colonia } from '@/shared/model/colonia.model';
import ColoniaService from './colonia.service';

const validations: any = {
  colonia: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class ColoniaUpdate extends Vue {
  @Inject('coloniaService') private coloniaService: () => ColoniaService;
  @Inject('alertService') private alertService: () => AlertService;

  public colonia: IColonia = new Colonia();

  @Inject('direccionService') private direccionService: () => DireccionService;

  public direccions: IDireccion[] = [];

  @Inject('cPService') private cPService: () => CPService;

  public cPS: ICP[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.coloniaId) {
        vm.retrieveColonia(to.params.coloniaId);
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
    if (this.colonia.id) {
      this.coloniaService()
        .update(this.colonia)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.colonia.updated', { param: param.id });
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
      this.coloniaService()
        .create(this.colonia)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.colonia.created', { param: param.id });
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

  public retrieveColonia(coloniaId): void {
    this.coloniaService()
      .find(coloniaId)
      .then(res => {
        this.colonia = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.direccionService()
      .retrieve()
      .then(res => {
        this.direccions = res.data;
      });
    this.cPService()
      .retrieve()
      .then(res => {
        this.cPS = res.data;
      });
  }
}
