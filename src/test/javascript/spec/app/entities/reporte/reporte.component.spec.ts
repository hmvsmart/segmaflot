/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ReporteComponent from '@/entities/reporte/reporte.vue';
import ReporteClass from '@/entities/reporte/reporte.component';
import ReporteService from '@/entities/reporte/reporte.service';
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
  describe('Reporte Management Component', () => {
    let wrapper: Wrapper<ReporteClass>;
    let comp: ReporteClass;
    let reporteServiceStub: SinonStubbedInstance<ReporteService>;

    beforeEach(() => {
      reporteServiceStub = sinon.createStubInstance<ReporteService>(ReporteService);
      reporteServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ReporteClass>(ReporteComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          reporteService: () => reporteServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      reporteServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllReportes();
      await comp.$nextTick();

      // THEN
      expect(reporteServiceStub.retrieve.called).toBeTruthy();
      expect(comp.reportes[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      reporteServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(reporteServiceStub.retrieve.callCount).toEqual(1);

      comp.removeReporte();
      await comp.$nextTick();

      // THEN
      expect(reporteServiceStub.delete.called).toBeTruthy();
      expect(reporteServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
