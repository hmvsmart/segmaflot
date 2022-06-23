/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import OperadorUnidadComponent from '@/entities/operador-unidad/operador-unidad.vue';
import OperadorUnidadClass from '@/entities/operador-unidad/operador-unidad.component';
import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';
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
  describe('OperadorUnidad Management Component', () => {
    let wrapper: Wrapper<OperadorUnidadClass>;
    let comp: OperadorUnidadClass;
    let operadorUnidadServiceStub: SinonStubbedInstance<OperadorUnidadService>;

    beforeEach(() => {
      operadorUnidadServiceStub = sinon.createStubInstance<OperadorUnidadService>(OperadorUnidadService);
      operadorUnidadServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<OperadorUnidadClass>(OperadorUnidadComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          operadorUnidadService: () => operadorUnidadServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      operadorUnidadServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllOperadorUnidads();
      await comp.$nextTick();

      // THEN
      expect(operadorUnidadServiceStub.retrieve.called).toBeTruthy();
      expect(comp.operadorUnidads[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      operadorUnidadServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(operadorUnidadServiceStub.retrieve.callCount).toEqual(1);

      comp.removeOperadorUnidad();
      await comp.$nextTick();

      // THEN
      expect(operadorUnidadServiceStub.delete.called).toBeTruthy();
      expect(operadorUnidadServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
