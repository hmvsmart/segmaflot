/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DiaHorarioComponent from '@/entities/dia-horario/dia-horario.vue';
import DiaHorarioClass from '@/entities/dia-horario/dia-horario.component';
import DiaHorarioService from '@/entities/dia-horario/dia-horario.service';
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
  describe('DiaHorario Management Component', () => {
    let wrapper: Wrapper<DiaHorarioClass>;
    let comp: DiaHorarioClass;
    let diaHorarioServiceStub: SinonStubbedInstance<DiaHorarioService>;

    beforeEach(() => {
      diaHorarioServiceStub = sinon.createStubInstance<DiaHorarioService>(DiaHorarioService);
      diaHorarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DiaHorarioClass>(DiaHorarioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          diaHorarioService: () => diaHorarioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      diaHorarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDiaHorarios();
      await comp.$nextTick();

      // THEN
      expect(diaHorarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.diaHorarios[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      diaHorarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(diaHorarioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDiaHorario();
      await comp.$nextTick();

      // THEN
      expect(diaHorarioServiceStub.delete.called).toBeTruthy();
      expect(diaHorarioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
