/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ProductoComponent from '@/entities/producto/producto.vue';
import ProductoClass from '@/entities/producto/producto.component';
import ProductoService from '@/entities/producto/producto.service';
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
  describe('Producto Management Component', () => {
    let wrapper: Wrapper<ProductoClass>;
    let comp: ProductoClass;
    let productoServiceStub: SinonStubbedInstance<ProductoService>;

    beforeEach(() => {
      productoServiceStub = sinon.createStubInstance<ProductoService>(ProductoService);
      productoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProductoClass>(ProductoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          productoService: () => productoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      productoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProductos();
      await comp.$nextTick();

      // THEN
      expect(productoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.productos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      productoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(productoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeProducto();
      await comp.$nextTick();

      // THEN
      expect(productoServiceStub.delete.called).toBeTruthy();
      expect(productoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
