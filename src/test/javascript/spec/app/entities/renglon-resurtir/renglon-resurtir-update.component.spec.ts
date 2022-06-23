/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import RenglonResurtirUpdateComponent from '@/entities/renglon-resurtir/renglon-resurtir-update.vue';
import RenglonResurtirClass from '@/entities/renglon-resurtir/renglon-resurtir-update.component';
import RenglonResurtirService from '@/entities/renglon-resurtir/renglon-resurtir.service';

import ResurtirService from '@/entities/resurtir/resurtir.service';
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
  describe('RenglonResurtir Management Update Component', () => {
    let wrapper: Wrapper<RenglonResurtirClass>;
    let comp: RenglonResurtirClass;
    let renglonResurtirServiceStub: SinonStubbedInstance<RenglonResurtirService>;

    beforeEach(() => {
      renglonResurtirServiceStub = sinon.createStubInstance<RenglonResurtirService>(RenglonResurtirService);

      wrapper = shallowMount<RenglonResurtirClass>(RenglonResurtirUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          renglonResurtirService: () => renglonResurtirServiceStub,
          alertService: () => new AlertService(),

          resurtirService: () =>
            sinon.createStubInstance<ResurtirService>(ResurtirService, {
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
        comp.renglonResurtir = entity;
        renglonResurtirServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(renglonResurtirServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.renglonResurtir = entity;
        renglonResurtirServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(renglonResurtirServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRenglonResurtir = { id: 123 };
        renglonResurtirServiceStub.find.resolves(foundRenglonResurtir);
        renglonResurtirServiceStub.retrieve.resolves([foundRenglonResurtir]);

        // WHEN
        comp.beforeRouteEnter({ params: { renglonResurtirId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.renglonResurtir).toBe(foundRenglonResurtir);
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
