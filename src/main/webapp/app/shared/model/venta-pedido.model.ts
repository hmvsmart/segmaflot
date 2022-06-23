import { IVenta } from '@/shared/model/venta.model';
import { IPedido } from '@/shared/model/pedido.model';

export interface IVentaPedido {
  id?: number;
  fecha?: Date | null;
  venta?: IVenta | null;
  pedido?: IPedido | null;
}

export class VentaPedido implements IVentaPedido {
  constructor(public id?: number, public fecha?: Date | null, public venta?: IVenta | null, public pedido?: IPedido | null) {}
}
