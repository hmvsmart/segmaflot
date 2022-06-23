/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SucursalComponent from '@/entities/sucursal/sucursal.vue';
import SucursalClass from '@/entities/sucursal/sucursal.component';
import SucursalService from '@/entities/sucursal/sucursal.service';
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
  describe('Sucursal Management Component', () => {
    let wrapper: Wrapper<SucursalClass>;
    let comp: SucursalClass;
    let sucursalServiceStub: SinonStubbedInstance<SucursalService>;

    beforeEach(() => {
      sucursalServiceStub = sinon.createStubInstance<SucursalService>(SucursalService);
      sucursalServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SucursalClass>(SucursalComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          sucursalService: () => sucursalServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      sucursalServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSucursals();
      await comp.$nextTick();

      // THEN
      expect(sucursalServiceStub.retrieve.called).toBeTruthy();
      expect(comp.sucursals[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      sucursalServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(sucursalServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSucursal();
      await comp.$nextTick();

      // THEN
      expect(sucursalServiceStub.delete.called).toBeTruthy();
      expect(sucursalServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
