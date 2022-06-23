/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ClienteComponent from '@/entities/cliente/cliente.vue';
import ClienteClass from '@/entities/cliente/cliente.component';
import ClienteService from '@/entities/cliente/cliente.service';
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
  describe('Cliente Management Component', () => {
    let wrapper: Wrapper<ClienteClass>;
    let comp: ClienteClass;
    let clienteServiceStub: SinonStubbedInstance<ClienteService>;

    beforeEach(() => {
      clienteServiceStub = sinon.createStubInstance<ClienteService>(ClienteService);
      clienteServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ClienteClass>(ClienteComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          clienteService: () => clienteServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      clienteServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllClientes();
      await comp.$nextTick();

      // THEN
      expect(clienteServiceStub.retrieve.called).toBeTruthy();
      expect(comp.clientes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      clienteServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(clienteServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCliente();
      await comp.$nextTick();

      // THEN
      expect(clienteServiceStub.delete.called).toBeTruthy();
      expect(clienteServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
