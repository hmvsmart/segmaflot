/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import StatusCitaUpdateComponent from '@/entities/status-cita/status-cita-update.vue';
import StatusCitaClass from '@/entities/status-cita/status-cita-update.component';
import StatusCitaService from '@/entities/status-cita/status-cita.service';

import CitaService from '@/entities/cita/cita.service';
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
  describe('StatusCita Management Update Component', () => {
    let wrapper: Wrapper<StatusCitaClass>;
    let comp: StatusCitaClass;
    let statusCitaServiceStub: SinonStubbedInstance<StatusCitaService>;

    beforeEach(() => {
      statusCitaServiceStub = sinon.createStubInstance<StatusCitaService>(StatusCitaService);

      wrapper = shallowMount<StatusCitaClass>(StatusCitaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          statusCitaService: () => statusCitaServiceStub,
          alertService: () => new AlertService(),

          citaService: () =>
            sinon.createStubInstance<CitaService>(CitaService, {
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
        comp.statusCita = entity;
        statusCitaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(statusCitaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.statusCita = entity;
        statusCitaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(statusCitaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStatusCita = { id: 123 };
        statusCitaServiceStub.find.resolves(foundStatusCita);
        statusCitaServiceStub.retrieve.resolves([foundStatusCita]);

        // WHEN
        comp.beforeRouteEnter({ params: { statusCitaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.statusCita).toBe(foundStatusCita);
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
