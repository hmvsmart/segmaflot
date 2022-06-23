/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VentaComponent from '@/entities/venta/venta.vue';
import VentaClass from '@/entities/venta/venta.component';
import VentaService from '@/entities/venta/venta.service';
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
  describe('Venta Management Component', () => {
    let wrapper: Wrapper<VentaClass>;
    let comp: VentaClass;
    let ventaServiceStub: SinonStubbedInstance<VentaService>;

    beforeEach(() => {
      ventaServiceStub = sinon.createStubInstance<VentaService>(VentaService);
      ventaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VentaClass>(VentaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ventaService: () => ventaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ventaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVentas();
      await comp.$nextTick();

      // THEN
      expect(ventaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.ventas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ventaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(ventaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVenta();
      await comp.$nextTick();

      // THEN
      expect(ventaServiceStub.delete.called).toBeTruthy();
      expect(ventaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
