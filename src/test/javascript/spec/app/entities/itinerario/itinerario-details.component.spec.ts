/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ItinerarioDetailComponent from '@/entities/itinerario/itinerario-details.vue';
import ItinerarioClass from '@/entities/itinerario/itinerario-details.component';
import ItinerarioService from '@/entities/itinerario/itinerario.service';
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
  describe('Itinerario Management Detail Component', () => {
    let wrapper: Wrapper<ItinerarioClass>;
    let comp: ItinerarioClass;
    let itinerarioServiceStub: SinonStubbedInstance<ItinerarioService>;

    beforeEach(() => {
      itinerarioServiceStub = sinon.createStubInstance<ItinerarioService>(ItinerarioService);

      wrapper = shallowMount<ItinerarioClass>(ItinerarioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { itinerarioService: () => itinerarioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundItinerario = { id: 123 };
        itinerarioServiceStub.find.resolves(foundItinerario);

        // WHEN
        comp.retrieveItinerario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.itinerario).toBe(foundItinerario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundItinerario = { id: 123 };
        itinerarioServiceStub.find.resolves(foundItinerario);

        // WHEN
        comp.beforeRouteEnter({ params: { itinerarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.itinerario).toBe(foundItinerario);
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
