import { IAditamento } from '@/shared/model/aditamento.model';
import { IUnidad } from '@/shared/model/unidad.model';

export interface IAditamentoUnidad {
  id?: number;
  fecha?: Date | null;
  numeroSerie?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  aditamento?: IAditamento | null;
  unidad?: IUnidad | null;
}

export class AditamentoUnidad implements IAditamentoUnidad {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public numeroSerie?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public aditamento?: IAditamento | null,
    public unidad?: IUnidad | null
  ) {}
}
