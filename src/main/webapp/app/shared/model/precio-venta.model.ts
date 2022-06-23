import { IRenglonVenta } from '@/shared/model/renglon-venta.model';
import { IInventario } from '@/shared/model/inventario.model';

export interface IPrecioVenta {
  id?: number;
  fecha?: Date | null;
  ppu?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  renglonVentas?: IRenglonVenta[] | null;
  inventario?: IInventario | null;
}

export class PrecioVenta implements IPrecioVenta {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public ppu?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public renglonVentas?: IRenglonVenta[] | null,
    public inventario?: IInventario | null
  ) {}
}
