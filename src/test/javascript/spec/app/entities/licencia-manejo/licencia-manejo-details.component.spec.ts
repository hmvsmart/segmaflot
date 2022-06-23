/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import LicenciaManejoDetailComponent from '@/entities/licencia-manejo/licencia-manejo-details.vue';
import LicenciaManejoClass from '@/entities/licencia-manejo/licencia-manejo-details.component';
import LicenciaManejoService from '@/entities/licencia-manejo/licencia-manejo.service';
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
  describe('LicenciaManejo Management Detail Component', () => {
    let wrapper: Wrapper<LicenciaManejoClass>;
    let comp: LicenciaManejoClass;
    let licenciaManejoServiceStub: SinonStubbedInstance<LicenciaManejoService>;

    beforeEach(() => {
      licenciaManejoServiceStub = sinon.createStubInstance<LicenciaManejoService>(LicenciaManejoService);

      wrapper = shallowMount<LicenciaManejoClass>(LicenciaManejoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { licenciaManejoService: () => licenciaManejoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLicenciaManejo = { id: 123 };
        licenciaManejoServiceStub.find.resolves(foundLicenciaManejo);

        // WHEN
        comp.retrieveLicenciaManejo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.licenciaManejo).toBe(foundLicenciaManejo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLicenciaManejo = { id: 123 };
        licenciaManejoServiceStub.find.resolves(foundLicenciaManejo);

        // WHEN
        comp.beforeRouteEnter({ params: { licenciaManejoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.licenciaManejo).toBe(foundLicenciaManejo);
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
