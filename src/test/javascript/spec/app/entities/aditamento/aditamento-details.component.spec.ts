/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AditamentoDetailComponent from '@/entities/aditamento/aditamento-details.vue';
import AditamentoClass from '@/entities/aditamento/aditamento-details.component';
import AditamentoService from '@/entities/aditamento/aditamento.service';
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
  describe('Aditamento Management Detail Component', () => {
    let wrapper: Wrapper<AditamentoClass>;
    let comp: AditamentoClass;
    let aditamentoServiceStub: SinonStubbedInstance<AditamentoService>;

    beforeEach(() => {
      aditamentoServiceStub = sinon.createStubInstance<AditamentoService>(AditamentoService);

      wrapper = shallowMount<AditamentoClass>(AditamentoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { aditamentoService: () => aditamentoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAditamento = { id: 123 };
        aditamentoServiceStub.find.resolves(foundAditamento);

        // WHEN
        comp.retrieveAditamento(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aditamento).toBe(foundAditamento);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAditamento = { id: 123 };
        aditamentoServiceStub.find.resolves(foundAditamento);

        // WHEN
        comp.beforeRouteEnter({ params: { aditamentoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.aditamento).toBe(foundAditamento);
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
