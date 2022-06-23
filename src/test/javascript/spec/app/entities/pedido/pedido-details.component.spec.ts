/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PedidoDetailComponent from '@/entities/pedido/pedido-details.vue';
import PedidoClass from '@/entities/pedido/pedido-details.component';
import PedidoService from '@/entities/pedido/pedido.service';
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
  describe('Pedido Management Detail Component', () => {
    let wrapper: Wrapper<PedidoClass>;
    let comp: PedidoClass;
    let pedidoServiceStub: SinonStubbedInstance<PedidoService>;

    beforeEach(() => {
      pedidoServiceStub = sinon.createStubInstance<PedidoService>(PedidoService);

      wrapper = shallowMount<PedidoClass>(PedidoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { pedidoService: () => pedidoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPedido = { id: 123 };
        pedidoServiceStub.find.resolves(foundPedido);

        // WHEN
        comp.retrievePedido(123);
        await comp.$nextTick();

        // THEN
        expect(comp.pedido).toBe(foundPedido);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPedido = { id: 123 };
        pedidoServiceStub.find.resolves(foundPedido);

        // WHEN
        comp.beforeRouteEnter({ params: { pedidoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.pedido).toBe(foundPedido);
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
