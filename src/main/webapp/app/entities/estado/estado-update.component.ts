import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CiudadService from '@/entities/ciudad/ciudad.service';
import { ICiudad } from '@/shared/model/ciudad.model';

import { IEstado, Estado } from '@/shared/model/estado.model';
import EstadoService from './estado.service';

const validations: any = {
  estado: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class EstadoUpdate extends Vue {
  @Inject('estadoService') private estadoService: () => EstadoService;
  @Inject('alertService') private alertService: () => AlertService;

  public estado: IEstado = new Estado();

  @Inject('ciudadService') private ciudadService: () => CiudadService;

  public ciudads: ICiudad[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estadoId) {
        vm.retrieveEstado(to.params.estadoId);
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
    if (this.estado.id) {
      this.estadoService()
        .update(this.estado)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.estado.updated', { param: param.id });
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
      this.estadoService()
        .create(this.estado)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.estado.created', { param: param.id });
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

  public retrieveEstado(estadoId): void {
    this.estadoService()
      .find(estadoId)
      .then(res => {
        this.estado = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ciudadService()
      .retrieve()
      .then(res => {
        this.ciudads = res.data;
      });
  }
}
