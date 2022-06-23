import { IDireccion } from '@/shared/model/direccion.model';
import { IPersona } from '@/shared/model/persona.model';

export interface IDireccionPersona {
  id?: number;
  fecha?: Date | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  direccion?: IDireccion | null;
  persona?: IPersona | null;
}

export class DireccionPersona implements IDireccionPersona {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public direccion?: IDireccion | null,
    public persona?: IPersona | null
  ) {
    this.status = this.status ?? false;
  }
}
