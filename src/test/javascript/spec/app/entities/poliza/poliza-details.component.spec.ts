/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PolizaDetailComponent from '@/entities/poliza/poliza-details.vue';
import PolizaClass from '@/entities/poliza/poliza-details.component';
import PolizaService from '@/entities/poliza/poliza.service';
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
  describe('Poliza Management Detail Component', () => {
    let wrapper: Wrapper<PolizaClass>;
    let comp: PolizaClass;
    let polizaServiceStub: SinonStubbedInstance<PolizaService>;

    beforeEach(() => {
      polizaServiceStub = sinon.createStubInstance<PolizaService>(PolizaService);

      wrapper = shallowMount<PolizaClass>(PolizaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { polizaService: () => polizaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPoliza = { id: 123 };
        polizaServiceStub.find.resolves(foundPoliza);

        // WHEN
        comp.retrievePoliza(123);
        await comp.$nextTick();

        // THEN
        expect(comp.poliza).toBe(foundPoliza);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPoliza = { id: 123 };
        polizaServiceStub.find.resolves(foundPoliza);

        // WHEN
        comp.beforeRouteEnter({ params: { polizaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.poliza).toBe(foundPoliza);
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
