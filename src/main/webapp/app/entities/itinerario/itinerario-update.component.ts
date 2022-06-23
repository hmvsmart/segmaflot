import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import ViajeService from '@/entities/viaje/viaje.service';
import { IViaje } from '@/shared/model/viaje.model';

import { IItinerario, Itinerario } from '@/shared/model/itinerario.model';
import ItinerarioService from './itinerario.service';

const validations: any = {
  itinerario: {
    accion: {},
    fechaHoraEstimada: {},
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
export default class ItinerarioUpdate extends Vue {
  @Inject('itinerarioService') private itinerarioService: () => ItinerarioService;
  @Inject('alertService') private alertService: () => AlertService;

  public itinerario: IItinerario = new Itinerario();

  @Inject('viajeService') private viajeService: () => ViajeService;

  public viajes: IViaje[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.itinerarioId) {
        vm.retrieveItinerario(to.params.itinerarioId);
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
    if (this.itinerario.id) {
      this.itinerarioService()
        .update(this.itinerario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.itinerario.updated', { param: param.id });
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
      this.itinerarioService()
        .create(this.itinerario)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.itinerario.created', { param: param.id });
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
      this.itinerario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.itinerario[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.itinerario[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.itinerario[field] = null;
    }
  }

  public retrieveItinerario(itinerarioId): void {
    this.itinerarioService()
      .find(itinerarioId)
      .then(res => {
        res.fechaHoraEstimada = new Date(res.fechaHoraEstimada);
        res.createdOn = new Date(res.createdOn);
        res.modifiedOn = new Date(res.modifiedOn);
        this.itinerario = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.viajeService()
      .retrieve()
      .then(res => {
        this.viajes = res.data;
      });
  }
}
