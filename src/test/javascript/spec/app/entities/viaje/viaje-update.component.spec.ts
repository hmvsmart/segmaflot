/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ViajeUpdateComponent from '@/entities/viaje/viaje-update.vue';
import ViajeClass from '@/entities/viaje/viaje-update.component';
import ViajeService from '@/entities/viaje/viaje.service';

import ItinerarioService from '@/entities/itinerario/itinerario.service';

import UnidadViajeService from '@/entities/unidad-viaje/unidad-viaje.service';

import DireccionService from '@/entities/direccion/direccion.service';
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
  describe('Viaje Management Update Component', () => {
    let wrapper: Wrapper<ViajeClass>;
    let comp: ViajeClass;
    let viajeServiceStub: SinonStubbedInstance<ViajeService>;

    beforeEach(() => {
      viajeServiceStub = sinon.createStubInstance<ViajeService>(ViajeService);

      wrapper = shallowMount<ViajeClass>(ViajeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          viajeService: () => viajeServiceStub,
          alertService: () => new AlertService(),

          itinerarioService: () =>
            sinon.createStubInstance<ItinerarioService>(ItinerarioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          unidadViajeService: () =>
            sinon.createStubInstance<UnidadViajeService>(UnidadViajeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          direccionService: () =>
            sinon.createStubInstance<DireccionService>(DireccionService, {
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
        comp.viaje = entity;
        viajeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(viajeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.viaje = entity;
        viajeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(viajeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundViaje = { id: 123 };
        viajeServiceStub.find.resolves(foundViaje);
        viajeServiceStub.retrieve.resolves([foundViaje]);

        // WHEN
        comp.beforeRouteEnter({ params: { viajeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.viaje).toBe(foundViaje);
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
