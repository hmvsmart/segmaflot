/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ExperienciaHabilidadDetailComponent from '@/entities/experiencia-habilidad/experiencia-habilidad-details.vue';
import ExperienciaHabilidadClass from '@/entities/experiencia-habilidad/experiencia-habilidad-details.component';
import ExperienciaHabilidadService from '@/entities/experiencia-habilidad/experiencia-habilidad.service';
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
  describe('ExperienciaHabilidad Management Detail Component', () => {
    let wrapper: Wrapper<ExperienciaHabilidadClass>;
    let comp: ExperienciaHabilidadClass;
    let experienciaHabilidadServiceStub: SinonStubbedInstance<ExperienciaHabilidadService>;

    beforeEach(() => {
      experienciaHabilidadServiceStub = sinon.createStubInstance<ExperienciaHabilidadService>(ExperienciaHabilidadService);

      wrapper = shallowMount<ExperienciaHabilidadClass>(ExperienciaHabilidadDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { experienciaHabilidadService: () => experienciaHabilidadServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundExperienciaHabilidad = { id: 123 };
        experienciaHabilidadServiceStub.find.resolves(foundExperienciaHabilidad);

        // WHEN
        comp.retrieveExperienciaHabilidad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.experienciaHabilidad).toBe(foundExperienciaHabilidad);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExperienciaHabilidad = { id: 123 };
        experienciaHabilidadServiceStub.find.resolves(foundExperienciaHabilidad);

        // WHEN
        comp.beforeRouteEnter({ params: { experienciaHabilidadId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.experienciaHabilidad).toBe(foundExperienciaHabilidad);
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
