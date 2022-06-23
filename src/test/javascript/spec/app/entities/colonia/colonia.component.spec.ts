/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ColoniaComponent from '@/entities/colonia/colonia.vue';
import ColoniaClass from '@/entities/colonia/colonia.component';
import ColoniaService from '@/entities/colonia/colonia.service';
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
  describe('Colonia Management Component', () => {
    let wrapper: Wrapper<ColoniaClass>;
    let comp: ColoniaClass;
    let coloniaServiceStub: SinonStubbedInstance<ColoniaService>;

    beforeEach(() => {
      coloniaServiceStub = sinon.createStubInstance<ColoniaService>(ColoniaService);
      coloniaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ColoniaClass>(ColoniaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          coloniaService: () => coloniaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      coloniaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllColonias();
      await comp.$nextTick();

      // THEN
      expect(coloniaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.colonias[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      coloniaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(coloniaServiceStub.retrieve.callCount).toEqual(1);

      comp.removeColonia();
      await comp.$nextTick();

      // THEN
      expect(coloniaServiceStub.delete.called).toBeTruthy();
      expect(coloniaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
