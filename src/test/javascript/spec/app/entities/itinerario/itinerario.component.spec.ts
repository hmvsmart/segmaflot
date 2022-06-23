/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ItinerarioComponent from '@/entities/itinerario/itinerario.vue';
import ItinerarioClass from '@/entities/itinerario/itinerario.component';
import ItinerarioService from '@/entities/itinerario/itinerario.service';
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
  describe('Itinerario Management Component', () => {
    let wrapper: Wrapper<ItinerarioClass>;
    let comp: ItinerarioClass;
    let itinerarioServiceStub: SinonStubbedInstance<ItinerarioService>;

    beforeEach(() => {
      itinerarioServiceStub = sinon.createStubInstance<ItinerarioService>(ItinerarioService);
      itinerarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ItinerarioClass>(ItinerarioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          itinerarioService: () => itinerarioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      itinerarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllItinerarios();
      await comp.$nextTick();

      // THEN
      expect(itinerarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.itinerarios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      itinerarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(itinerarioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeItinerario();
      await comp.$nextTick();

      // THEN
      expect(itinerarioServiceStub.delete.called).toBeTruthy();
      expect(itinerarioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
