/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TipoContactoComponent from '@/entities/tipo-contacto/tipo-contacto.vue';
import TipoContactoClass from '@/entities/tipo-contacto/tipo-contacto.component';
import TipoContactoService from '@/entities/tipo-contacto/tipo-contacto.service';
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
  describe('TipoContacto Management Component', () => {
    let wrapper: Wrapper<TipoContactoClass>;
    let comp: TipoContactoClass;
    let tipoContactoServiceStub: SinonStubbedInstance<TipoContactoService>;

    beforeEach(() => {
      tipoContactoServiceStub = sinon.createStubInstance<TipoContactoService>(TipoContactoService);
      tipoContactoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TipoContactoClass>(TipoContactoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          tipoContactoService: () => tipoContactoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tipoContactoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTipoContactos();
      await comp.$nextTick();

      // THEN
      expect(tipoContactoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tipoContactos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      tipoContactoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(tipoContactoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTipoContacto();
      await comp.$nextTick();

      // THEN
      expect(tipoContactoServiceStub.delete.called).toBeTruthy();
      expect(tipoContactoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
