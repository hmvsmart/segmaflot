/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ListaPedidoDetailComponent from '@/entities/lista-pedido/lista-pedido-details.vue';
import ListaPedidoClass from '@/entities/lista-pedido/lista-pedido-details.component';
import ListaPedidoService from '@/entities/lista-pedido/lista-pedido.service';
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
  describe('ListaPedido Management Detail Component', () => {
    let wrapper: Wrapper<ListaPedidoClass>;
    let comp: ListaPedidoClass;
    let listaPedidoServiceStub: SinonStubbedInstance<ListaPedidoService>;

    beforeEach(() => {
      listaPedidoServiceStub = sinon.createStubInstance<ListaPedidoService>(ListaPedidoService);

      wrapper = shallowMount<ListaPedidoClass>(ListaPedidoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { listaPedidoService: () => listaPedidoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListaPedido = { id: 123 };
        listaPedidoServiceStub.find.resolves(foundListaPedido);

        // WHEN
        comp.retrieveListaPedido(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listaPedido).toBe(foundListaPedido);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundListaPedido = { id: 123 };
        listaPedidoServiceStub.find.resolves(foundListaPedido);

        // WHEN
        comp.beforeRouteEnter({ params: { listaPedidoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.listaPedido).toBe(foundListaPedido);
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
