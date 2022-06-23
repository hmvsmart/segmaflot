import { IVenta } from '@/shared/model/venta.model';
import { IPrecioVenta } from '@/shared/model/precio-venta.model';

export interface IRenglonVenta {
  id?: number;
  cantidad?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  venta?: IVenta | null;
  precioventa?: IPrecioVenta | null;
}

export class RenglonVenta implements IRenglonVenta {
  constructor(
    public id?: number,
    public cantidad?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public venta?: IVenta | null,
    public precioventa?: IPrecioVenta | null
  ) {}
}
