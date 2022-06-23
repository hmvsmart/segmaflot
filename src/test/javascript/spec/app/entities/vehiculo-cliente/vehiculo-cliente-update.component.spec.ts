/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import VehiculoClienteUpdateComponent from '@/entities/vehiculo-cliente/vehiculo-cliente-update.vue';
import VehiculoClienteClass from '@/entities/vehiculo-cliente/vehiculo-cliente-update.component';
import VehiculoClienteService from '@/entities/vehiculo-cliente/vehiculo-cliente.service';

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
  describe('VehiculoCliente Management Update Component', () => {
    let wrapper: Wrapper<VehiculoClienteClass>;
    let comp: VehiculoClienteClass;
    let vehiculoClienteServiceStub: SinonStubbedInstance<VehiculoClienteService>;

    beforeEach(() => {
      vehiculoClienteServiceStub = sinon.createStubInstance<VehiculoClienteService>(VehiculoClienteService);

      wrapper = shallowMount<VehiculoClienteClass>(VehiculoClienteUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          vehiculoClienteService: () => vehiculoClienteServiceStub,
          alertService: () => new AlertService(),

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
        comp.vehiculoCliente = entity;
        vehiculoClienteServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vehiculoClienteServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.vehiculoCliente = entity;
        vehiculoClienteServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(vehiculoClienteServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVehiculoCliente = { id: 123 };
        vehiculoClienteServiceStub.find.resolves(foundVehiculoCliente);
        vehiculoClienteServiceStub.retrieve.resolves([foundVehiculoCliente]);

        // WHEN
        comp.beforeRouteEnter({ params: { vehiculoClienteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vehiculoCliente).toBe(foundVehiculoCliente);
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
