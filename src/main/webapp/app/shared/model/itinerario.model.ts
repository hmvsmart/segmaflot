import { IViaje } from '@/shared/model/viaje.model';

export interface IItinerario {
  id?: number;
  accion?: string | null;
  fechaHoraEstimada?: Date | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  viaje?: IViaje | null;
}

export class Itinerario implements IItinerario {
  constructor(
    public id?: number,
    public accion?: string | null,
    public fechaHoraEstimada?: Date | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public viaje?: IViaje | null
  ) {}
}
