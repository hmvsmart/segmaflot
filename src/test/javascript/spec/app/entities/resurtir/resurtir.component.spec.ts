/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ResurtirComponent from '@/entities/resurtir/resurtir.vue';
import ResurtirClass from '@/entities/resurtir/resurtir.component';
import ResurtirService from '@/entities/resurtir/resurtir.service';
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
  describe('Resurtir Management Component', () => {
    let wrapper: Wrapper<ResurtirClass>;
    let comp: ResurtirClass;
    let resurtirServiceStub: SinonStubbedInstance<ResurtirService>;

    beforeEach(() => {
      resurtirServiceStub = sinon.createStubInstance<ResurtirService>(ResurtirService);
      resurtirServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ResurtirClass>(ResurtirComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          resurtirService: () => resurtirServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      resurtirServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllResurtirs();
      await comp.$nextTick();

      // THEN
      expect(resurtirServiceStub.retrieve.called).toBeTruthy();
      expect(comp.resurtirs[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      resurtirServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(resurtirServiceStub.retrieve.callCount).toEqual(1);

      comp.removeResurtir();
      await comp.$nextTick();

      // THEN
      expect(resurtirServiceStub.delete.called).toBeTruthy();
      expect(resurtirServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
