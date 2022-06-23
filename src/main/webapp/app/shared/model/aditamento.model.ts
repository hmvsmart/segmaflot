import { IAditamentoUnidad } from '@/shared/model/aditamento-unidad.model';

export interface IAditamento {
  id?: number;
  nombre?: string | null;
  numeroSerie?: string | null;
  descripcionContentType?: string | null;
  descripcion?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  aditamentoUnidads?: IAditamentoUnidad[] | null;
}

export class Aditamento implements IAditamento {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public numeroSerie?: string | null,
    public descripcionContentType?: string | null,
    public descripcion?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public aditamentoUnidads?: IAditamentoUnidad[] | null
  ) {}
}
