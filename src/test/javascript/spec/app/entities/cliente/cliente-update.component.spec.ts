/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ClienteUpdateComponent from '@/entities/cliente/cliente-update.vue';
import ClienteClass from '@/entities/cliente/cliente-update.component';
import ClienteService from '@/entities/cliente/cliente.service';

import VehiculoClienteService from '@/entities/vehiculo-cliente/vehiculo-cliente.service';

import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';

import PersonaService from '@/entities/persona/persona.service';
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
  describe('Cliente Management Update Component', () => {
    let wrapper: Wrapper<ClienteClass>;
    let comp: ClienteClass;
    let clienteServiceStub: SinonStubbedInstance<ClienteService>;

    beforeEach(() => {
      clienteServiceStub = sinon.createStubInstance<ClienteService>(ClienteService);

      wrapper = shallowMount<ClienteClass>(ClienteUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          clienteService: () => clienteServiceStub,
          alertService: () => new AlertService(),

          vehiculoClienteService: () =>
            sinon.createStubInstance<VehiculoClienteService>(VehiculoClienteService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          unidadServicioService: () =>
            sinon.createStubInstance<UnidadServicioService>(UnidadServicioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          personaService: () =>
            sinon.createStubInstance<PersonaService>(PersonaService, {
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
        comp.cliente = entity;
        clienteServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clienteServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cliente = entity;
        clienteServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(clienteServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCliente = { id: 123 };
        clienteServiceStub.find.resolves(foundCliente);
        clienteServiceStub.retrieve.resolves([foundCliente]);

        // WHEN
        comp.beforeRouteEnter({ params: { clienteId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cliente).toBe(foundCliente);
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
