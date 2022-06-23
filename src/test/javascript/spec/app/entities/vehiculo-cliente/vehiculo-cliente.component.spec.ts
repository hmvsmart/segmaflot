/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VehiculoClienteComponent from '@/entities/vehiculo-cliente/vehiculo-cliente.vue';
import VehiculoClienteClass from '@/entities/vehiculo-cliente/vehiculo-cliente.component';
import VehiculoClienteService from '@/entities/vehiculo-cliente/vehiculo-cliente.service';
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
  describe('VehiculoCliente Management Component', () => {
    let wrapper: Wrapper<VehiculoClienteClass>;
    let comp: VehiculoClienteClass;
    let vehiculoClienteServiceStub: SinonStubbedInstance<VehiculoClienteService>;

    beforeEach(() => {
      vehiculoClienteServiceStub = sinon.createStubInstance<VehiculoClienteService>(VehiculoClienteService);
      vehiculoClienteServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VehiculoClienteClass>(VehiculoClienteComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          vehiculoClienteService: () => vehiculoClienteServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      vehiculoClienteServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVehiculoClientes();
      await comp.$nextTick();

      // THEN
      expect(vehiculoClienteServiceStub.retrieve.called).toBeTruthy();
      expect(comp.vehiculoClientes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      vehiculoClienteServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(vehiculoClienteServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVehiculoCliente();
      await comp.$nextTick();

      // THEN
      expect(vehiculoClienteServiceStub.delete.called).toBeTruthy();
      expect(vehiculoClienteServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
