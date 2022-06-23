/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import HorarioComponent from '@/entities/horario/horario.vue';
import HorarioClass from '@/entities/horario/horario.component';
import HorarioService from '@/entities/horario/horario.service';
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
  describe('Horario Management Component', () => {
    let wrapper: Wrapper<HorarioClass>;
    let comp: HorarioClass;
    let horarioServiceStub: SinonStubbedInstance<HorarioService>;

    beforeEach(() => {
      horarioServiceStub = sinon.createStubInstance<HorarioService>(HorarioService);
      horarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HorarioClass>(HorarioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          horarioService: () => horarioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      horarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllHorarios();
      await comp.$nextTick();

      // THEN
      expect(horarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.horarios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      horarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(horarioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeHorario();
      await comp.$nextTick();

      // THEN
      expect(horarioServiceStub.delete.called).toBeTruthy();
      expect(horarioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
