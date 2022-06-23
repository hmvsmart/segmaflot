/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EstanteDetailComponent from '@/entities/estante/estante-details.vue';
import EstanteClass from '@/entities/estante/estante-details.component';
import EstanteService from '@/entities/estante/estante.service';
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
  describe('Estante Management Detail Component', () => {
    let wrapper: Wrapper<EstanteClass>;
    let comp: EstanteClass;
    let estanteServiceStub: SinonStubbedInstance<EstanteService>;

    beforeEach(() => {
      estanteServiceStub = sinon.createStubInstance<EstanteService>(EstanteService);

      wrapper = shallowMount<EstanteClass>(EstanteDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { estanteService: () => estanteServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEstante = { id: 123 };
        estanteServiceStub.find.resolves(foundEstante);

        // WHEN
        comp.retrieveEstante(123);
        await comp.$nextTick();

        // THEN
        expect(comp.estante).toBe(foundEstante);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEstante = { id: 123 };
        estanteServiceStub.find.resolves(foundEstante);

        // WHEN
        comp.beforeRouteEnter({ params: { estanteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.estante).toBe(foundEstante);
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
