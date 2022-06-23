/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EmpleadoComponent from '@/entities/empleado/empleado.vue';
import EmpleadoClass from '@/entities/empleado/empleado.component';
import EmpleadoService from '@/entities/empleado/empleado.service';
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
  describe('Empleado Management Component', () => {
    let wrapper: Wrapper<EmpleadoClass>;
    let comp: EmpleadoClass;
    let empleadoServiceStub: SinonStubbedInstance<EmpleadoService>;

    beforeEach(() => {
      empleadoServiceStub = sinon.createStubInstance<EmpleadoService>(EmpleadoService);
      empleadoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EmpleadoClass>(EmpleadoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          empleadoService: () => empleadoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      empleadoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEmpleados();
      await comp.$nextTick();

      // THEN
      expect(empleadoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.empleados[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      empleadoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(empleadoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEmpleado();
      await comp.$nextTick();

      // THEN
      expect(empleadoServiceStub.delete.called).toBeTruthy();
      expect(empleadoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
