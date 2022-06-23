/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AditamentoUnidadComponent from '@/entities/aditamento-unidad/aditamento-unidad.vue';
import AditamentoUnidadClass from '@/entities/aditamento-unidad/aditamento-unidad.component';
import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';
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
  describe('AditamentoUnidad Management Component', () => {
    let wrapper: Wrapper<AditamentoUnidadClass>;
    let comp: AditamentoUnidadClass;
    let aditamentoUnidadServiceStub: SinonStubbedInstance<AditamentoUnidadService>;

    beforeEach(() => {
      aditamentoUnidadServiceStub = sinon.createStubInstance<AditamentoUnidadService>(AditamentoUnidadService);
      aditamentoUnidadServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AditamentoUnidadClass>(AditamentoUnidadComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          aditamentoUnidadService: () => aditamentoUnidadServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      aditamentoUnidadServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAditamentoUnidads();
      await comp.$nextTick();

      // THEN
      expect(aditamentoUnidadServiceStub.retrieve.called).toBeTruthy();
      expect(comp.aditamentoUnidads[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      aditamentoUnidadServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(aditamentoUnidadServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAditamentoUnidad();
      await comp.$nextTick();

      // THEN
      expect(aditamentoUnidadServiceStub.delete.called).toBeTruthy();
      expect(aditamentoUnidadServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
