import { ICita } from '@/shared/model/cita.model';

import { TipoStatusCita } from '@/shared/model/enumerations/tipo-status-cita.model';
export interface IStatusCita {
  id?: number;
  fecha?: Date | null;
  status?: TipoStatusCita | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  cita?: ICita | null;
}

export class StatusCita implements IStatusCita {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public status?: TipoStatusCita | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public cita?: ICita | null
  ) {}
}
