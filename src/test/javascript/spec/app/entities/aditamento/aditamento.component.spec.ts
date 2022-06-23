/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AditamentoComponent from '@/entities/aditamento/aditamento.vue';
import AditamentoClass from '@/entities/aditamento/aditamento.component';
import AditamentoService from '@/entities/aditamento/aditamento.service';
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
  describe('Aditamento Management Component', () => {
    let wrapper: Wrapper<AditamentoClass>;
    let comp: AditamentoClass;
    let aditamentoServiceStub: SinonStubbedInstance<AditamentoService>;

    beforeEach(() => {
      aditamentoServiceStub = sinon.createStubInstance<AditamentoService>(AditamentoService);
      aditamentoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AditamentoClass>(AditamentoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          aditamentoService: () => aditamentoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      aditamentoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAditamentos();
      await comp.$nextTick();

      // THEN
      expect(aditamentoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.aditamentos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      aditamentoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(aditamentoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAditamento();
      await comp.$nextTick();

      // THEN
      expect(aditamentoServiceStub.delete.called).toBeTruthy();
      expect(aditamentoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
