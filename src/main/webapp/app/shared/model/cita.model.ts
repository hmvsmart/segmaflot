import { IStatusCita } from '@/shared/model/status-cita.model';

export interface ICita {
  id?: number;
  fecha?: Date | null;
  horaInicio?: Date | null;
  horaFin?: Date | null;
  area?: string | null;
  identificador?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  statusCitas?: IStatusCita[] | null;
}

export class Cita implements ICita {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public horaInicio?: Date | null,
    public horaFin?: Date | null,
    public area?: string | null,
    public identificador?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public statusCitas?: IStatusCita[] | null
  ) {}
}
