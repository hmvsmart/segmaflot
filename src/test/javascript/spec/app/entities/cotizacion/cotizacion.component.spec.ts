/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CotizacionComponent from '@/entities/cotizacion/cotizacion.vue';
import CotizacionClass from '@/entities/cotizacion/cotizacion.component';
import CotizacionService from '@/entities/cotizacion/cotizacion.service';
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
  describe('Cotizacion Management Component', () => {
    let wrapper: Wrapper<CotizacionClass>;
    let comp: CotizacionClass;
    let cotizacionServiceStub: SinonStubbedInstance<CotizacionService>;

    beforeEach(() => {
      cotizacionServiceStub = sinon.createStubInstance<CotizacionService>(CotizacionService);
      cotizacionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CotizacionClass>(CotizacionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          cotizacionService: () => cotizacionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      cotizacionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCotizacions();
      await comp.$nextTick();

      // THEN
      expect(cotizacionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cotizacions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      cotizacionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(cotizacionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCotizacion();
      await comp.$nextTick();

      // THEN
      expect(cotizacionServiceStub.delete.called).toBeTruthy();
      expect(cotizacionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
