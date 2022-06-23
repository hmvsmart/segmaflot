/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MaterialServicioComponent from '@/entities/material-servicio/material-servicio.vue';
import MaterialServicioClass from '@/entities/material-servicio/material-servicio.component';
import MaterialServicioService from '@/entities/material-servicio/material-servicio.service';
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
  describe('MaterialServicio Management Component', () => {
    let wrapper: Wrapper<MaterialServicioClass>;
    let comp: MaterialServicioClass;
    let materialServicioServiceStub: SinonStubbedInstance<MaterialServicioService>;

    beforeEach(() => {
      materialServicioServiceStub = sinon.createStubInstance<MaterialServicioService>(MaterialServicioService);
      materialServicioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MaterialServicioClass>(MaterialServicioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          materialServicioService: () => materialServicioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      materialServicioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMaterialServicios();
      await comp.$nextTick();

      // THEN
      expect(materialServicioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.materialServicios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      materialServicioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(materialServicioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMaterialServicio();
      await comp.$nextTick();

      // THEN
      expect(materialServicioServiceStub.delete.called).toBeTruthy();
      expect(materialServicioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
