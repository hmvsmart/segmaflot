/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UnidadViajeDetailComponent from '@/entities/unidad-viaje/unidad-viaje-details.vue';
import UnidadViajeClass from '@/entities/unidad-viaje/unidad-viaje-details.component';
import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';
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
  describe('UnidadViaje Management Detail Component', () => {
    let wrapper: Wrapper<UnidadViajeClass>;
    let comp: UnidadViajeClass;
    let unidadViajeServiceStub: SinonStubbedInstance<UnidadViajeService>;

    beforeEach(() => {
      unidadViajeServiceStub = sinon.createStubInstance<UnidadViajeService>(UnidadViajeService);

      wrapper = shallowMount<UnidadViajeClass>(UnidadViajeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { unidadViajeService: () => unidadViajeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUnidadViaje = { id: 123 };
        unidadViajeServiceStub.find.resolves(foundUnidadViaje);

        // WHEN
        comp.retrieveUnidadViaje(123);
        await comp.$nextTick();

        // THEN
        expect(comp.unidadViaje).toBe(foundUnidadViaje);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidadViaje = { id: 123 };
        unidadViajeServiceStub.find.resolves(foundUnidadViaje);

        // WHEN
        comp.beforeRouteEnter({ params: { unidadViajeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.unidadViaje).toBe(foundUnidadViaje);
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
