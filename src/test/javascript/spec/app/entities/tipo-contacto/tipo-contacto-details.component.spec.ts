/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TipoContactoDetailComponent from '@/entities/tipo-contacto/tipo-contacto-details.vue';
import TipoContactoClass from '@/entities/tipo-contacto/tipo-contacto-details.component';
import TipoContactoService from '@/entities/tipo-contacto/tipo-contacto.service';
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
  describe('TipoContacto Management Detail Component', () => {
    let wrapper: Wrapper<TipoContactoClass>;
    let comp: TipoContactoClass;
    let tipoContactoServiceStub: SinonStubbedInstance<TipoContactoService>;

    beforeEach(() => {
      tipoContactoServiceStub = sinon.createStubInstance<TipoContactoService>(TipoContactoService);

      wrapper = shallowMount<TipoContactoClass>(TipoContactoDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { tipoContactoService: () => tipoContactoServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTipoContacto = { id: 123 };
        tipoContactoServiceStub.find.resolves(foundTipoContacto);

        // WHEN
        comp.retrieveTipoContacto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tipoContacto).toBe(foundTipoContacto);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoContacto = { id: 123 };
        tipoContactoServiceStub.find.resolves(foundTipoContacto);

        // WHEN
        comp.beforeRouteEnter({ params: { tipoContactoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tipoContacto).toBe(foundTipoContacto);
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
