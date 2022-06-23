/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PersonaUpdateComponent from '@/entities/persona/persona-update.vue';
import PersonaClass from '@/entities/persona/persona-update.component';
import PersonaService from '@/entities/persona/persona.service';

import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';

import DireccionPersonaService from '@/entities/direccion-persona/direccion-persona.service';

import EmpleadoService from '@/entities/empleado/empleado.service';

import ClienteService from '@/entities/cliente/cliente.service';

import ContactoService from '@/entities/contacto/contacto.service';
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
  describe('Persona Management Update Component', () => {
    let wrapper: Wrapper<PersonaClass>;
    let comp: PersonaClass;
    let personaServiceStub: SinonStubbedInstance<PersonaService>;

    beforeEach(() => {
      personaServiceStub = sinon.createStubInstance<PersonaService>(PersonaService);

      wrapper = shallowMount<PersonaClass>(PersonaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          personaService: () => personaServiceStub,
          alertService: () => new AlertService(),

          personaMoralService: () =>
            sinon.createStubInstance<PersonaMoralService>(PersonaMoralService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          direccionPersonaService: () =>
            sinon.createStubInstance<DireccionPersonaService>(DireccionPersonaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          empleadoService: () =>
            sinon.createStubInstance<EmpleadoService>(EmpleadoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          clienteService: () =>
            sinon.createStubInstance<ClienteService>(ClienteService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          contactoService: () =>
            sinon.createStubInstance<ContactoService>(ContactoService, {
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
        comp.persona = entity;
        personaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.persona = entity;
        personaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersona = { id: 123 };
        personaServiceStub.find.resolves(foundPersona);
        personaServiceStub.retrieve.resolves([foundPersona]);

        // WHEN
        comp.beforeRouteEnter({ params: { personaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.persona).toBe(foundPersona);
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
