/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DireccionComponent from '@/entities/direccion/direccion.vue';
import DireccionClass from '@/entities/direccion/direccion.component';
import DireccionService from '@/entities/direccion/direccion.service';
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
  describe('Direccion Management Component', () => {
    let wrapper: Wrapper<DireccionClass>;
    let comp: DireccionClass;
    let direccionServiceStub: SinonStubbedInstance<DireccionService>;

    beforeEach(() => {
      direccionServiceStub = sinon.createStubInstance<DireccionService>(DireccionService);
      direccionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DireccionClass>(DireccionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          direccionService: () => direccionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      direccionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDireccions();
      await comp.$nextTick();

      // THEN
      expect(direccionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.direccions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      direccionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(direccionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDireccion();
      await comp.$nextTick();

      // THEN
      expect(direccionServiceStub.delete.called).toBeTruthy();
      expect(direccionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
