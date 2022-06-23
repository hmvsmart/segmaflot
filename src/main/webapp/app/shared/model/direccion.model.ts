import { IDireccionPersona } from '@/shared/model/direccion-persona.model';
import { IViaje } from '@/shared/model/viaje.model';
import { IColonia } from '@/shared/model/colonia.model';

export interface IDireccion {
  id?: number;
  calle?: string | null;
  numeroExterior?: number | null;
  numeroInterior?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  direccionPersonas?: IDireccionPersona[] | null;
  viajes?: IViaje[] | null;
  colonia?: IColonia | null;
}

export class Direccion implements IDireccion {
  constructor(
    public id?: number,
    public calle?: string | null,
    public numeroExterior?: number | null,
    public numeroInterior?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public direccionPersonas?: IDireccionPersona[] | null,
    public viajes?: IViaje[] | null,
    public colonia?: IColonia | null
  ) {}
}
