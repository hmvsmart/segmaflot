/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import AditamentoUpdateComponent from '@/entities/aditamento/aditamento-update.vue';
import AditamentoClass from '@/entities/aditamento/aditamento-update.component';
import AditamentoService from '@/entities/aditamento/aditamento.service';

import AditamentoUnidadService from '@/entities/aditamento-unidad/aditamento-unidad.service';
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
  describe('Aditamento Management Update Component', () => {
    let wrapper: Wrapper<AditamentoClass>;
    let comp: AditamentoClass;
    let aditamentoServiceStub: SinonStubbedInstance<AditamentoService>;

    beforeEach(() => {
      aditamentoServiceStub = sinon.createStubInstance<AditamentoService>(AditamentoService);

      wrapper = shallowMount<AditamentoClass>(AditamentoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          aditamentoService: () => aditamentoServiceStub,
          alertService: () => new AlertService(),

          aditamentoUnidadService: () =>
            sinon.createStubInstance<AditamentoUnidadService>(AditamentoUnidadService, {
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
        comp.aditamento = entity;
        aditamentoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aditamentoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.aditamento = entity;
        aditamentoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(aditamentoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAditamento = { id: 123 };
        aditamentoServiceStub.find.resolves(foundAditamento);
        aditamentoServiceStub.retrieve.resolves([foundAditamento]);

        // WHEN
        comp.beforeRouteEnter({ params: { aditamentoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.aditamento).toBe(foundAditamento);
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
