import { IUbicacionInventario } from '@/shared/model/ubicacion-inventario.model';
import { INivel } from '@/shared/model/nivel.model';

export interface ISeccion {
  id?: number;
  nombre?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  ubicacionInventarios?: IUbicacionInventario[] | null;
  nivel?: INivel | null;
}

export class Seccion implements ISeccion {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public ubicacionInventarios?: IUbicacionInventario[] | null,
    public nivel?: INivel | null
  ) {}
}
