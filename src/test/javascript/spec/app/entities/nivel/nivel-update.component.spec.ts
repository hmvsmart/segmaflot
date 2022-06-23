/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import NivelUpdateComponent from '@/entities/nivel/nivel-update.vue';
import NivelClass from '@/entities/nivel/nivel-update.component';
import NivelService from '@/entities/nivel/nivel.service';

import SeccionService from '@/entities/seccion/seccion.service';

import EstanteService from '@/entities/estante/estante.service';
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
  describe('Nivel Management Update Component', () => {
    let wrapper: Wrapper<NivelClass>;
    let comp: NivelClass;
    let nivelServiceStub: SinonStubbedInstance<NivelService>;

    beforeEach(() => {
      nivelServiceStub = sinon.createStubInstance<NivelService>(NivelService);

      wrapper = shallowMount<NivelClass>(NivelUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          nivelService: () => nivelServiceStub,
          alertService: () => new AlertService(),

          seccionService: () =>
            sinon.createStubInstance<SeccionService>(SeccionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          estanteService: () =>
            sinon.createStubInstance<EstanteService>(EstanteService, {
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
        comp.nivel = entity;
        nivelServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nivelServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.nivel = entity;
        nivelServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nivelServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNivel = { id: 123 };
        nivelServiceStub.find.resolves(foundNivel);
        nivelServiceStub.retrieve.resolves([foundNivel]);

        // WHEN
        comp.beforeRouteEnter({ params: { nivelId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.nivel).toBe(foundNivel);
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
