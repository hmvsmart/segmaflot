/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import MaterialServicioUpdateComponent from '@/entities/material-servicio/material-servicio-update.vue';
import MaterialServicioClass from '@/entities/material-servicio/material-servicio-update.component';
import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';

import InventarioService from '@/entities/inventario/inventario.service';

import ServicioService from '@/entities/servicio/servicio.service';
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
  describe('MaterialServicio Management Update Component', () => {
    let wrapper: Wrapper<MaterialServicioClass>;
    let comp: MaterialServicioClass;
    let materialServicioServiceStub: SinonStubbedInstance<MaterialServicioService>;

    beforeEach(() => {
      materialServicioServiceStub = sinon.createStubInstance<MaterialServicioService>(MaterialServicioService);

      wrapper = shallowMount<MaterialServicioClass>(MaterialServicioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          materialServicioService: () => materialServicioServiceStub,
          alertService: () => new AlertService(),

          inventarioService: () =>
            sinon.createStubInstance<InventarioService>(InventarioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          servicioService: () =>
            sinon.createStubInstance<ServicioService>(ServicioService, {
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
        comp.materialServicio = entity;
        materialServicioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(materialServicioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.materialServicio = entity;
        materialServicioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(materialServicioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMaterialServicio = { id: 123 };
        materialServicioServiceStub.find.resolves(foundMaterialServicio);
        materialServicioServiceStub.retrieve.resolves([foundMaterialServicio]);

        // WHEN
        comp.beforeRouteEnter({ params: { materialServicioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.materialServicio).toBe(foundMaterialServicio);
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
