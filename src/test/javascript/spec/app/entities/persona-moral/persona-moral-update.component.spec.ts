/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import PersonaMoralUpdateComponent from '@/entities/persona-moral/persona-moral-update.vue';
import PersonaMoralClass from '@/entities/persona-moral/persona-moral-update.component';
import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';

import SucursalService from '@/entities/sucursal/sucursal.service';

import ReporteService from '@/entities/reporte/reporte.service';

import PedidoService from '@/entities/pedido/pedido.service';

import PersonaService from '@/entities/persona/persona.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PersonaMoral Management Update Component', () => {
    let wrapper: Wrapper<PersonaMoralClass>;
    let comp: PersonaMoralClass;
    let personaMoralServiceStub: SinonStubbedInstance<PersonaMoralService>;

    beforeEach(() => {
      personaMoralServiceStub = sinon.createStubInstance<PersonaMoralService>(PersonaMoralService);

      wrapper = shallowMount<PersonaMoralClass>(PersonaMoralUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          personaMoralService: () => personaMoralServiceStub,
          alertService: () => new AlertService(),

          sucursalService: () =>
            sinon.createStubInstance<SucursalService>(SucursalService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          reporteService: () =>
            sinon.createStubInstance<ReporteService>(ReporteService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          pedidoService: () =>
            sinon.createStubInstance<PedidoService>(PedidoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          personaService: () =>
            sinon.createStubInstance<PersonaService>(PersonaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.personaMoral = entity;
        personaMoralServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personaMoralServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.personaMoral = entity;
        personaMoralServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personaMoralServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersonaMoral = { id: 123 };
        personaMoralServiceStub.find.resolves(foundPersonaMoral);
        personaMoralServiceStub.retrieve.resolves([foundPersonaMoral]);

        // WHEN
        comp.beforeRouteEnter({ params: { personaMoralId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.personaMoral).toBe(foundPersonaMoral);
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
