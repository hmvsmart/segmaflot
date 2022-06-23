/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ItinerarioUpdateComponent from '@/entities/itinerario/itinerario-update.vue';
import ItinerarioClass from '@/entities/itinerario/itinerario-update.component';
import ItinerarioService from '@/entities/itinerario/itinerario.service';

import ViajeService from '@/entities/viaje/viaje.service';
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
  describe('Itinerario Management Update Component', () => {
    let wrapper: Wrapper<ItinerarioClass>;
    let comp: ItinerarioClass;
    let itinerarioServiceStub: SinonStubbedInstance<ItinerarioService>;

    beforeEach(() => {
      itinerarioServiceStub = sinon.createStubInstance<ItinerarioService>(ItinerarioService);

      wrapper = shallowMount<ItinerarioClass>(ItinerarioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          itinerarioService: () => itinerarioServiceStub,
          alertService: () => new AlertService(),

          viajeService: () =>
            sinon.createStubInstance<ViajeService>(ViajeService, {
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
        comp.itinerario = entity;
        itinerarioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(itinerarioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.itinerario = entity;
        itinerarioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(itinerarioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundItinerario = { id: 123 };
        itinerarioServiceStub.find.resolves(foundItinerario);
        itinerarioServiceStub.retrieve.resolves([foundItinerario]);

        // WHEN
        comp.beforeRouteEnter({ params: { itinerarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.itinerario).toBe(foundItinerario);
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
