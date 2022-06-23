/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersonaComponent from '@/entities/persona/persona.vue';
import PersonaClass from '@/entities/persona/persona.component';
import PersonaService from '@/entities/persona/persona.service';
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
  describe('Persona Management Component', () => {
    let wrapper: Wrapper<PersonaClass>;
    let comp: PersonaClass;
    let personaServiceStub: SinonStubbedInstance<PersonaService>;

    beforeEach(() => {
      personaServiceStub = sinon.createStubInstance<PersonaService>(PersonaService);
      personaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PersonaClass>(PersonaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          personaService: () => personaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      personaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPersonas();
      await comp.$nextTick();

      // THEN
      expect(personaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.personas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      personaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(personaServiceStub.retrieve.callCount).toEqual(1);

      comp.removePersona();
      await comp.$nextTick();

      // THEN
      expect(personaServiceStub.delete.called).toBeTruthy();
      expect(personaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
