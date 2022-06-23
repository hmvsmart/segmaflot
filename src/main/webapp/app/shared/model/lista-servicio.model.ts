import { IServicio } from '@/shared/model/servicio.model';
import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';

export interface IListaServicio {
  id?: number;
  cantidad?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  servicio?: IServicio | null;
  unidadServicio?: IUnidadServicio | null;
}

export class ListaServicio implements IListaServicio {
  constructor(
    public id?: number,
    public cantidad?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public servicio?: IServicio | null,
    public unidadServicio?: IUnidadServicio | null
  ) {}
}
