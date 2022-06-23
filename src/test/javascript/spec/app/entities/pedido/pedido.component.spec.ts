/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PedidoComponent from '@/entities/pedido/pedido.vue';
import PedidoClass from '@/entities/pedido/pedido.component';
import PedidoService from '@/entities/pedido/pedido.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Pedido Management Component', () => {
    let wrapper: Wrapper<PedidoClass>;
    let comp: PedidoClass;
    let pedidoServiceStub: SinonStubbedInstance<PedidoService>;

    beforeEach(() => {
      pedidoServiceStub = sinon.createStubInstance<PedidoService>(PedidoService);
      pedidoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PedidoClass>(PedidoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          pedidoService: () => pedidoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      pedidoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPedidos();
      await comp.$nextTick();

      // THEN
      expect(pedidoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.pedidos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      pedidoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(pedidoServiceStub.retrieve.callCount).toEqual(1);

      comp.removePedido();
      await comp.$nextTick();

      // THEN
      expect(pedidoServiceStub.delete.called).toBeTruthy();
      expect(pedidoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
