/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MaterialServicioDetailComponent from '@/entities/material-servicio/material-servicio-details.vue';
import MaterialServicioClass from '@/entities/material-servicio/material-servicio-details.component';
import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('MaterialServicio Management Detail Component', () => {
    let wrapper: Wrapper<MaterialServicioClass>;
    let comp: MaterialServicioClass;
    let materialServicioServiceStub: SinonStubbedInstance<MaterialServicioService>;

    beforeEach(() => {
      materialServicioServiceStub = sinon.createStubInstance<MaterialServicioService>(MaterialServicioService);

      wrapper = shallowMount<MaterialServicioClass>(MaterialServicioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { materialServicioService: () => materialServicioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMaterialServicio = { id: 123 };
        materialServicioServiceStub.find.resolves(foundMaterialServicio);

        // WHEN
        comp.retrieveMaterialServicio(123);
        await comp.$nextTick();

        // THEN
        expect(comp.materialServicio).toBe(foundMaterialServicio);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMaterialServicio = { id: 123 };
        materialServicioServiceStub.find.resolves(foundMaterialServicio);

        // WHEN
        comp.beforeRouteEnter({ params: { materialServicioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.materialServicio).toBe(foundMaterialServicio);
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
