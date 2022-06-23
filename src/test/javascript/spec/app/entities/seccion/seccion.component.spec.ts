/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SeccionComponent from '@/entities/seccion/seccion.vue';
import SeccionClass from '@/entities/seccion/seccion.component';
import SeccionService from '@/entities/seccion/seccion.service';
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
  describe('Seccion Management Component', () => {
    let wrapper: Wrapper<SeccionClass>;
    let comp: SeccionClass;
    let seccionServiceStub: SinonStubbedInstance<SeccionService>;

    beforeEach(() => {
      seccionServiceStub = sinon.createStubInstance<SeccionService>(SeccionService);
      seccionServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SeccionClass>(SeccionComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          seccionService: () => seccionServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      seccionServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSeccions();
      await comp.$nextTick();

      // THEN
      expect(seccionServiceStub.retrieve.called).toBeTruthy();
      expect(comp.seccions[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      seccionServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(seccionServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSeccion();
      await comp.$nextTick();

      // THEN
      expect(seccionServiceStub.delete.called).toBeTruthy();
      expect(seccionServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
