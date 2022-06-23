import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPersonaMoral } from '@/shared/model/persona-moral.model';
import PersonaMoralService from './persona-moral.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PersonaMoralDetails extends Vue {
  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;
  @Inject('alertService') private alertService: () => AlertService;

  public personaMoral: IPersonaMoral = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personaMoralId) {
        vm.retrievePersonaMoral(to.params.personaMoralId);
      }
    });
  }

  public retrievePersonaMoral(personaMoralId) {
    this.personaMoralService()
      .find(personaMoralId)
      .then(res => {
        this.personaMoral = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
