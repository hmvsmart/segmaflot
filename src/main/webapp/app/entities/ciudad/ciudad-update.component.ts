import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CPService from '@/entities/cp/cp.service';
import { ICP } from '@/shared/model/cp.model';

import EstadoService from '@/entities/estado/estado.service';
import { IEstado } from '@/shared/model/estado.model';

import { ICiudad, Ciudad } from '@/shared/model/ciudad.model';
import CiudadService from './ciudad.service';

const validations: any = {
  ciudad: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class CiudadUpdate extends Vue {
  @Inject('ciudadService') private ciudadService: () => CiudadService;
  @Inject('alertService') private alertService: () => AlertService;

  public ciudad: ICiudad = new Ciudad();

  @Inject('cPService') private cPService: () => CPService;

  public cPS: ICP[] = [];

  @Inject('estadoService') private estadoService: () => EstadoService;

  public estados: IEstado[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ciudadId) {
        vm.retrieveCiudad(to.params.ciudadId);
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
    if (this.ciudad.id) {
      this.ciudadService()
        .update(this.ciudad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.ciudad.updated', { param: param.id });
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
      this.ciudadService()
        .create(this.ciudad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.ciudad.created', { param: param.id });
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

  public retrieveCiudad(ciudadId): void {
    this.ciudadService()
      .find(ciudadId)
      .then(res => {
        this.ciudad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cPService()
      .retrieve()
      .then(res => {
        this.cPS = res.data;
      });
    this.estadoService()
      .retrieve()
      .then(res => {
        this.estados = res.data;
      });
  }
}
