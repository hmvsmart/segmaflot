/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import InventarioComponent from '@/entities/inventario/inventario.vue';
import InventarioClass from '@/entities/inventario/inventario.component';
import InventarioService from '@/entities/inventario/inventario.service';
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
  describe('Inventario Management Component', () => {
    let wrapper: Wrapper<InventarioClass>;
    let comp: InventarioClass;
    let inventarioServiceStub: SinonStubbedInstance<InventarioService>;

    beforeEach(() => {
      inventarioServiceStub = sinon.createStubInstance<InventarioService>(InventarioService);
      inventarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<InventarioClass>(InventarioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          inventarioService: () => inventarioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      inventarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllInventarios();
      await comp.$nextTick();

      // THEN
      expect(inventarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.inventarios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      inventarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(inventarioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeInventario();
      await comp.$nextTick();

      // THEN
      expect(inventarioServiceStub.delete.called).toBeTruthy();
      expect(inventarioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
