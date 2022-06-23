/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DireccionDetailComponent from '@/entities/direccion/direccion-details.vue';
import DireccionClass from '@/entities/direccion/direccion-details.component';
import DireccionService from '@/entities/direccion/direccion.service';
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
  describe('Direccion Management Detail Component', () => {
    let wrapper: Wrapper<DireccionClass>;
    let comp: DireccionClass;
    let direccionServiceStub: SinonStubbedInstance<DireccionService>;

    beforeEach(() => {
      direccionServiceStub = sinon.createStubInstance<DireccionService>(DireccionService);

      wrapper = shallowMount<DireccionClass>(DireccionDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { direccionService: () => direccionServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDireccion = { id: 123 };
        direccionServiceStub.find.resolves(foundDireccion);

        // WHEN
        comp.retrieveDireccion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.direccion).toBe(foundDireccion);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDireccion = { id: 123 };
        direccionServiceStub.find.resolves(foundDireccion);

        // WHEN
        comp.beforeRouteEnter({ params: { direccionId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.direccion).toBe(foundDireccion);
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
