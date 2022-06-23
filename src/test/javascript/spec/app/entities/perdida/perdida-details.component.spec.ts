/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PerdidaDetailComponent from '@/entities/perdida/perdida-details.vue';
import PerdidaClass from '@/entities/perdida/perdida-details.component';
import PerdidaService from '@/entities/perdida/perdida.service';
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
  describe('Perdida Management Detail Component', () => {
    let wrapper: Wrapper<PerdidaClass>;
    let comp: PerdidaClass;
    let perdidaServiceStub: SinonStubbedInstance<PerdidaService>;

    beforeEach(() => {
      perdidaServiceStub = sinon.createStubInstance<PerdidaService>(PerdidaService);

      wrapper = shallowMount<PerdidaClass>(PerdidaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { perdidaService: () => perdidaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPerdida = { id: 123 };
        perdidaServiceStub.find.resolves(foundPerdida);

        // WHEN
        comp.retrievePerdida(123);
        await comp.$nextTick();

        // THEN
        expect(comp.perdida).toBe(foundPerdida);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPerdida = { id: 123 };
        perdidaServiceStub.find.resolves(foundPerdida);

        // WHEN
        comp.beforeRouteEnter({ params: { perdidaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.perdida).toBe(foundPerdida);
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
