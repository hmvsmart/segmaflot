/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PerdidaComponent from '@/entities/perdida/perdida.vue';
import PerdidaClass from '@/entities/perdida/perdida.component';
import PerdidaService from '@/entities/perdida/perdida.service';
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
  describe('Perdida Management Component', () => {
    let wrapper: Wrapper<PerdidaClass>;
    let comp: PerdidaClass;
    let perdidaServiceStub: SinonStubbedInstance<PerdidaService>;

    beforeEach(() => {
      perdidaServiceStub = sinon.createStubInstance<PerdidaService>(PerdidaService);
      perdidaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PerdidaClass>(PerdidaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          perdidaService: () => perdidaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      perdidaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPerdidas();
      await comp.$nextTick();

      // THEN
      expect(perdidaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.perdidas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      perdidaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(perdidaServiceStub.retrieve.callCount).toEqual(1);

      comp.removePerdida();
      await comp.$nextTick();

      // THEN
      expect(perdidaServiceStub.delete.called).toBeTruthy();
      expect(perdidaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
