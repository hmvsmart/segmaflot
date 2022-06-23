/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import UnidadViajeUpdateComponent from '@/entities/unidad-viaje/unidad-viaje-update.vue';
import UnidadViajeClass from '@/entities/unidad-viaje/unidad-viaje-update.component';
import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';

import OperadorUnidadService from '@/entities/operador-unidad/operador-unidad.service';

import ViajeService from '@/entities/viaje/viaje.service';

import UnidadService from '@/entities/unidad/unidad.service';
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
  describe('UnidadViaje Management Update Component', () => {
    let wrapper: Wrapper<UnidadViajeClass>;
    let comp: UnidadViajeClass;
    let unidadViajeServiceStub: SinonStubbedInstance<UnidadViajeService>;

    beforeEach(() => {
      unidadViajeServiceStub = sinon.createStubInstance<UnidadViajeService>(UnidadViajeService);

      wrapper = shallowMount<UnidadViajeClass>(UnidadViajeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          unidadViajeService: () => unidadViajeServiceStub,
          alertService: () => new AlertService(),

          operadorUnidadService: () =>
            sinon.createStubInstance<OperadorUnidadService>(OperadorUnidadService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          viajeService: () =>
            sinon.createStubInstance<ViajeService>(ViajeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          unidadService: () =>
            sinon.createStubInstance<UnidadService>(UnidadService, {
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
        comp.unidadViaje = entity;
        unidadViajeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadViajeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.unidadViaje = entity;
        unidadViajeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadViajeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidadViaje = { id: 123 };
        unidadViajeServiceStub.find.resolves(foundUnidadViaje);
        unidadViajeServiceStub.retrieve.resolves([foundUnidadViaje]);

        // WHEN
        comp.beforeRouteEnter({ params: { unidadViajeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.unidadViaje).toBe(foundUnidadViaje);
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
