/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PagoDetailComponent from '@/entities/pago/pago-details.vue';
import PagoClass from '@/entities/pago/pago-details.component';
import PagoService from '@/entities/pago/pago.service';
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
  describe('Pago Management Detail Component', () => {
    let wrapper: Wrapper<PagoClass>;
    let comp: PagoClass;
    let pagoServiceStub: SinonStubbedInstance<PagoService>;

    beforeEach(() => {
      pagoServiceStub = sinon.createStubInstance<PagoService>(PagoService);

      wrapper = shallowMount<PagoClass>(PagoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { pagoService: () => pagoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPago = { id: 123 };
        pagoServiceStub.find.resolves(foundPago);

        // WHEN
        comp.retrievePago(123);
        await comp.$nextTick();

        // THEN
        expect(comp.pago).toBe(foundPago);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPago = { id: 123 };
        pagoServiceStub.find.resolves(foundPago);

        // WHEN
        comp.beforeRouteEnter({ params: { pagoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.pago).toBe(foundPago);
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
