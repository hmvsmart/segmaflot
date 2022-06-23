/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import UnidadUpdateComponent from '@/entities/unidad/unidad-update.vue';
import UnidadClass from '@/entities/unidad/unidad-update.component';
import UnidadService from '@/entities/unidad/unidad.service';

import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';

import PolizaService from '@/entities/poliza/poliza.service';

import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';

import VehiculoService from '@/entities/vehiculo/vehiculo.service';
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
  describe('Unidad Management Update Component', () => {
    let wrapper: Wrapper<UnidadClass>;
    let comp: UnidadClass;
    let unidadServiceStub: SinonStubbedInstance<UnidadService>;

    beforeEach(() => {
      unidadServiceStub = sinon.createStubInstance<UnidadService>(UnidadService);

      wrapper = shallowMount<UnidadClass>(UnidadUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          unidadService: () => unidadServiceStub,
          alertService: () => new AlertService(),

          aditamentoUnidadService: () =>
            sinon.createStubInstance<AditamentoUnidadService>(AditamentoUnidadService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          polizaService: () =>
            sinon.createStubInstance<PolizaService>(PolizaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          unidadViajeService: () =>
            sinon.createStubInstance<UnidadViajeService>(UnidadViajeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          vehiculoService: () =>
            sinon.createStubInstance<VehiculoService>(VehiculoService, {
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
        comp.unidad = entity;
        unidadServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.unidad = entity;
        unidadServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidad = { id: 123 };
        unidadServiceStub.find.resolves(foundUnidad);
        unidadServiceStub.retrieve.resolves([foundUnidad]);

        // WHEN
        comp.beforeRouteEnter({ params: { unidadId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.unidad).toBe(foundUnidad);
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
