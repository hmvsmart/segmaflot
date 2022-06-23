/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import ResurtirUpdateComponent from '@/entities/resurtir/resurtir-update.vue';
import ResurtirClass from '@/entities/resurtir/resurtir-update.component';
import ResurtirService from '@/entities/resurtir/resurtir.service';

import RenglonResurtirService from '@/entities/renglon-resurtir/renglon-resurtir.service';

import InventarioService from '@/entities/inventario/inventario.service';
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
  describe('Resurtir Management Update Component', () => {
    let wrapper: Wrapper<ResurtirClass>;
    let comp: ResurtirClass;
    let resurtirServiceStub: SinonStubbedInstance<ResurtirService>;

    beforeEach(() => {
      resurtirServiceStub = sinon.createStubInstance<ResurtirService>(ResurtirService);

      wrapper = shallowMount<ResurtirClass>(ResurtirUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          resurtirService: () => resurtirServiceStub,
          alertService: () => new AlertService(),

          renglonResurtirService: () =>
            sinon.createStubInstance<RenglonResurtirService>(RenglonResurtirService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          inventarioService: () =>
            sinon.createStubInstance<InventarioService>(InventarioService, {
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
        comp.resurtir = entity;
        resurtirServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resurtirServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.resurtir = entity;
        resurtirServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resurtirServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResurtir = { id: 123 };
        resurtirServiceStub.find.resolves(foundResurtir);
        resurtirServiceStub.retrieve.resolves([foundResurtir]);

        // WHEN
        comp.beforeRouteEnter({ params: { resurtirId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.resurtir).toBe(foundResurtir);
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
