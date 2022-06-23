import { IItinerario } from '@/shared/model/itinerario.model';
import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';
import { IDireccion } from '@/shared/model/direccion.model';

export interface IViaje {
  id?: number;
  fecha?: Date | null;
  fechaInicio?: Date | null;
  fechaFin?: Date | null;
  horaEmbarque?: Date | null;
  tipoCarga?: string | null;
  pesoNeto?: number | null;
  comentariosContentType?: string | null;
  comentarios?: string | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  itinerarios?: IItinerario[] | null;
  unidadViajes?: IUnidadViaje[] | null;
  origen?: IDireccion | null;
}

export class Viaje implements IViaje {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public fechaInicio?: Date | null,
    public fechaFin?: Date | null,
    public horaEmbarque?: Date | null,
    public tipoCarga?: string | null,
    public pesoNeto?: number | null,
    public comentariosContentType?: string | null,
    public comentarios?: string | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public itinerarios?: IItinerario[] | null,
    public unidadViajes?: IUnidadViaje[] | null,
    public origen?: IDireccion | null
  ) {
    this.status = this.status ?? false;
  }
}
