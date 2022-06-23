import { IPersonaMoral } from '@/shared/model/persona-moral.model';

export interface IReporte {
  id?: number;
  fecha?: Date | null;
  nombre?: string | null;
  documentoContentType?: string | null;
  documento?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  empresa?: IPersonaMoral | null;
}

export class Reporte implements IReporte {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public nombre?: string | null,
    public documentoContentType?: string | null,
    public documento?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public empresa?: IPersonaMoral | null
  ) {}
}
