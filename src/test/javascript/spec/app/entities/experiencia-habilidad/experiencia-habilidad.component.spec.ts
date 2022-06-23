/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ExperienciaHabilidadComponent from '@/entities/experiencia-habilidad/experiencia-habilidad.vue';
import ExperienciaHabilidadClass from '@/entities/experiencia-habilidad/experiencia-habilidad.component';
import ExperienciaHabilidadService from '@/entities/experiencia-habilidad/experiencia-habilidad.service';
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
  describe('ExperienciaHabilidad Management Component', () => {
    let wrapper: Wrapper<ExperienciaHabilidadClass>;
    let comp: ExperienciaHabilidadClass;
    let experienciaHabilidadServiceStub: SinonStubbedInstance<ExperienciaHabilidadService>;

    beforeEach(() => {
      experienciaHabilidadServiceStub = sinon.createStubInstance<ExperienciaHabilidadService>(ExperienciaHabilidadService);
      experienciaHabilidadServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ExperienciaHabilidadClass>(ExperienciaHabilidadComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          experienciaHabilidadService: () => experienciaHabilidadServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      experienciaHabilidadServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllExperienciaHabilidads();
      await comp.$nextTick();

      // THEN
      expect(experienciaHabilidadServiceStub.retrieve.called).toBeTruthy();
      expect(comp.experienciaHabilidads[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      experienciaHabilidadServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(experienciaHabilidadServiceStub.retrieve.callCount).toEqual(1);

      comp.removeExperienciaHabilidad();
      await comp.$nextTick();

      // THEN
      expect(experienciaHabilidadServiceStub.delete.called).toBeTruthy();
      expect(experienciaHabilidadServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
