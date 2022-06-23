/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CPDetailComponent from '@/entities/cp/cp-details.vue';
import CPClass from '@/entities/cp/cp-details.component';
import CPService from '@/entities/cp/cp.service';
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
  describe('CP Management Detail Component', () => {
    let wrapper: Wrapper<CPClass>;
    let comp: CPClass;
    let cPServiceStub: SinonStubbedInstance<CPService>;

    beforeEach(() => {
      cPServiceStub = sinon.createStubInstance<CPService>(CPService);

      wrapper = shallowMount<CPClass>(CPDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { cPService: () => cPServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCP = { id: 123 };
        cPServiceStub.find.resolves(foundCP);

        // WHEN
        comp.retrieveCP(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cP).toBe(foundCP);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCP = { id: 123 };
        cPServiceStub.find.resolves(foundCP);

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
