import { IUnidad } from '@/shared/model/unidad.model';

export interface IPoliza {
  id?: number;
  fecha?: Date | null;
  fechaVencimiento?: Date | null;
  numeroPoliza?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  unidad?: IUnidad | null;
}

export class Poliza implements IPoliza {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public fechaVencimiento?: Date | null,
    public numeroPoliza?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public unidad?: IUnidad | null
  ) {}
}
