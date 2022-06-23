/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import HorarioDetailComponent from '@/entities/horario/horario-details.vue';
import HorarioClass from '@/entities/horario/horario-details.component';
import HorarioService from '@/entities/horario/horario.service';
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
  describe('Horario Management Detail Component', () => {
    let wrapper: Wrapper<HorarioClass>;
    let comp: HorarioClass;
    let horarioServiceStub: SinonStubbedInstance<HorarioService>;

    beforeEach(() => {
      horarioServiceStub = sinon.createStubInstance<HorarioService>(HorarioService);

      wrapper = shallowMount<HorarioClass>(HorarioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { horarioService: () => horarioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHorario = { id: 123 };
        horarioServiceStub.find.resolves(foundHorario);

        // WHEN
        comp.retrieveHorario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.horario).toBe(foundHorario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHorario = { id: 123 };
        horarioServiceStub.find.resolves(foundHorario);

        // WHEN
        comp.beforeRouteEnter({ params: { horarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.horario).toBe(foundHorario);
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
