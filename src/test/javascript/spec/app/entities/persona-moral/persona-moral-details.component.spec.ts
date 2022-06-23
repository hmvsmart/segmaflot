/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PersonaMoralDetailComponent from '@/entities/persona-moral/persona-moral-details.vue';
import PersonaMoralClass from '@/entities/persona-moral/persona-moral-details.component';
import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
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
  describe('PersonaMoral Management Detail Component', () => {
    let wrapper: Wrapper<PersonaMoralClass>;
    let comp: PersonaMoralClass;
    let personaMoralServiceStub: SinonStubbedInstance<PersonaMoralService>;

    beforeEach(() => {
      personaMoralServiceStub = sinon.createStubInstance<PersonaMoralService>(PersonaMoralService);

      wrapper = shallowMount<PersonaMoralClass>(PersonaMoralDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { personaMoralService: () => personaMoralServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPersonaMoral = { id: 123 };
        personaMoralServiceStub.find.resolves(foundPersonaMoral);

        // WHEN
        comp.retrievePersonaMoral(123);
        await comp.$nextTick();

        // THEN
        expect(comp.personaMoral).toBe(foundPersonaMoral);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPersonaMoral = { id: 123 };
        personaMoralServiceStub.find.resolves(foundPersonaMoral);

        // WHEN
        comp.beforeRouteEnter({ params: { personaMoralId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.personaMoral).toBe(foundPersonaMoral);
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
