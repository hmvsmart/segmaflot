/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PersonaFisicaDetailComponent from '@/entities/persona-fisica/persona-fisica-details.vue';
import PersonaFisicaClass from '@/entities/persona-fisica/persona-fisica-details.component';
import PersonaFisicaService from '@/entities/persona-fisica/persona-fisica.service';
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
  describe('PersonaFisica Management Detail Component', () => {
    let wrapper: Wrapper<PersonaFisicaClass>;
    let comp: PersonaFisicaClass;
    let personaFisicaServiceStub: SinonStubbedInstance<PersonaFisicaService>;

    beforeEach(() => {
      personaFisicaServiceStub = sinon.createStubInstance<PersonaFisicaService>(PersonaFisicaService);

      wrapper = shallowMount<PersonaFisicaClass>(PersonaFisicaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { personaFisicaService: () => personaFisicaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPersonaFisica = { id: 123 };
        personaFisicaServiceStub.find.resolves(foundPersonaFisica);

        // WHEN
        comp.retrievePersonaFisica(123);
        await comp.$nextTick();

        // THEN
        expect(comp.personaFisica).toBe(foundPersonaFisica);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersonaFisica = { id: 123 };
        personaFisicaServiceStub.find.resolves(foundPersonaFisica);

        // WHEN
        comp.beforeRouteEnter({ params: { personaFisicaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.personaFisica).toBe(foundPersonaFisica);
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
