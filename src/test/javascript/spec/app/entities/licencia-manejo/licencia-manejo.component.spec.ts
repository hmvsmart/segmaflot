/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LicenciaManejoComponent from '@/entities/licencia-manejo/licencia-manejo.vue';
import LicenciaManejoClass from '@/entities/licencia-manejo/licencia-manejo.component';
import LicenciaManejoService from '@/entities/licencia-manejo/licencia-manejo.service';
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
  describe('LicenciaManejo Management Component', () => {
    let wrapper: Wrapper<LicenciaManejoClass>;
    let comp: LicenciaManejoClass;
    let licenciaManejoServiceStub: SinonStubbedInstance<LicenciaManejoService>;

    beforeEach(() => {
      licenciaManejoServiceStub = sinon.createStubInstance<LicenciaManejoService>(LicenciaManejoService);
      licenciaManejoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LicenciaManejoClass>(LicenciaManejoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          licenciaManejoService: () => licenciaManejoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      licenciaManejoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLicenciaManejos();
      await comp.$nextTick();

      // THEN
      expect(licenciaManejoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.licenciaManejos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      licenciaManejoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(licenciaManejoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLicenciaManejo();
      await comp.$nextTick();

      // THEN
      expect(licenciaManejoServiceStub.delete.called).toBeTruthy();
      expect(licenciaManejoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
