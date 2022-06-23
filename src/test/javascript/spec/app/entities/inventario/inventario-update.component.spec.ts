/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import InventarioUpdateComponent from '@/entities/inventario/inventario-update.vue';
import InventarioClass from '@/entities/inventario/inventario-update.component';
import InventarioService from '@/entities/inventario/inventario.service';

import UbicacionInventarioService from '@/entities/ubicacion-inventario/ubicacion-inventario.service';

import ResurtirService from '@/entities/resurtir/resurtir.service';

import PrecioVentaService from '@/entities/precio-venta/precio-venta.service';

import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';

import PerdidaService from '@/entities/perdida/perdida.service';

import ListaPedidoService from '@/entities/lista-pedido/lista-pedido.service';

import ProductoService from '@/entities/producto/producto.service';

import SucursalService from '@/entities/sucursal/sucursal.service';
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
  describe('Inventario Management Update Component', () => {
    let wrapper: Wrapper<InventarioClass>;
    let comp: InventarioClass;
    let inventarioServiceStub: SinonStubbedInstance<InventarioService>;

    beforeEach(() => {
      inventarioServiceStub = sinon.createStubInstance<InventarioService>(InventarioService);

      wrapper = shallowMount<InventarioClass>(InventarioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          inventarioService: () => inventarioServiceStub,
          alertService: () => new AlertService(),

          ubicacionInventarioService: () =>
            sinon.createStubInstance<UbicacionInventarioService>(UbicacionInventarioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          resurtirService: () =>
            sinon.createStubInstance<ResurtirService>(ResurtirService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          precioVentaService: () =>
            sinon.createStubInstance<PrecioVentaService>(PrecioVentaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          materialServicioService: () =>
            sinon.createStubInstance<MaterialServicioService>(MaterialServicioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          perdidaService: () =>
            sinon.createStubInstance<PerdidaService>(PerdidaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          listaPedidoService: () =>
            sinon.createStubInstance<ListaPedidoService>(ListaPedidoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          productoService: () =>
            sinon.createStubInstance<ProductoService>(ProductoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          sucursalService: () =>
            sinon.createStubInstance<SucursalService>(SucursalService, {
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
        comp.inventario = entity;
        inventarioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(inventarioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.inventario = entity;
        inventarioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(inventarioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundInventario = { id: 123 };
        inventarioServiceStub.find.resolves(foundInventario);
        inventarioServiceStub.retrieve.resolves([foundInventario]);

        // WHEN
        comp.beforeRouteEnter({ params: { inventarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.inventario).toBe(foundInventario);
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
