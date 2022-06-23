/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VentaPedidoUpdateComponent from '@/entities/venta-pedido/venta-pedido-update.vue';
import VentaPedidoClass from '@/entities/venta-pedido/venta-pedido-update.component';
import VentaPedidoService from '@/entities/venta-pedido/venta-pedido.service';

import VentaService from '@/entities/venta/venta.service';

import PedidoService from '@/entities/pedido/pedido.service';
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
  describe('VentaPedido Management Update Component', () => {
    let wrapper: Wrapper<VentaPedidoClass>;
    let comp: VentaPedidoClass;
    let ventaPedidoServiceStub: SinonStubbedInstance<VentaPedidoService>;

    beforeEach(() => {
      ventaPedidoServiceStub = sinon.createStubInstance<VentaPedidoService>(VentaPedidoService);

      wrapper = shallowMount<VentaPedidoClass>(VentaPedidoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          ventaPedidoService: () => ventaPedidoServiceStub,
          alertService: () => new AlertService(),

          ventaService: () =>
            sinon.createStubInstance<VentaService>(VentaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          pedidoService: () =>
            sinon.createStubInstance<PedidoService>(PedidoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.ventaPedido = entity;
        ventaPedidoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ventaPedidoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.ventaPedido = entity;
        ventaPedidoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(ventaPedidoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVentaPedido = { id: 123 };
        ventaPedidoServiceStub.find.resolves(foundVentaPedido);
        ventaPedidoServiceStub.retrieve.resolves([foundVentaPedido]);

        // WHEN
        comp.beforeRouteEnter({ params: { ventaPedidoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.ventaPedido).toBe(foundVentaPedido);
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
