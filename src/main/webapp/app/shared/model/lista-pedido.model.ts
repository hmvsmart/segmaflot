import { IPedido } from '@/shared/model/pedido.model';
import { IInventario } from '@/shared/model/inventario.model';

export interface IListaPedido {
  id?: number;
  cantidad?: number | null;
  pedido?: IPedido | null;
  inventario?: IInventario | null;
}

export class ListaPedido implements IListaPedido {
  constructor(
    public id?: number,
    public cantidad?: number | null,
    public pedido?: IPedido | null,
    public inventario?: IInventario | null
  ) {}
}
