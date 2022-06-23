/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import AditamentoUnidadUpdateComponent from '@/entities/aditamento-unidad/aditamento-unidad-update.vue';
import AditamentoUnidadClass from '@/entities/aditamento-unidad/aditamento-unidad-update.component';
import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';

import AditamentoService from '@/entities/aditamento/aditamento.service';

import UnidadService from '@/entities/unidad/unidad.service';
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
  describe('AditamentoUnidad Management Update Component', () => {
    let wrapper: Wrapper<AditamentoUnidadClass>;
    let comp: AditamentoUnidadClass;
    let aditamentoUnidadServiceStub: SinonStubbedInstance<AditamentoUnidadService>;

    beforeEach(() => {
      aditamentoUnidadServiceStub = sinon.createStubInstance<AditamentoUnidadService>(AditamentoUnidadService);

      wrapper = shallowMount<AditamentoUnidadClass>(AditamentoUnidadUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          aditamentoUnidadService: () => aditamentoUnidadServiceStub,
          alertService: () => new AlertService(),

          aditamentoService: () =>
            sinon.createStubInstance<AditamentoService>(AditamentoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          unidadService: () =>
            sinon.createStubInstance<UnidadService>(UnidadService, {
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
        comp.aditamentoUnidad = entity;
        aditamentoUnidadServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aditamentoUnidadServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aditamentoUnidad = entity;
        aditamentoUnidadServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aditamentoUnidadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAditamentoUnidad = { id: 123 };
        aditamentoUnidadServiceStub.find.resolves(foundAditamentoUnidad);
        aditamentoUnidadServiceStub.retrieve.resolves([foundAditamentoUnidad]);

        // WHEN
        comp.beforeRouteEnter({ params: { aditamentoUnidadId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.aditamentoUnidad).toBe(foundAditamentoUnidad);
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
