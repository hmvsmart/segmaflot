/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ReporteDetailComponent from '@/entities/reporte/reporte-details.vue';
import ReporteClass from '@/entities/reporte/reporte-details.component';
import ReporteService from '@/entities/reporte/reporte.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Reporte Management Detail Component', () => {
    let wrapper: Wrapper<ReporteClass>;
    let comp: ReporteClass;
    let reporteServiceStub: SinonStubbedInstance<ReporteService>;

    beforeEach(() => {
      reporteServiceStub = sinon.createStubInstance<ReporteService>(ReporteService);

      wrapper = shallowMount<ReporteClass>(ReporteDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { reporteService: () => reporteServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundReporte = { id: 123 };
        reporteServiceStub.find.resolves(foundReporte);

        // WHEN
        comp.retrieveReporte(123);
        await comp.$nextTick();

        // THEN
        expect(comp.reporte).toBe(foundReporte);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundReporte = { id: 123 };
        reporteServiceStub.find.resolves(foundReporte);

        // WHEN
        comp.beforeRouteEnter({ params: { reporteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.reporte).toBe(foundReporte);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
