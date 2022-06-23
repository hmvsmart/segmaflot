/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VentaPedidoDetailComponent from '@/entities/venta-pedido/venta-pedido-details.vue';
import VentaPedidoClass from '@/entities/venta-pedido/venta-pedido-details.component';
import VentaPedidoService from '@/entities/venta-pedido/venta-pedido.service';
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
  describe('VentaPedido Management Detail Component', () => {
    let wrapper: Wrapper<VentaPedidoClass>;
    let comp: VentaPedidoClass;
    let ventaPedidoServiceStub: SinonStubbedInstance<VentaPedidoService>;

    beforeEach(() => {
      ventaPedidoServiceStub = sinon.createStubInstance<VentaPedidoService>(VentaPedidoService);

      wrapper = shallowMount<VentaPedidoClass>(VentaPedidoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { ventaPedidoService: () => ventaPedidoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVentaPedido = { id: 123 };
        ventaPedidoServiceStub.find.resolves(foundVentaPedido);

        // WHEN
        comp.retrieveVentaPedido(123);
        await comp.$nextTick();

        // THEN
        expect(comp.ventaPedido).toBe(foundVentaPedido);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVentaPedido = { id: 123 };
        ventaPedidoServiceStub.find.resolves(foundVentaPedido);

        // WHEN
        comp.beforeRouteEnter({ params: { ventaPedidoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ventaPedido).toBe(foundVentaPedido);
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
