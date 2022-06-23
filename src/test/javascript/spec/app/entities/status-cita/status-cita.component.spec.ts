/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import StatusCitaComponent from '@/entities/status-cita/status-cita.vue';
import StatusCitaClass from '@/entities/status-cita/status-cita.component';
import StatusCitaService from '@/entities/status-cita/status-cita.service';
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
  describe('StatusCita Management Component', () => {
    let wrapper: Wrapper<StatusCitaClass>;
    let comp: StatusCitaClass;
    let statusCitaServiceStub: SinonStubbedInstance<StatusCitaService>;

    beforeEach(() => {
      statusCitaServiceStub = sinon.createStubInstance<StatusCitaService>(StatusCitaService);
      statusCitaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StatusCitaClass>(StatusCitaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          statusCitaService: () => statusCitaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      statusCitaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStatusCitas();
      await comp.$nextTick();

      // THEN
      expect(statusCitaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.statusCitas[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      statusCitaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(statusCitaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeStatusCita();
      await comp.$nextTick();

      // THEN
      expect(statusCitaServiceStub.delete.called).toBeTruthy();
      expect(statusCitaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
