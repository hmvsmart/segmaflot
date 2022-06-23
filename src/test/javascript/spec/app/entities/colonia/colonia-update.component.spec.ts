/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ColoniaUpdateComponent from '@/entities/colonia/colonia-update.vue';
import ColoniaClass from '@/entities/colonia/colonia-update.component';
import ColoniaService from '@/entities/colonia/colonia.service';

import DireccionService from '@/entities/direccion/direccion.service';

import CPService from '@/entities/cp/cp.service';
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
  describe('Colonia Management Update Component', () => {
    let wrapper: Wrapper<ColoniaClass>;
    let comp: ColoniaClass;
    let coloniaServiceStub: SinonStubbedInstance<ColoniaService>;

    beforeEach(() => {
      coloniaServiceStub = sinon.createStubInstance<ColoniaService>(ColoniaService);

      wrapper = shallowMount<ColoniaClass>(ColoniaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          coloniaService: () => coloniaServiceStub,
          alertService: () => new AlertService(),

          direccionService: () =>
            sinon.createStubInstance<DireccionService>(DireccionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          cPService: () =>
            sinon.createStubInstance<CPService>(CPService, {
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
        comp.colonia = entity;
        coloniaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(coloniaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.colonia = entity;
        coloniaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(coloniaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundColonia = { id: 123 };
        coloniaServiceStub.find.resolves(foundColonia);
        coloniaServiceStub.retrieve.resolves([foundColonia]);

        // WHEN
        comp.beforeRouteEnter({ params: { coloniaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.colonia).toBe(foundColonia);
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
