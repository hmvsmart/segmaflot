/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DiaHorarioDetailComponent from '@/entities/dia-horario/dia-horario-details.vue';
import DiaHorarioClass from '@/entities/dia-horario/dia-horario-details.component';
import DiaHorarioService from '@/entities/dia-horario/dia-horario.service';
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
  describe('DiaHorario Management Detail Component', () => {
    let wrapper: Wrapper<DiaHorarioClass>;
    let comp: DiaHorarioClass;
    let diaHorarioServiceStub: SinonStubbedInstance<DiaHorarioService>;

    beforeEach(() => {
      diaHorarioServiceStub = sinon.createStubInstance<DiaHorarioService>(DiaHorarioService);

      wrapper = shallowMount<DiaHorarioClass>(DiaHorarioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { diaHorarioService: () => diaHorarioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDiaHorario = { id: 123 };
        diaHorarioServiceStub.find.resolves(foundDiaHorario);

        // WHEN
        comp.retrieveDiaHorario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.diaHorario).toBe(foundDiaHorario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDiaHorario = { id: 123 };
        diaHorarioServiceStub.find.resolves(foundDiaHorario);

        // WHEN
        comp.beforeRouteEnter({ params: { diaHorarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.diaHorario).toBe(foundDiaHorario);
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
