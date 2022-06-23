/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ContactoUpdateComponent from '@/entities/contacto/contacto-update.vue';
import ContactoClass from '@/entities/contacto/contacto-update.component';
import ContactoService from '@/entities/contacto/contacto.service';

import TipoContactoService from '@/entities/tipo-contacto/tipo-contacto.service';

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
  describe('Contacto Management Update Component', () => {
    let wrapper: Wrapper<ContactoClass>;
    let comp: ContactoClass;
    let contactoServiceStub: SinonStubbedInstance<ContactoService>;

    beforeEach(() => {
      contactoServiceStub = sinon.createStubInstance<ContactoService>(ContactoService);

      wrapper = shallowMount<ContactoClass>(ContactoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          contactoService: () => contactoServiceStub,
          alertService: () => new AlertService(),

          tipoContactoService: () =>
            sinon.createStubInstance<TipoContactoService>(TipoContactoService, {
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
        comp.contacto = entity;
        contactoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(contactoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.contacto = entity;
        contactoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(contactoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundContacto = { id: 123 };
        contactoServiceStub.find.resolves(foundContacto);
        contactoServiceStub.retrieve.resolves([foundContacto]);

        // WHEN
        comp.beforeRouteEnter({ params: { contactoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.contacto).toBe(foundContacto);
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
