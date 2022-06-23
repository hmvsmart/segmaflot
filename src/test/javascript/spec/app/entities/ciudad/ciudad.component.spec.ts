/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CiudadComponent from '@/entities/ciudad/ciudad.vue';
import CiudadClass from '@/entities/ciudad/ciudad.component';
import CiudadService from '@/entities/ciudad/ciudad.service';
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
  describe('Ciudad Management Component', () => {
    let wrapper: Wrapper<CiudadClass>;
    let comp: CiudadClass;
    let ciudadServiceStub: SinonStubbedInstance<CiudadService>;

    beforeEach(() => {
      ciudadServiceStub = sinon.createStubInstance<CiudadService>(CiudadService);
      ciudadServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CiudadClass>(CiudadComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ciudadService: () => ciudadServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ciudadServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCiudads();
      await comp.$nextTick();

      // THEN
      expect(ciudadServiceStub.retrieve.called).toBeTruthy();
      expect(comp.ciudads[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ciudadServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(ciudadServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCiudad();
      await comp.$nextTick();

      // THEN
      expect(ciudadServiceStub.delete.called).toBeTruthy();
      expect(ciudadServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
