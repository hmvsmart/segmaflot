import { IOperadorUnidad } from '@/shared/model/operador-unidad.model';
import { IViaje } from '@/shared/model/viaje.model';
import { IUnidad } from '@/shared/model/unidad.model';

export interface IUnidadViaje {
  id?: number;
  fecha?: Date | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  operadorUnidads?: IOperadorUnidad[] | null;
  viaje?: IViaje | null;
  unidad?: IUnidad | null;
}

export class UnidadViaje implements IUnidadViaje {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public operadorUnidads?: IOperadorUnidad[] | null,
    public viaje?: IViaje | null,
    public unidad?: IUnidad | null
  ) {
    this.status = this.status ?? false;
  }
}
