import { IInventario } from '@/shared/model/inventario.model';
import { IEmpleado } from '@/shared/model/empleado.model';
import { IPersonaMoral } from '@/shared/model/persona-moral.model';

export interface ISucursal {
  id?: number;
  nombre?: string | null;
  tipoSucursal?: string | null;
  telefono?: string | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  inventarios?: IInventario[] | null;
  empleados?: IEmpleado[] | null;
  empresa?: IPersonaMoral | null;
}

export class Sucursal implements ISucursal {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public tipoSucursal?: string | null,
    public telefono?: string | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public inventarios?: IInventario[] | null,
    public empleados?: IEmpleado[] | null,
    public empresa?: IPersonaMoral | null
  ) {
    this.status = this.status ?? false;
  }
}
