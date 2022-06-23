import { INivel } from '@/shared/model/nivel.model';

export interface IEstante {
  id?: number;
  nombre?: string | null;
  material?: string | null;
  color?: string | null;
  descripcion?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  nivels?: INivel[] | null;
}

export class Estante implements IEstante {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public material?: string | null,
    public color?: string | null,
    public descripcion?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public nivels?: INivel[] | null
  ) {}
}
