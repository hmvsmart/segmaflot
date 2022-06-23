/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import UnidadServicioUpdateComponent from '@/entities/unidad-servicio/unidad-servicio-update.vue';
import UnidadServicioClass from '@/entities/unidad-servicio/unidad-servicio-update.component';
import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';

import ListaServicioService from '@/entities/lista-servicio/lista-servicio.service';

import ClienteService from '@/entities/cliente/cliente.service';
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
  describe('UnidadServicio Management Update Component', () => {
    let wrapper: Wrapper<UnidadServicioClass>;
    let comp: UnidadServicioClass;
    let unidadServicioServiceStub: SinonStubbedInstance<UnidadServicioService>;

    beforeEach(() => {
      unidadServicioServiceStub = sinon.createStubInstance<UnidadServicioService>(UnidadServicioService);

      wrapper = shallowMount<UnidadServicioClass>(UnidadServicioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          unidadServicioService: () => unidadServicioServiceStub,
          alertService: () => new AlertService(),

          listaServicioService: () =>
            sinon.createStubInstance<ListaServicioService>(ListaServicioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          clienteService: () =>
            sinon.createStubInstance<ClienteService>(ClienteService, {
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
        comp.unidadServicio = entity;
        unidadServicioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServicioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.unidadServicio = entity;
        unidadServicioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServicioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidadServicio = { id: 123 };
        unidadServicioServiceStub.find.resolves(foundUnidadServicio);
        unidadServicioServiceStub.retrieve.resolves([foundUnidadServicio]);

        // WHEN
        comp.beforeRouteEnter({ params: { unidadServicioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.unidadServicio).toBe(foundUnidadServicio);
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
