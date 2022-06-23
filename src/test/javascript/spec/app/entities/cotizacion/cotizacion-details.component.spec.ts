/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CotizacionDetailComponent from '@/entities/cotizacion/cotizacion-details.vue';
import CotizacionClass from '@/entities/cotizacion/cotizacion-details.component';
import CotizacionService from '@/entities/cotizacion/cotizacion.service';
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
  describe('Cotizacion Management Detail Component', () => {
    let wrapper: Wrapper<CotizacionClass>;
    let comp: CotizacionClass;
    let cotizacionServiceStub: SinonStubbedInstance<CotizacionService>;

    beforeEach(() => {
      cotizacionServiceStub = sinon.createStubInstance<CotizacionService>(CotizacionService);

      wrapper = shallowMount<CotizacionClass>(CotizacionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { cotizacionService: () => cotizacionServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCotizacion = { id: 123 };
        cotizacionServiceStub.find.resolves(foundCotizacion);

        // WHEN
        comp.retrieveCotizacion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cotizacion).toBe(foundCotizacion);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCotizacion = { id: 123 };
        cotizacionServiceStub.find.resolves(foundCotizacion);

        // WHEN
        comp.beforeRouteEnter({ params: { cotizacionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cotizacion).toBe(foundCotizacion);
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
