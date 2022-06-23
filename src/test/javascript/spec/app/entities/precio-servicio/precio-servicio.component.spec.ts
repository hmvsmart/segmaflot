/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PrecioServicioComponent from '@/entities/precio-servicio/precio-servicio.vue';
import PrecioServicioClass from '@/entities/precio-servicio/precio-servicio.component';
import PrecioServicioService from '@/entities/precio-servicio/precio-servicio.service';
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
  describe('PrecioServicio Management Component', () => {
    let wrapper: Wrapper<PrecioServicioClass>;
    let comp: PrecioServicioClass;
    let precioServicioServiceStub: SinonStubbedInstance<PrecioServicioService>;

    beforeEach(() => {
      precioServicioServiceStub = sinon.createStubInstance<PrecioServicioService>(PrecioServicioService);
      precioServicioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PrecioServicioClass>(PrecioServicioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          precioServicioService: () => precioServicioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      precioServicioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPrecioServicios();
      await comp.$nextTick();

      // THEN
      expect(precioServicioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.precioServicios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      precioServicioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(precioServicioServiceStub.retrieve.callCount).toEqual(1);

      comp.removePrecioServicio();
      await comp.$nextTick();

      // THEN
      expect(precioServicioServiceStub.delete.called).toBeTruthy();
      expect(precioServicioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
