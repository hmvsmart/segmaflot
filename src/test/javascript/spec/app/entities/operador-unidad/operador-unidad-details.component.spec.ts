/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import OperadorUnidadDetailComponent from '@/entities/operador-unidad/operador-unidad-details.vue';
import OperadorUnidadClass from '@/entities/operador-unidad/operador-unidad-details.component';
import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';
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
  describe('OperadorUnidad Management Detail Component', () => {
    let wrapper: Wrapper<OperadorUnidadClass>;
    let comp: OperadorUnidadClass;
    let operadorUnidadServiceStub: SinonStubbedInstance<OperadorUnidadService>;

    beforeEach(() => {
      operadorUnidadServiceStub = sinon.createStubInstance<OperadorUnidadService>(OperadorUnidadService);

      wrapper = shallowMount<OperadorUnidadClass>(OperadorUnidadDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { operadorUnidadService: () => operadorUnidadServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundOperadorUnidad = { id: 123 };
        operadorUnidadServiceStub.find.resolves(foundOperadorUnidad);

        // WHEN
        comp.retrieveOperadorUnidad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.operadorUnidad).toBe(foundOperadorUnidad);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOperadorUnidad = { id: 123 };
        operadorUnidadServiceStub.find.resolves(foundOperadorUnidad);

        // WHEN
        comp.beforeRouteEnter({ params: { operadorUnidadId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.operadorUnidad).toBe(foundOperadorUnidad);
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
