import { IHorario } from '@/shared/model/horario.model';

export interface IDiaHorario {
  id?: number;
  dia?: number | null;
  horaEntrada?: Date | null;
  horaSalida?: Date | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  horario?: IHorario | null;
}

export class DiaHorario implements IDiaHorario {
  constructor(
    public id?: number,
    public dia?: number | null,
    public horaEntrada?: Date | null,
    public horaSalida?: Date | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public horario?: IHorario | null
  ) {}
}
