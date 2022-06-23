/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import RenglonVentaUpdateComponent from '@/entities/renglon-venta/renglon-venta-update.vue';
import RenglonVentaClass from '@/entities/renglon-venta/renglon-venta-update.component';
import RenglonVentaService from '@/entities/renglon-venta/renglon-venta.service';

import VentaService from '@/entities/venta/venta.service';

import PrecioVentaService from '@/entities/precio-venta/precio-venta.service';
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
  describe('RenglonVenta Management Update Component', () => {
    let wrapper: Wrapper<RenglonVentaClass>;
    let comp: RenglonVentaClass;
    let renglonVentaServiceStub: SinonStubbedInstance<RenglonVentaService>;

    beforeEach(() => {
      renglonVentaServiceStub = sinon.createStubInstance<RenglonVentaService>(RenglonVentaService);

      wrapper = shallowMount<RenglonVentaClass>(RenglonVentaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          renglonVentaService: () => renglonVentaServiceStub,
          alertService: () => new AlertService(),

          ventaService: () =>
            sinon.createStubInstance<VentaService>(VentaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          precioVentaService: () =>
            sinon.createStubInstance<PrecioVentaService>(PrecioVentaService, {
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
        comp.renglonVenta = entity;
        renglonVentaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(renglonVentaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.renglonVenta = entity;
        renglonVentaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(renglonVentaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRenglonVenta = { id: 123 };
        renglonVentaServiceStub.find.resolves(foundRenglonVenta);
        renglonVentaServiceStub.retrieve.resolves([foundRenglonVenta]);

        // WHEN
        comp.beforeRouteEnter({ params: { renglonVentaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.renglonVenta).toBe(foundRenglonVenta);
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
