/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UbicacionInventarioDetailComponent from '@/entities/ubicacion-inventario/ubicacion-inventario-details.vue';
import UbicacionInventarioClass from '@/entities/ubicacion-inventario/ubicacion-inventario-details.component';
import UbicacionInventarioService from '@/entities/ubicacion-inventario/ubicacion-inventario.service';
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
  describe('UbicacionInventario Management Detail Component', () => {
    let wrapper: Wrapper<UbicacionInventarioClass>;
    let comp: UbicacionInventarioClass;
    let ubicacionInventarioServiceStub: SinonStubbedInstance<UbicacionInventarioService>;

    beforeEach(() => {
      ubicacionInventarioServiceStub = sinon.createStubInstance<UbicacionInventarioService>(UbicacionInventarioService);

      wrapper = shallowMount<UbicacionInventarioClass>(UbicacionInventarioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ubicacionInventarioService: () => ubicacionInventarioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUbicacionInventario = { id: 123 };
        ubicacionInventarioServiceStub.find.resolves(foundUbicacionInventario);

        // WHEN
        comp.retrieveUbicacionInventario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ubicacionInventario).toBe(foundUbicacionInventario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUbicacionInventario = { id: 123 };
        ubicacionInventarioServiceStub.find.resolves(foundUbicacionInventario);

        // WHEN
        comp.beforeRouteEnter({ params: { ubicacionInventarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ubicacionInventario).toBe(foundUbicacionInventario);
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
