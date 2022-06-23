import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';
import { IEmpleado } from '@/shared/model/empleado.model';

export interface IOperadorUnidad {
  id?: number;
  fecha?: Date | null;
  rol?: string | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  unidasViaje?: IUnidadViaje | null;
  empleado?: IEmpleado | null;
}

export class OperadorUnidad implements IOperadorUnidad {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public rol?: string | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public unidasViaje?: IUnidadViaje | null,
    public empleado?: IEmpleado | null
  ) {
    this.status = this.status ?? false;
  }
}
