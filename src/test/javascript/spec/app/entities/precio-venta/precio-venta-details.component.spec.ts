/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PrecioVentaDetailComponent from '@/entities/precio-venta/precio-venta-details.vue';
import PrecioVentaClass from '@/entities/precio-venta/precio-venta-details.component';
import PrecioVentaService from '@/entities/precio-venta/precio-venta.service';
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
  describe('PrecioVenta Management Detail Component', () => {
    let wrapper: Wrapper<PrecioVentaClass>;
    let comp: PrecioVentaClass;
    let precioVentaServiceStub: SinonStubbedInstance<PrecioVentaService>;

    beforeEach(() => {
      precioVentaServiceStub = sinon.createStubInstance<PrecioVentaService>(PrecioVentaService);

      wrapper = shallowMount<PrecioVentaClass>(PrecioVentaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { precioVentaService: () => precioVentaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrecioVenta = { id: 123 };
        precioVentaServiceStub.find.resolves(foundPrecioVenta);

        // WHEN
        comp.retrievePrecioVenta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.precioVenta).toBe(foundPrecioVenta);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrecioVenta = { id: 123 };
        precioVentaServiceStub.find.resolves(foundPrecioVenta);

        // WHEN
        comp.beforeRouteEnter({ params: { precioVentaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.precioVenta).toBe(foundPrecioVenta);
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
