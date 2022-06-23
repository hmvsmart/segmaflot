/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VentaPedidoComponent from '@/entities/venta-pedido/venta-pedido.vue';
import VentaPedidoClass from '@/entities/venta-pedido/venta-pedido.component';
import VentaPedidoService from '@/entities/venta-pedido/venta-pedido.service';
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
  describe('VentaPedido Management Component', () => {
    let wrapper: Wrapper<VentaPedidoClass>;
    let comp: VentaPedidoClass;
    let ventaPedidoServiceStub: SinonStubbedInstance<VentaPedidoService>;

    beforeEach(() => {
      ventaPedidoServiceStub = sinon.createStubInstance<VentaPedidoService>(VentaPedidoService);
      ventaPedidoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VentaPedidoClass>(VentaPedidoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ventaPedidoService: () => ventaPedidoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ventaPedidoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVentaPedidos();
      await comp.$nextTick();

      // THEN
      expect(ventaPedidoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.ventaPedidos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ventaPedidoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(ventaPedidoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVentaPedido();
      await comp.$nextTick();

      // THEN
      expect(ventaPedidoServiceStub.delete.called).toBeTruthy();
      expect(ventaPedidoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
