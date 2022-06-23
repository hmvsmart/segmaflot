/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import SucursalUpdateComponent from '@/entities/sucursal/sucursal-update.vue';
import SucursalClass from '@/entities/sucursal/sucursal-update.component';
import SucursalService from '@/entities/sucursal/sucursal.service';

import InventarioService from '@/entities/inventario/inventario.service';

import EmpleadoService from '@/entities/empleado/empleado.service';

import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
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
  describe('Sucursal Management Update Component', () => {
    let wrapper: Wrapper<SucursalClass>;
    let comp: SucursalClass;
    let sucursalServiceStub: SinonStubbedInstance<SucursalService>;

    beforeEach(() => {
      sucursalServiceStub = sinon.createStubInstance<SucursalService>(SucursalService);

      wrapper = shallowMount<SucursalClass>(SucursalUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          sucursalService: () => sucursalServiceStub,
          alertService: () => new AlertService(),

          inventarioService: () =>
            sinon.createStubInstance<InventarioService>(InventarioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          empleadoService: () =>
            sinon.createStubInstance<EmpleadoService>(EmpleadoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          personaMoralService: () =>
            sinon.createStubInstance<PersonaMoralService>(PersonaMoralService, {
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
        comp.sucursal = entity;
        sucursalServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sucursalServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.sucursal = entity;
        sucursalServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sucursalServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSucursal = { id: 123 };
        sucursalServiceStub.find.resolves(foundSucursal);
        sucursalServiceStub.retrieve.resolves([foundSucursal]);

        // WHEN
        comp.beforeRouteEnter({ params: { sucursalId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.sucursal).toBe(foundSucursal);
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
