/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersonaFisicaComponent from '@/entities/persona-fisica/persona-fisica.vue';
import PersonaFisicaClass from '@/entities/persona-fisica/persona-fisica.component';
import PersonaFisicaService from '@/entities/persona-fisica/persona-fisica.service';
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
  describe('PersonaFisica Management Component', () => {
    let wrapper: Wrapper<PersonaFisicaClass>;
    let comp: PersonaFisicaClass;
    let personaFisicaServiceStub: SinonStubbedInstance<PersonaFisicaService>;

    beforeEach(() => {
      personaFisicaServiceStub = sinon.createStubInstance<PersonaFisicaService>(PersonaFisicaService);
      personaFisicaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PersonaFisicaClass>(PersonaFisicaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          personaFisicaService: () => personaFisicaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      personaFisicaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPersonaFisicas();
      await comp.$nextTick();

      // THEN
      expect(personaFisicaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.personaFisicas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      personaFisicaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(personaFisicaServiceStub.retrieve.callCount).toEqual(1);

      comp.removePersonaFisica();
      await comp.$nextTick();

      // THEN
      expect(personaFisicaServiceStub.delete.called).toBeTruthy();
      expect(personaFisicaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
