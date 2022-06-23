import { IRenglonVenta } from '@/shared/model/renglon-venta.model';
import { IVentaPedido } from '@/shared/model/venta-pedido.model';

export interface IVenta {
  id?: number;
  fecha?: Date | null;
  total?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  renglonVentas?: IRenglonVenta[] | null;
  ventaPedidos?: IVentaPedido[] | null;
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public total?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public renglonVentas?: IRenglonVenta[] | null,
    public ventaPedidos?: IVentaPedido[] | null
  ) {}
}
