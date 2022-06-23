/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ResurtirDetailComponent from '@/entities/resurtir/resurtir-details.vue';
import ResurtirClass from '@/entities/resurtir/resurtir-details.component';
import ResurtirService from '@/entities/resurtir/resurtir.service';
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
  describe('Resurtir Management Detail Component', () => {
    let wrapper: Wrapper<ResurtirClass>;
    let comp: ResurtirClass;
    let resurtirServiceStub: SinonStubbedInstance<ResurtirService>;

    beforeEach(() => {
      resurtirServiceStub = sinon.createStubInstance<ResurtirService>(ResurtirService);

      wrapper = shallowMount<ResurtirClass>(ResurtirDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { resurtirService: () => resurtirServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundResurtir = { id: 123 };
        resurtirServiceStub.find.resolves(foundResurtir);

        // WHEN
        comp.retrieveResurtir(123);
        await comp.$nextTick();

        // THEN
        expect(comp.resurtir).toBe(foundResurtir);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResurtir = { id: 123 };
        resurtirServiceStub.find.resolves(foundResurtir);

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
