/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EmpleadoTipoVehiculoComponent from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo.vue';
import EmpleadoTipoVehiculoClass from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo.component';
import EmpleadoTipoVehiculoService from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo.service';
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
  describe('EmpleadoTipoVehiculo Management Component', () => {
    let wrapper: Wrapper<EmpleadoTipoVehiculoClass>;
    let comp: EmpleadoTipoVehiculoClass;
    let empleadoTipoVehiculoServiceStub: SinonStubbedInstance<EmpleadoTipoVehiculoService>;

    beforeEach(() => {
      empleadoTipoVehiculoServiceStub = sinon.createStubInstance<EmpleadoTipoVehiculoService>(EmpleadoTipoVehiculoService);
      empleadoTipoVehiculoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmpleadoTipoVehiculoClass>(EmpleadoTipoVehiculoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          empleadoTipoVehiculoService: () => empleadoTipoVehiculoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      empleadoTipoVehiculoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmpleadoTipoVehiculos();
      await comp.$nextTick();

      // THEN
      expect(empleadoTipoVehiculoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.empleadoTipoVehiculos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      empleadoTipoVehiculoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(empleadoTipoVehiculoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEmpleadoTipoVehiculo();
      await comp.$nextTick();

      // THEN
      expect(empleadoTipoVehiculoServiceStub.delete.called).toBeTruthy();
      expect(empleadoTipoVehiculoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
