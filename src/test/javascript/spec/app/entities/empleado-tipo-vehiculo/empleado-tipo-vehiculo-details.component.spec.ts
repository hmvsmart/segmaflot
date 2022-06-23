/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EmpleadoTipoVehiculoDetailComponent from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo-details.vue';
import EmpleadoTipoVehiculoClass from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo-details.component';
import EmpleadoTipoVehiculoService from '@/entities/empleado-tipo-vehiculo/empleado-tipo-vehiculo.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EmpleadoTipoVehiculo Management Detail Component', () => {
    let wrapper: Wrapper<EmpleadoTipoVehiculoClass>;
    let comp: EmpleadoTipoVehiculoClass;
    let empleadoTipoVehiculoServiceStub: SinonStubbedInstance<EmpleadoTipoVehiculoService>;

    beforeEach(() => {
      empleadoTipoVehiculoServiceStub = sinon.createStubInstance<EmpleadoTipoVehiculoService>(EmpleadoTipoVehiculoService);

      wrapper = shallowMount<EmpleadoTipoVehiculoClass>(EmpleadoTipoVehiculoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { empleadoTipoVehiculoService: () => empleadoTipoVehiculoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEmpleadoTipoVehiculo = { id: 123 };
        empleadoTipoVehiculoServiceStub.find.resolves(foundEmpleadoTipoVehiculo);

        // WHEN
        comp.retrieveEmpleadoTipoVehiculo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.empleadoTipoVehiculo).toBe(foundEmpleadoTipoVehiculo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEmpleadoTipoVehiculo = { id: 123 };
        empleadoTipoVehiculoServiceStub.find.resolves(foundEmpleadoTipoVehiculo);

        // WHEN
        comp.beforeRouteEnter({ params: { empleadoTipoVehiculoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.empleadoTipoVehiculo).toBe(foundEmpleadoTipoVehiculo);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
