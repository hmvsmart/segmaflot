/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PagoComponent from '@/entities/pago/pago.vue';
import PagoClass from '@/entities/pago/pago.component';
import PagoService from '@/entities/pago/pago.service';
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
  describe('Pago Management Component', () => {
    let wrapper: Wrapper<PagoClass>;
    let comp: PagoClass;
    let pagoServiceStub: SinonStubbedInstance<PagoService>;

    beforeEach(() => {
      pagoServiceStub = sinon.createStubInstance<PagoService>(PagoService);
      pagoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PagoClass>(PagoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          pagoService: () => pagoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      pagoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPagos();
      await comp.$nextTick();

      // THEN
      expect(pagoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.pagos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      pagoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(pagoServiceStub.retrieve.callCount).toEqual(1);

      comp.removePago();
      await comp.$nextTick();

      // THEN
      expect(pagoServiceStub.delete.called).toBeTruthy();
      expect(pagoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
