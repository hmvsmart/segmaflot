/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DireccionPersonaDetailComponent from '@/entities/direccion-persona/direccion-persona-details.vue';
import DireccionPersonaClass from '@/entities/direccion-persona/direccion-persona-details.component';
import DireccionPersonaService from '@/entities/direccion-persona/direccion-persona.service';
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
  describe('DireccionPersona Management Detail Component', () => {
    let wrapper: Wrapper<DireccionPersonaClass>;
    let comp: DireccionPersonaClass;
    let direccionPersonaServiceStub: SinonStubbedInstance<DireccionPersonaService>;

    beforeEach(() => {
      direccionPersonaServiceStub = sinon.createStubInstance<DireccionPersonaService>(DireccionPersonaService);

      wrapper = shallowMount<DireccionPersonaClass>(DireccionPersonaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { direccionPersonaService: () => direccionPersonaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDireccionPersona = { id: 123 };
        direccionPersonaServiceStub.find.resolves(foundDireccionPersona);

        // WHEN
        comp.retrieveDireccionPersona(123);
        await comp.$nextTick();

        // THEN
        expect(comp.direccionPersona).toBe(foundDireccionPersona);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDireccionPersona = { id: 123 };
        direccionPersonaServiceStub.find.resolves(foundDireccionPersona);

        // WHEN
        comp.beforeRouteEnter({ params: { direccionPersonaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.direccionPersona).toBe(foundDireccionPersona);
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
