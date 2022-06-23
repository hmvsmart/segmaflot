/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RenglonResurtirDetailComponent from '@/entities/renglon-resurtir/renglon-resurtir-details.vue';
import RenglonResurtirClass from '@/entities/renglon-resurtir/renglon-resurtir-details.component';
import RenglonResurtirService from '@/entities/renglon-resurtir/renglon-resurtir.service';
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
  describe('RenglonResurtir Management Detail Component', () => {
    let wrapper: Wrapper<RenglonResurtirClass>;
    let comp: RenglonResurtirClass;
    let renglonResurtirServiceStub: SinonStubbedInstance<RenglonResurtirService>;

    beforeEach(() => {
      renglonResurtirServiceStub = sinon.createStubInstance<RenglonResurtirService>(RenglonResurtirService);

      wrapper = shallowMount<RenglonResurtirClass>(RenglonResurtirDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { renglonResurtirService: () => renglonResurtirServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRenglonResurtir = { id: 123 };
        renglonResurtirServiceStub.find.resolves(foundRenglonResurtir);

        // WHEN
        comp.retrieveRenglonResurtir(123);
        await comp.$nextTick();

        // THEN
        expect(comp.renglonResurtir).toBe(foundRenglonResurtir);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRenglonResurtir = { id: 123 };
        renglonResurtirServiceStub.find.resolves(foundRenglonResurtir);

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
