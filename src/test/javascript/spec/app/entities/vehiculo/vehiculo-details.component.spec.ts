/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VehiculoDetailComponent from '@/entities/vehiculo/vehiculo-details.vue';
import VehiculoClass from '@/entities/vehiculo/vehiculo-details.component';
import VehiculoService from '@/entities/vehiculo/vehiculo.service';
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
  describe('Vehiculo Management Detail Component', () => {
    let wrapper: Wrapper<VehiculoClass>;
    let comp: VehiculoClass;
    let vehiculoServiceStub: SinonStubbedInstance<VehiculoService>;

    beforeEach(() => {
      vehiculoServiceStub = sinon.createStubInstance<VehiculoService>(VehiculoService);

      wrapper = shallowMount<VehiculoClass>(VehiculoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { vehiculoService: () => vehiculoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVehiculo = { id: 123 };
        vehiculoServiceStub.find.resolves(foundVehiculo);

        // WHEN
        comp.retrieveVehiculo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vehiculo).toBe(foundVehiculo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVehiculo = { id: 123 };
        vehiculoServiceStub.find.resolves(foundVehiculo);

        // WHEN
        comp.beforeRouteEnter({ params: { vehiculoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vehiculo).toBe(foundVehiculo);
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
