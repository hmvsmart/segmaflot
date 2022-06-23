import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PersonaMoralService from '@/entities/persona-moral/persona-moral.service';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

import DireccionPersonaService from '@/entities/direccion-persona/direccion-persona.service';
import { IDireccionPersona } from '@/shared/model/direccion-persona.model';

import EmpleadoService from '@/entities/empleado/empleado.service';
import { IEmpleado } from '@/shared/model/empleado.model';

import ClienteService from '@/entities/cliente/cliente.service';
import { ICliente } from '@/shared/model/cliente.model';

import ContactoService from '@/entities/contacto/contacto.service';
import { IContacto } from '@/shared/model/contacto.model';

import { IPersona, Persona } from '@/shared/model/persona.model';
import PersonaService from './persona.service';

const validations: any = {
  persona: {},
};

@Component({
  validations,
})
export default class PersonaUpdate extends Vue {
  @Inject('personaService') private personaService: () => PersonaService;
  @Inject('alertService') private alertService: () => AlertService;

  public persona: IPersona = new Persona();

  @Inject('personaMoralService') private personaMoralService: () => PersonaMoralService;

  public personaMorals: IPersonaMoral[] = [];

  @Inject('direccionPersonaService') private direccionPersonaService: () => DireccionPersonaService;

  public direccionPersonas: IDireccionPersona[] = [];

  @Inject('empleadoService') private empleadoService: () => EmpleadoService;

  public empleados: IEmpleado[] = [];

  @Inject('clienteService') private clienteService: () => ClienteService;

  public clientes: ICliente[] = [];

  @Inject('contactoService') private contactoService: () => ContactoService;

  public contactos: IContacto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.personaId) {
        vm.retrievePersona(to.params.personaId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.persona.id) {
      this.personaService()
        .update(this.persona)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.persona.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.personaService()
        .create(this.persona)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('segmaflotApp.persona.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrievePersona(personaId): void {
    this.personaService()
      .find(personaId)
      .then(res => {
        this.persona = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.personaMoralService()
      .retrieve()
      .then(res => {
        this.personaMorals = res.data;
      });
    this.direccionPersonaService()
      .retrieve()
      .then(res => {
        this.direccionPersonas = res.data;
      });
    this.empleadoService()
      .retrieve()
      .then(res => {
        this.empleados = res.data;
      });
    this.clienteService()
      .retrieve()
      .then(res => {
        this.clientes = res.data;
      });
    this.contactoService()
      .retrieve()
      .then(res => {
        this.contactos = res.data;
      });
  }
}
