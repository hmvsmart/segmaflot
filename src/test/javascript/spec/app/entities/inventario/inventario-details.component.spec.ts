/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import InventarioDetailComponent from '@/entities/inventario/inventario-details.vue';
import InventarioClass from '@/entities/inventario/inventario-details.component';
import InventarioService from '@/entities/inventario/inventario.service';
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
  describe('Inventario Management Detail Component', () => {
    let wrapper: Wrapper<InventarioClass>;
    let comp: InventarioClass;
    let inventarioServiceStub: SinonStubbedInstance<InventarioService>;

    beforeEach(() => {
      inventarioServiceStub = sinon.createStubInstance<InventarioService>(InventarioService);

      wrapper = shallowMount<InventarioClass>(InventarioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { inventarioService: () => inventarioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundInventario = { id: 123 };
        inventarioServiceStub.find.resolves(foundInventario);

        // WHEN
        comp.retrieveInventario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.inventario).toBe(foundInventario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundInventario = { id: 123 };
        inventarioServiceStub.find.resolves(foundInventario);

        // WHEN
        comp.beforeRouteEnter({ params: { inventarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.inventario).toBe(foundInventario);
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
