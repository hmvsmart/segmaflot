import { Component, Vue, Inject } from 'vue-property-decorator';

import { IContacto } from '@/shared/model/contacto.model';
import ContactoService from './contacto.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ContactoDetails extends Vue {
  @Inject('contactoService') private contactoService: () => ContactoService;
  @Inject('alertService') private alertService: () => AlertService;

  public contacto: IContacto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.contactoId) {
        vm.retrieveContacto(to.params.contactoId);
      }
    });
  }

  public retrieveContacto(contactoId) {
    this.contactoService()
      .find(contactoId)
      .then(res => {
        this.contacto = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
