/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PolizaComponent from '@/entities/poliza/poliza.vue';
import PolizaClass from '@/entities/poliza/poliza.component';
import PolizaService from '@/entities/poliza/poliza.service';
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
  describe('Poliza Management Component', () => {
    let wrapper: Wrapper<PolizaClass>;
    let comp: PolizaClass;
    let polizaServiceStub: SinonStubbedInstance<PolizaService>;

    beforeEach(() => {
      polizaServiceStub = sinon.createStubInstance<PolizaService>(PolizaService);
      polizaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PolizaClass>(PolizaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          polizaService: () => polizaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      polizaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPolizas();
      await comp.$nextTick();

      // THEN
      expect(polizaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.polizas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      polizaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(polizaServiceStub.retrieve.callCount).toEqual(1);

      comp.removePoliza();
      await comp.$nextTick();

      // THEN
      expect(polizaServiceStub.delete.called).toBeTruthy();
      expect(polizaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
