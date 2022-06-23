import { ISeccion } from '@/shared/model/seccion.model';
import { IInventario } from '@/shared/model/inventario.model';

export interface IUbicacionInventario {
  id?: number;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  seccion?: ISeccion | null;
  inventario?: IInventario | null;
}

export class UbicacionInventario implements IUbicacionInventario {
  constructor(
    public id?: number,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public seccion?: ISeccion | null,
    public inventario?: IInventario | null
  ) {
    this.status = this.status ?? false;
  }
}
