import { IEmpleado } from '@/shared/model/empleado.model';

export interface IExperienciaHabilidad {
  id?: number;
  fecha?: Date | null;
  descripcionContentType?: string | null;
  descripcion?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  empleado?: IEmpleado | null;
}

export class ExperienciaHabilidad implements IExperienciaHabilidad {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public descripcionContentType?: string | null,
    public descripcion?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public empleado?: IEmpleado | null
  ) {}
}
