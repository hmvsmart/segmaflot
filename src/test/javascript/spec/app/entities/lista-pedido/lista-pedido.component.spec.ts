/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ListaPedidoComponent from '@/entities/lista-pedido/lista-pedido.vue';
import ListaPedidoClass from '@/entities/lista-pedido/lista-pedido.component';
import ListaPedidoService from '@/entities/lista-pedido/lista-pedido.service';
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
  describe('ListaPedido Management Component', () => {
    let wrapper: Wrapper<ListaPedidoClass>;
    let comp: ListaPedidoClass;
    let listaPedidoServiceStub: SinonStubbedInstance<ListaPedidoService>;

    beforeEach(() => {
      listaPedidoServiceStub = sinon.createStubInstance<ListaPedidoService>(ListaPedidoService);
      listaPedidoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListaPedidoClass>(ListaPedidoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          listaPedidoService: () => listaPedidoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listaPedidoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListaPedidos();
      await comp.$nextTick();

      // THEN
      expect(listaPedidoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listaPedidos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listaPedidoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(listaPedidoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeListaPedido();
      await comp.$nextTick();

      // THEN
      expect(listaPedidoServiceStub.delete.called).toBeTruthy();
      expect(listaPedidoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
