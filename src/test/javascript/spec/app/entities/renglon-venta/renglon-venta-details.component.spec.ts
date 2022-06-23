/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RenglonVentaDetailComponent from '@/entities/renglon-venta/renglon-venta-details.vue';
import RenglonVentaClass from '@/entities/renglon-venta/renglon-venta-details.component';
import RenglonVentaService from '@/entities/renglon-venta/renglon-venta.service';
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
  describe('RenglonVenta Management Detail Component', () => {
    let wrapper: Wrapper<RenglonVentaClass>;
    let comp: RenglonVentaClass;
    let renglonVentaServiceStub: SinonStubbedInstance<RenglonVentaService>;

    beforeEach(() => {
      renglonVentaServiceStub = sinon.createStubInstance<RenglonVentaService>(RenglonVentaService);

      wrapper = shallowMount<RenglonVentaClass>(RenglonVentaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { renglonVentaService: () => renglonVentaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRenglonVenta = { id: 123 };
        renglonVentaServiceStub.find.resolves(foundRenglonVenta);

        // WHEN
        comp.retrieveRenglonVenta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.renglonVenta).toBe(foundRenglonVenta);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRenglonVenta = { id: 123 };
        renglonVentaServiceStub.find.resolves(foundRenglonVenta);

        // WHEN
        comp.beforeRouteEnter({ params: { renglonVentaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.renglonVenta).toBe(foundRenglonVenta);
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
