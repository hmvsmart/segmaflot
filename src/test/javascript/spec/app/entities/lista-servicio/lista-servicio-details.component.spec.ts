/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ListaServicioDetailComponent from '@/entities/lista-servicio/lista-servicio-details.vue';
import ListaServicioClass from '@/entities/lista-servicio/lista-servicio-details.component';
import ListaServicioService from '@/entities/lista-servicio/lista-servicio.service';
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
  describe('ListaServicio Management Detail Component', () => {
    let wrapper: Wrapper<ListaServicioClass>;
    let comp: ListaServicioClass;
    let listaServicioServiceStub: SinonStubbedInstance<ListaServicioService>;

    beforeEach(() => {
      listaServicioServiceStub = sinon.createStubInstance<ListaServicioService>(ListaServicioService);

      wrapper = shallowMount<ListaServicioClass>(ListaServicioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { listaServicioService: () => listaServicioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundListaServicio = { id: 123 };
        listaServicioServiceStub.find.resolves(foundListaServicio);

        // WHEN
        comp.retrieveListaServicio(123);
        await comp.$nextTick();

        // THEN
        expect(comp.listaServicio).toBe(foundListaServicio);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundListaServicio = { id: 123 };
        listaServicioServiceStub.find.resolves(foundListaServicio);

        // WHEN
        comp.beforeRouteEnter({ params: { listaServicioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.listaServicio).toBe(foundListaServicio);
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
