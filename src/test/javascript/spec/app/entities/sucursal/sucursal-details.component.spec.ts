/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SucursalDetailComponent from '@/entities/sucursal/sucursal-details.vue';
import SucursalClass from '@/entities/sucursal/sucursal-details.component';
import SucursalService from '@/entities/sucursal/sucursal.service';
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
  describe('Sucursal Management Detail Component', () => {
    let wrapper: Wrapper<SucursalClass>;
    let comp: SucursalClass;
    let sucursalServiceStub: SinonStubbedInstance<SucursalService>;

    beforeEach(() => {
      sucursalServiceStub = sinon.createStubInstance<SucursalService>(SucursalService);

      wrapper = shallowMount<SucursalClass>(SucursalDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { sucursalService: () => sucursalServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSucursal = { id: 123 };
        sucursalServiceStub.find.resolves(foundSucursal);

        // WHEN
        comp.retrieveSucursal(123);
        await comp.$nextTick();

        // THEN
        expect(comp.sucursal).toBe(foundSucursal);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSucursal = { id: 123 };
        sucursalServiceStub.find.resolves(foundSucursal);

        // WHEN
        comp.beforeRouteEnter({ params: { sucursalId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.sucursal).toBe(foundSucursal);
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
