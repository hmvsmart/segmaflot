import { IServicio } from '@/shared/model/servicio.model';

export interface IPrecioServicio {
  id?: number;
  fecha?: Date | null;
  costo?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  servicio?: IServicio | null;
}

export class PrecioServicio implements IPrecioServicio {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public costo?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public servicio?: IServicio | null
  ) {}
}
