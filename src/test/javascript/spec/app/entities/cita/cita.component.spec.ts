/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CitaComponent from '@/entities/cita/cita.vue';
import CitaClass from '@/entities/cita/cita.component';
import CitaService from '@/entities/cita/cita.service';
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
  describe('Cita Management Component', () => {
    let wrapper: Wrapper<CitaClass>;
    let comp: CitaClass;
    let citaServiceStub: SinonStubbedInstance<CitaService>;

    beforeEach(() => {
      citaServiceStub = sinon.createStubInstance<CitaService>(CitaService);
      citaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CitaClass>(CitaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          citaService: () => citaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      citaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCitas();
      await comp.$nextTick();

      // THEN
      expect(citaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.citas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      citaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(citaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCita();
      await comp.$nextTick();

      // THEN
      expect(citaServiceStub.delete.called).toBeTruthy();
      expect(citaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
