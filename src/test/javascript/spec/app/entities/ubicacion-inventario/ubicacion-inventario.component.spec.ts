/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UbicacionInventarioComponent from '@/entities/ubicacion-inventario/ubicacion-inventario.vue';
import UbicacionInventarioClass from '@/entities/ubicacion-inventario/ubicacion-inventario.component';
import UbicacionInventarioService from '@/entities/ubicacion-inventario/ubicacion-inventario.service';
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
  describe('UbicacionInventario Management Component', () => {
    let wrapper: Wrapper<UbicacionInventarioClass>;
    let comp: UbicacionInventarioClass;
    let ubicacionInventarioServiceStub: SinonStubbedInstance<UbicacionInventarioService>;

    beforeEach(() => {
      ubicacionInventarioServiceStub = sinon.createStubInstance<UbicacionInventarioService>(UbicacionInventarioService);
      ubicacionInventarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UbicacionInventarioClass>(UbicacionInventarioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ubicacionInventarioService: () => ubicacionInventarioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ubicacionInventarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUbicacionInventarios();
      await comp.$nextTick();

      // THEN
      expect(ubicacionInventarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.ubicacionInventarios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ubicacionInventarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(ubicacionInventarioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUbicacionInventario();
      await comp.$nextTick();

      // THEN
      expect(ubicacionInventarioServiceStub.delete.called).toBeTruthy();
      expect(ubicacionInventarioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
