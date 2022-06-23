/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ServicioUpdateComponent from '@/entities/servicio/servicio-update.vue';
import ServicioClass from '@/entities/servicio/servicio-update.component';
import ServicioService from '@/entities/servicio/servicio.service';

import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';

import PrecioServicioService from '@/entities/precio-servicio/precio-servicio.service';

import ListaServicioService from '@/entities/lista-servicio/lista-servicio.service';
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
  describe('Servicio Management Update Component', () => {
    let wrapper: Wrapper<ServicioClass>;
    let comp: ServicioClass;
    let servicioServiceStub: SinonStubbedInstance<ServicioService>;

    beforeEach(() => {
      servicioServiceStub = sinon.createStubInstance<ServicioService>(ServicioService);

      wrapper = shallowMount<ServicioClass>(ServicioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          servicioService: () => servicioServiceStub,
          alertService: () => new AlertService(),

          materialServicioService: () =>
            sinon.createStubInstance<MaterialServicioService>(MaterialServicioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          precioServicioService: () =>
            sinon.createStubInstance<PrecioServicioService>(PrecioServicioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          listaServicioService: () =>
            sinon.createStubInstance<ListaServicioService>(ListaServicioService, {
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
        comp.servicio = entity;
        servicioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(servicioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.servicio = entity;
        servicioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(servicioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundServicio = { id: 123 };
        servicioServiceStub.find.resolves(foundServicio);
        servicioServiceStub.retrieve.resolves([foundServicio]);

        // WHEN
        comp.beforeRouteEnter({ params: { servicioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.servicio).toBe(foundServicio);
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
