/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import StatusCitaDetailComponent from '@/entities/status-cita/status-cita-details.vue';
import StatusCitaClass from '@/entities/status-cita/status-cita-details.component';
import StatusCitaService from '@/entities/status-cita/status-cita.service';
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
  describe('StatusCita Management Detail Component', () => {
    let wrapper: Wrapper<StatusCitaClass>;
    let comp: StatusCitaClass;
    let statusCitaServiceStub: SinonStubbedInstance<StatusCitaService>;

    beforeEach(() => {
      statusCitaServiceStub = sinon.createStubInstance<StatusCitaService>(StatusCitaService);

      wrapper = shallowMount<StatusCitaClass>(StatusCitaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { statusCitaService: () => statusCitaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundStatusCita = { id: 123 };
        statusCitaServiceStub.find.resolves(foundStatusCita);

        // WHEN
        comp.retrieveStatusCita(123);
        await comp.$nextTick();

        // THEN
        expect(comp.statusCita).toBe(foundStatusCita);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStatusCita = { id: 123 };
        statusCitaServiceStub.find.resolves(foundStatusCita);

        // WHEN
        comp.beforeRouteEnter({ params: { statusCitaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.statusCita).toBe(foundStatusCita);
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
