/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import TipoContactoUpdateComponent from '@/entities/tipo-contacto/tipo-contacto-update.vue';
import TipoContactoClass from '@/entities/tipo-contacto/tipo-contacto-update.component';
import TipoContactoService from '@/entities/tipo-contacto/tipo-contacto.service';

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
  describe('TipoContacto Management Update Component', () => {
    let wrapper: Wrapper<TipoContactoClass>;
    let comp: TipoContactoClass;
    let tipoContactoServiceStub: SinonStubbedInstance<TipoContactoService>;

    beforeEach(() => {
      tipoContactoServiceStub = sinon.createStubInstance<TipoContactoService>(TipoContactoService);

      wrapper = shallowMount<TipoContactoClass>(TipoContactoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          tipoContactoService: () => tipoContactoServiceStub,
          alertService: () => new AlertService(),

          contactoService: () =>
            sinon.createStubInstance<ContactoService>(ContactoService, {
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
        comp.tipoContacto = entity;
        tipoContactoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoContactoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tipoContacto = entity;
        tipoContactoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoContactoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoContacto = { id: 123 };
        tipoContactoServiceStub.find.resolves(foundTipoContacto);
        tipoContactoServiceStub.retrieve.resolves([foundTipoContacto]);

        // WHEN
        comp.beforeRouteEnter({ params: { tipoContactoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tipoContacto).toBe(foundTipoContacto);
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
