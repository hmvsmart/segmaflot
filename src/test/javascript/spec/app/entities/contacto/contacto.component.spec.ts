/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ContactoComponent from '@/entities/contacto/contacto.vue';
import ContactoClass from '@/entities/contacto/contacto.component';
import ContactoService from '@/entities/contacto/contacto.service';
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
  describe('Contacto Management Component', () => {
    let wrapper: Wrapper<ContactoClass>;
    let comp: ContactoClass;
    let contactoServiceStub: SinonStubbedInstance<ContactoService>;

    beforeEach(() => {
      contactoServiceStub = sinon.createStubInstance<ContactoService>(ContactoService);
      contactoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ContactoClass>(ContactoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          contactoService: () => contactoServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      contactoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllContactos();
      await comp.$nextTick();

      // THEN
      expect(contactoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.contactos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      contactoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(contactoServiceStub.retrieve.callCount).toEqual(1);

      comp.removeContacto();
      await comp.$nextTick();

      // THEN
      expect(contactoServiceStub.delete.called).toBeTruthy();
      expect(contactoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
