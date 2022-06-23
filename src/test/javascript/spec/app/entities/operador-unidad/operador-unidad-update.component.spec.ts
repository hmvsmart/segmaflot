/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import OperadorUnidadUpdateComponent from '@/entities/operador-unidad/operador-unidad-update.vue';
import OperadorUnidadClass from '@/entities/operador-unidad/operador-unidad-update.component';
import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';

import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';

import EmpleadoService from '@/entities/empleado/empleado.service';
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
  describe('OperadorUnidad Management Update Component', () => {
    let wrapper: Wrapper<OperadorUnidadClass>;
    let comp: OperadorUnidadClass;
    let operadorUnidadServiceStub: SinonStubbedInstance<OperadorUnidadService>;

    beforeEach(() => {
      operadorUnidadServiceStub = sinon.createStubInstance<OperadorUnidadService>(OperadorUnidadService);

      wrapper = shallowMount<OperadorUnidadClass>(OperadorUnidadUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          operadorUnidadService: () => operadorUnidadServiceStub,
          alertService: () => new AlertService(),

          unidadViajeService: () =>
            sinon.createStubInstance<UnidadViajeService>(UnidadViajeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          empleadoService: () =>
            sinon.createStubInstance<EmpleadoService>(EmpleadoService, {
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
        comp.operadorUnidad = entity;
        operadorUnidadServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operadorUnidadServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.operadorUnidad = entity;
        operadorUnidadServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operadorUnidadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundOperadorUnidad = { id: 123 };
        operadorUnidadServiceStub.find.resolves(foundOperadorUnidad);
        operadorUnidadServiceStub.retrieve.resolves([foundOperadorUnidad]);

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
