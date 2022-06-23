/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RenglonVentaComponent from '@/entities/renglon-venta/renglon-venta.vue';
import RenglonVentaClass from '@/entities/renglon-venta/renglon-venta.component';
import RenglonVentaService from '@/entities/renglon-venta/renglon-venta.service';
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
  describe('RenglonVenta Management Component', () => {
    let wrapper: Wrapper<RenglonVentaClass>;
    let comp: RenglonVentaClass;
    let renglonVentaServiceStub: SinonStubbedInstance<RenglonVentaService>;

    beforeEach(() => {
      renglonVentaServiceStub = sinon.createStubInstance<RenglonVentaService>(RenglonVentaService);
      renglonVentaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RenglonVentaClass>(RenglonVentaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          renglonVentaService: () => renglonVentaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      renglonVentaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRenglonVentas();
      await comp.$nextTick();

      // THEN
      expect(renglonVentaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.renglonVentas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      renglonVentaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(renglonVentaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRenglonVenta();
      await comp.$nextTick();

      // THEN
      expect(renglonVentaServiceStub.delete.called).toBeTruthy();
      expect(renglonVentaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
