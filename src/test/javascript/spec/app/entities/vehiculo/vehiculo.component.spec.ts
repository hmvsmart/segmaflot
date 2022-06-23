/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VehiculoComponent from '@/entities/vehiculo/vehiculo.vue';
import VehiculoClass from '@/entities/vehiculo/vehiculo.component';
import VehiculoService from '@/entities/vehiculo/vehiculo.service';
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
  describe('Vehiculo Management Component', () => {
    let wrapper: Wrapper<VehiculoClass>;
    let comp: VehiculoClass;
    let vehiculoServiceStub: SinonStubbedInstance<VehiculoService>;

    beforeEach(() => {
      vehiculoServiceStub = sinon.createStubInstance<VehiculoService>(VehiculoService);
      vehiculoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VehiculoClass>(VehiculoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          vehiculoService: () => vehiculoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      vehiculoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVehiculos();
      await comp.$nextTick();

      // THEN
      expect(vehiculoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.vehiculos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      vehiculoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(vehiculoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVehiculo();
      await comp.$nextTick();

      // THEN
      expect(vehiculoServiceStub.delete.called).toBeTruthy();
      expect(vehiculoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
