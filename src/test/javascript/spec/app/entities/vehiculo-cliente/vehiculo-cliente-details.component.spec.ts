/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VehiculoClienteDetailComponent from '@/entities/vehiculo-cliente/vehiculo-cliente-details.vue';
import VehiculoClienteClass from '@/entities/vehiculo-cliente/vehiculo-cliente-details.component';
import VehiculoClienteService from '@/entities/vehiculo-cliente/vehiculo-cliente.service';
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
  describe('VehiculoCliente Management Detail Component', () => {
    let wrapper: Wrapper<VehiculoClienteClass>;
    let comp: VehiculoClienteClass;
    let vehiculoClienteServiceStub: SinonStubbedInstance<VehiculoClienteService>;

    beforeEach(() => {
      vehiculoClienteServiceStub = sinon.createStubInstance<VehiculoClienteService>(VehiculoClienteService);

      wrapper = shallowMount<VehiculoClienteClass>(VehiculoClienteDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { vehiculoClienteService: () => vehiculoClienteServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVehiculoCliente = { id: 123 };
        vehiculoClienteServiceStub.find.resolves(foundVehiculoCliente);

        // WHEN
        comp.retrieveVehiculoCliente(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vehiculoCliente).toBe(foundVehiculoCliente);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVehiculoCliente = { id: 123 };
        vehiculoClienteServiceStub.find.resolves(foundVehiculoCliente);

        // WHEN
        comp.beforeRouteEnter({ params: { vehiculoClienteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vehiculoCliente).toBe(foundVehiculoCliente);
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
