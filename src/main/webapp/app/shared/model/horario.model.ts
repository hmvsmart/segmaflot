import { IDiaHorario } from '@/shared/model/dia-horario.model';
import { IEmpleado } from '@/shared/model/empleado.model';

export interface IHorario {
  id?: number;
  fecha?: Date | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  diaHorarios?: IDiaHorario[] | null;
  empleado?: IEmpleado | null;
}

export class Horario implements IHorario {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public diaHorarios?: IDiaHorario[] | null,
    public empleado?: IEmpleado | null
  ) {
    this.status = this.status ?? false;
  }
}
