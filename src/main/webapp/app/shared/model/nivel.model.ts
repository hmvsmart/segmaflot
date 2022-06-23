import { ISeccion } from '@/shared/model/seccion.model';
import { IEstante } from '@/shared/model/estante.model';

export interface INivel {
  id?: number;
  nombre?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  seccions?: ISeccion[] | null;
  estante?: IEstante | null;
}

export class Nivel implements INivel {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public seccions?: ISeccion[] | null,
    public estante?: IEstante | null
  ) {}
}
