import { IInventario } from '@/shared/model/inventario.model';
import { IServicio } from '@/shared/model/servicio.model';

export interface IMaterialServicio {
  id?: number;
  fecha?: Date | null;
  cantidad?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  inventario?: IInventario | null;
  servicio?: IServicio | null;
}

export class MaterialServicio implements IMaterialServicio {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public cantidad?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public inventario?: IInventario | null,
    public servicio?: IServicio | null
  ) {}
}
