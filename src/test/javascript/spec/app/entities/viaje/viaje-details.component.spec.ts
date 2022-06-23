/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ViajeDetailComponent from '@/entities/viaje/viaje-details.vue';
import ViajeClass from '@/entities/viaje/viaje-details.component';
import ViajeService from '@/entities/viaje/viaje.service';
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
  describe('Viaje Management Detail Component', () => {
    let wrapper: Wrapper<ViajeClass>;
    let comp: ViajeClass;
    let viajeServiceStub: SinonStubbedInstance<ViajeService>;

    beforeEach(() => {
      viajeServiceStub = sinon.createStubInstance<ViajeService>(ViajeService);

      wrapper = shallowMount<ViajeClass>(ViajeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { viajeService: () => viajeServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundViaje = { id: 123 };
        viajeServiceStub.find.resolves(foundViaje);

        // WHEN
        comp.retrieveViaje(123);
        await comp.$nextTick();

        // THEN
        expect(comp.viaje).toBe(foundViaje);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundViaje = { id: 123 };
        viajeServiceStub.find.resolves(foundViaje);

        // WHEN
        comp.beforeRouteEnter({ params: { viajeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.viaje).toBe(foundViaje);
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
