import { IListaPedido } from '@/shared/model/lista-pedido.model';
import { IVentaPedido } from '@/shared/model/venta-pedido.model';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

export interface IPedido {
  id?: number;
  fecha?: Date | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  listaPedidos?: IListaPedido[] | null;
  ventaPedidos?: IVentaPedido[] | null;
  empresa?: IPersonaMoral | null;
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public listaPedidos?: IListaPedido[] | null,
    public ventaPedidos?: IVentaPedido[] | null,
    public empresa?: IPersonaMoral | null
  ) {}
}
