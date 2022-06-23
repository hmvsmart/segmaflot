/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ListaServicioComponent from '@/entities/lista-servicio/lista-servicio.vue';
import ListaServicioClass from '@/entities/lista-servicio/lista-servicio.component';
import ListaServicioService from '@/entities/lista-servicio/lista-servicio.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('ListaServicio Management Component', () => {
    let wrapper: Wrapper<ListaServicioClass>;
    let comp: ListaServicioClass;
    let listaServicioServiceStub: SinonStubbedInstance<ListaServicioService>;

    beforeEach(() => {
      listaServicioServiceStub = sinon.createStubInstance<ListaServicioService>(ListaServicioService);
      listaServicioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ListaServicioClass>(ListaServicioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          listaServicioService: () => listaServicioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      listaServicioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllListaServicios();
      await comp.$nextTick();

      // THEN
      expect(listaServicioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.listaServicios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      listaServicioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(listaServicioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeListaServicio();
      await comp.$nextTick();

      // THEN
      expect(listaServicioServiceStub.delete.called).toBeTruthy();
      expect(listaServicioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
