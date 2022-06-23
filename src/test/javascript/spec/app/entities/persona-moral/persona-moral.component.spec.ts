/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersonaMoralComponent from '@/entities/persona-moral/persona-moral.vue';
import PersonaMoralClass from '@/entities/persona-moral/persona-moral.component';
import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('PersonaMoral Management Component', () => {
    let wrapper: Wrapper<PersonaMoralClass>;
    let comp: PersonaMoralClass;
    let personaMoralServiceStub: SinonStubbedInstance<PersonaMoralService>;

    beforeEach(() => {
      personaMoralServiceStub = sinon.createStubInstance<PersonaMoralService>(PersonaMoralService);
      personaMoralServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PersonaMoralClass>(PersonaMoralComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          personaMoralService: () => personaMoralServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      personaMoralServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPersonaMorals();
      await comp.$nextTick();

      // THEN
      expect(personaMoralServiceStub.retrieve.called).toBeTruthy();
      expect(comp.personaMorals[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      personaMoralServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(personaMoralServiceStub.retrieve.callCount).toEqual(1);

      comp.removePersonaMoral();
      await comp.$nextTick();

      // THEN
      expect(personaMoralServiceStub.delete.called).toBeTruthy();
      expect(personaMoralServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
