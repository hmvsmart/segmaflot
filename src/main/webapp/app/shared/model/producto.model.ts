import { IInventario } from '@/shared/model/inventario.model';

export interface IProducto {
  id?: number;
  nombre?: string | null;
  marca?: string | null;
  tipo?: string | null;
  cantidad?: number | null;
  unidadMedida?: string | null;
  material?: string | null;
  color?: string | null;
  uso?: string | null;
  descripcionContentType?: string | null;
  descripcion?: string | null;
  otrosContentType?: string | null;
  otros?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  inventarios?: IInventario[] | null;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public marca?: string | null,
    public tipo?: string | null,
    public cantidad?: number | null,
    public unidadMedida?: string | null,
    public material?: string | null,
    public color?: string | null,
    public uso?: string | null,
    public descripcionContentType?: string | null,
    public descripcion?: string | null,
    public otrosContentType?: string | null,
    public otros?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public inventarios?: IInventario[] | null
  ) {}
}
