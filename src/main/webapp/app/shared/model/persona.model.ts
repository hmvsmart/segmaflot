import { IPersonaMoral } from '@/shared/model/persona-moral.model';
import { IDireccionPersona } from '@/shared/model/direccion-persona.model';
import { IEmpleado } from '@/shared/model/empleado.model';
import { ICliente } from '@/shared/model/cliente.model';
import { IContacto } from '@/shared/model/contacto.model';

export interface IPersona {
  id?: number;
  personaMorals?: IPersonaMoral[] | null;
  direccionPersonas?: IDireccionPersona[] | null;
  empleados?: IEmpleado[] | null;
  clientes?: ICliente[] | null;
  contactos?: IContacto[] | null;
}

export class Persona implements IPersona {
  constructor(
    public id?: number,
    public personaMorals?: IPersonaMoral[] | null,
    public direccionPersonas?: IDireccionPersona[] | null,
    public empleados?: IEmpleado[] | null,
    public clientes?: ICliente[] | null,
    public contactos?: IContacto[] | null
  ) {}
}
