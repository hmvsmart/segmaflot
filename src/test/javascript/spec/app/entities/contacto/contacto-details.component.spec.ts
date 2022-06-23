/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ContactoDetailComponent from '@/entities/contacto/contacto-details.vue';
import ContactoClass from '@/entities/contacto/contacto-details.component';
import ContactoService from '@/entities/contacto/contacto.service';
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
  describe('Contacto Management Detail Component', () => {
    let wrapper: Wrapper<ContactoClass>;
    let comp: ContactoClass;
    let contactoServiceStub: SinonStubbedInstance<ContactoService>;

    beforeEach(() => {
      contactoServiceStub = sinon.createStubInstance<ContactoService>(ContactoService);

      wrapper = shallowMount<ContactoClass>(ContactoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { contactoService: () => contactoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundContacto = { id: 123 };
        contactoServiceStub.find.resolves(foundContacto);

        // WHEN
        comp.retrieveContacto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.contacto).toBe(foundContacto);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundContacto = { id: 123 };
        contactoServiceStub.find.resolves(foundContacto);

        // WHEN
        comp.beforeRouteEnter({ params: { contactoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.contacto).toBe(foundContacto);
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
