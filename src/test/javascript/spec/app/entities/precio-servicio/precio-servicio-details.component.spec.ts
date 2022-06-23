/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PrecioServicioDetailComponent from '@/entities/precio-servicio/precio-servicio-details.vue';
import PrecioServicioClass from '@/entities/precio-servicio/precio-servicio-details.component';
import PrecioServicioService from '@/entities/precio-servicio/precio-servicio.service';
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
  describe('PrecioServicio Management Detail Component', () => {
    let wrapper: Wrapper<PrecioServicioClass>;
    let comp: PrecioServicioClass;
    let precioServicioServiceStub: SinonStubbedInstance<PrecioServicioService>;

    beforeEach(() => {
      precioServicioServiceStub = sinon.createStubInstance<PrecioServicioService>(PrecioServicioService);

      wrapper = shallowMount<PrecioServicioClass>(PrecioServicioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { precioServicioService: () => precioServicioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrecioServicio = { id: 123 };
        precioServicioServiceStub.find.resolves(foundPrecioServicio);

        // WHEN
        comp.retrievePrecioServicio(123);
        await comp.$nextTick();

        // THEN
        expect(comp.precioServicio).toBe(foundPrecioServicio);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrecioServicio = { id: 123 };
        precioServicioServiceStub.find.resolves(foundPrecioServicio);

        // WHEN
        comp.beforeRouteEnter({ params: { precioServicioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.precioServicio).toBe(foundPrecioServicio);
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
