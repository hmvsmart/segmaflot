/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DireccionPersonaComponent from '@/entities/direccion-persona/direccion-persona.vue';
import DireccionPersonaClass from '@/entities/direccion-persona/direccion-persona.component';
import DireccionPersonaService from '@/entities/direccion-persona/direccion-persona.service';
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
  describe('DireccionPersona Management Component', () => {
    let wrapper: Wrapper<DireccionPersonaClass>;
    let comp: DireccionPersonaClass;
    let direccionPersonaServiceStub: SinonStubbedInstance<DireccionPersonaService>;

    beforeEach(() => {
      direccionPersonaServiceStub = sinon.createStubInstance<DireccionPersonaService>(DireccionPersonaService);
      direccionPersonaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DireccionPersonaClass>(DireccionPersonaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          direccionPersonaService: () => direccionPersonaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      direccionPersonaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDireccionPersonas();
      await comp.$nextTick();

      // THEN
      expect(direccionPersonaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.direccionPersonas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      direccionPersonaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(direccionPersonaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDireccionPersona();
      await comp.$nextTick();

      // THEN
      expect(direccionPersonaServiceStub.delete.called).toBeTruthy();
      expect(direccionPersonaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
