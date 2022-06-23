/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AditamentoUnidadDetailComponent from '@/entities/aditamento-unidad/aditamento-unidad-details.vue';
import AditamentoUnidadClass from '@/entities/aditamento-unidad/aditamento-unidad-details.component';
import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';
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
  describe('AditamentoUnidad Management Detail Component', () => {
    let wrapper: Wrapper<AditamentoUnidadClass>;
    let comp: AditamentoUnidadClass;
    let aditamentoUnidadServiceStub: SinonStubbedInstance<AditamentoUnidadService>;

    beforeEach(() => {
      aditamentoUnidadServiceStub = sinon.createStubInstance<AditamentoUnidadService>(AditamentoUnidadService);

      wrapper = shallowMount<AditamentoUnidadClass>(AditamentoUnidadDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { aditamentoUnidadService: () => aditamentoUnidadServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAditamentoUnidad = { id: 123 };
        aditamentoUnidadServiceStub.find.resolves(foundAditamentoUnidad);

        // WHEN
        comp.retrieveAditamentoUnidad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.aditamentoUnidad).toBe(foundAditamentoUnidad);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAditamentoUnidad = { id: 123 };
        aditamentoUnidadServiceStub.find.resolves(foundAditamentoUnidad);

        // WHEN
        comp.beforeRouteEnter({ params: { aditamentoUnidadId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.aditamentoUnidad).toBe(foundAditamentoUnidad);
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
