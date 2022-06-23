import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IContacto } from '@/shared/model/contacto.model';

import ContactoService from './contacto.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Contacto extends Vue {
  @Inject('contactoService') private contactoService: () => ContactoService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public contactos: IContacto[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllContactos();
  }

  public clear(): void {
    this.retrieveAllContactos();
  }

  public retrieveAllContactos(): void {
    this.isFetching = true;
    this.contactoService()
      .retrieve()
      .then(
        res => {
          this.contactos = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IContacto): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeContacto(): void {
    this.contactoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('segmaflotApp.contacto.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllContactos();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
