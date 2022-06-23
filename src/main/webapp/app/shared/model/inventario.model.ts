import { IUbicacionInventario } from '@/shared/model/ubicacion-inventario.model';
import { IResurtir } from '@/shared/model/resurtir.model';
import { IPrecioVenta } from '@/shared/model/precio-venta.model';
import { IMaterialServicio } from '@/shared/model/material-servicio.model';
import { IPerdida } from '@/shared/model/perdida.model';
import { IListaPedido } from '@/shared/model/lista-pedido.model';
import { IProducto } from '@/shared/model/producto.model';
import { ISucursal } from '@/shared/model/sucursal.model';

export interface IInventario {
  id?: number;
  cantidad?: number | null;
  cantidadMinimaEst?: number | null;
  cantidadMaximaEst?: number | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  ubicacionInventarios?: IUbicacionInventario[] | null;
  resurtirs?: IResurtir[] | null;
  precioVentas?: IPrecioVenta[] | null;
  materialServicios?: IMaterialServicio[] | null;
  perdidas?: IPerdida[] | null;
  listaPedidos?: IListaPedido[] | null;
  producto?: IProducto | null;
  sucursal?: ISucursal | null;
}

export class Inventario implements IInventario {
  constructor(
    public id?: number,
    public cantidad?: number | null,
    public cantidadMinimaEst?: number | null,
    public cantidadMaximaEst?: number | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public ubicacionInventarios?: IUbicacionInventario[] | null,
    public resurtirs?: IResurtir[] | null,
    public precioVentas?: IPrecioVenta[] | null,
    public materialServicios?: IMaterialServicio[] | null,
    public perdidas?: IPerdida[] | null,
    public listaPedidos?: IListaPedido[] | null,
    public producto?: IProducto | null,
    public sucursal?: ISucursal | null
  ) {
    this.status = this.status ?? false;
  }
}
