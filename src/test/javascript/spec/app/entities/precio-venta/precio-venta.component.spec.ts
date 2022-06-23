/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PrecioVentaComponent from '@/entities/precio-venta/precio-venta.vue';
import PrecioVentaClass from '@/entities/precio-venta/precio-venta.component';
import PrecioVentaService from '@/entities/precio-venta/precio-venta.service';
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
  describe('PrecioVenta Management Component', () => {
    let wrapper: Wrapper<PrecioVentaClass>;
    let comp: PrecioVentaClass;
    let precioVentaServiceStub: SinonStubbedInstance<PrecioVentaService>;

    beforeEach(() => {
      precioVentaServiceStub = sinon.createStubInstance<PrecioVentaService>(PrecioVentaService);
      precioVentaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PrecioVentaClass>(PrecioVentaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          precioVentaService: () => precioVentaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      precioVentaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPrecioVentas();
      await comp.$nextTick();

      // THEN
      expect(precioVentaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.precioVentas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      precioVentaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(precioVentaServiceStub.retrieve.callCount).toEqual(1);

      comp.removePrecioVenta();
      await comp.$nextTick();

      // THEN
      expect(precioVentaServiceStub.delete.called).toBeTruthy();
      expect(precioVentaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
