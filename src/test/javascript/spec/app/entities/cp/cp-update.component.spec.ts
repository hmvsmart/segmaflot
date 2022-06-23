/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CPUpdateComponent from '@/entities/cp/cp-update.vue';
import CPClass from '@/entities/cp/cp-update.component';
import CPService from '@/entities/cp/cp.service';

import ColoniaService from '@/entities/colonia/colonia.service';

import CiudadService from '@/entities/ciudad/ciudad.service';
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
  describe('CP Management Update Component', () => {
    let wrapper: Wrapper<CPClass>;
    let comp: CPClass;
    let cPServiceStub: SinonStubbedInstance<CPService>;

    beforeEach(() => {
      cPServiceStub = sinon.createStubInstance<CPService>(CPService);

      wrapper = shallowMount<CPClass>(CPUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          cPService: () => cPServiceStub,
          alertService: () => new AlertService(),

          coloniaService: () =>
            sinon.createStubInstance<ColoniaService>(ColoniaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          ciudadService: () =>
            sinon.createStubInstance<CiudadService>(CiudadService, {
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
        comp.cP = entity;
        cPServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cPServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cP = entity;
        cPServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cPServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCP = { id: 123 };
        cPServiceStub.find.resolves(foundCP);
        cPServiceStub.retrieve.resolves([foundCP]);

        // WHEN
        comp.beforeRouteEnter({ params: { cPId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cP).toBe(foundCP);
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
