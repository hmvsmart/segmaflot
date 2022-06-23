/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UnidadViajeComponent from '@/entities/unidad-viaje/unidad-viaje.vue';
import UnidadViajeClass from '@/entities/unidad-viaje/unidad-viaje.component';
import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';
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
  describe('UnidadViaje Management Component', () => {
    let wrapper: Wrapper<UnidadViajeClass>;
    let comp: UnidadViajeClass;
    let unidadViajeServiceStub: SinonStubbedInstance<UnidadViajeService>;

    beforeEach(() => {
      unidadViajeServiceStub = sinon.createStubInstance<UnidadViajeService>(UnidadViajeService);
      unidadViajeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UnidadViajeClass>(UnidadViajeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          unidadViajeService: () => unidadViajeServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      unidadViajeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUnidadViajes();
      await comp.$nextTick();

      // THEN
      expect(unidadViajeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.unidadViajes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      unidadViajeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(unidadViajeServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUnidadViaje();
      await comp.$nextTick();

      // THEN
      expect(unidadViajeServiceStub.delete.called).toBeTruthy();
      expect(unidadViajeServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
