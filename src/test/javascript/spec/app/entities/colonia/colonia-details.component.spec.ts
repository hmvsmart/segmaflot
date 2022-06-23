/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ColoniaDetailComponent from '@/entities/colonia/colonia-details.vue';
import ColoniaClass from '@/entities/colonia/colonia-details.component';
import ColoniaService from '@/entities/colonia/colonia.service';
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
  describe('Colonia Management Detail Component', () => {
    let wrapper: Wrapper<ColoniaClass>;
    let comp: ColoniaClass;
    let coloniaServiceStub: SinonStubbedInstance<ColoniaService>;

    beforeEach(() => {
      coloniaServiceStub = sinon.createStubInstance<ColoniaService>(ColoniaService);

      wrapper = shallowMount<ColoniaClass>(ColoniaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { coloniaService: () => coloniaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundColonia = { id: 123 };
        coloniaServiceStub.find.resolves(foundColonia);

        // WHEN
        comp.retrieveColonia(123);
        await comp.$nextTick();

        // THEN
        expect(comp.colonia).toBe(foundColonia);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundColonia = { id: 123 };
        coloniaServiceStub.find.resolves(foundColonia);

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
