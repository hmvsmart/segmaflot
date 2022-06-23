import { IAditamentoUnidad } from '@/shared/model/aditamento-unidad.model';
import { IPoliza } from '@/shared/model/poliza.model';
import { IUnidadViaje } from '@/shared/model/unidad-viaje.model';
import { IVehiculo } from '@/shared/model/vehiculo.model';

export interface IUnidad {
  id?: number;
  fecha?: Date | null;
  numeroSerie?: string | null;
  kmActual?: number | null;
  tipoAdquisicion?: string | null;
  imagenContentType?: string | null;
  imagen?: string | null;
  color?: string | null;
  descripcionContentType?: string | null;
  descripcion?: string | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  aditamentoUnidads?: IAditamentoUnidad[] | null;
  polizas?: IPoliza[] | null;
  unidadViajes?: IUnidadViaje[] | null;
  vehiculo?: IVehiculo | null;
}

export class Unidad implements IUnidad {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public numeroSerie?: string | null,
    public kmActual?: number | null,
    public tipoAdquisicion?: string | null,
    public imagenContentType?: string | null,
    public imagen?: string | null,
    public color?: string | null,
    public descripcionContentType?: string | null,
    public descripcion?: string | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public aditamentoUnidads?: IAditamentoUnidad[] | null,
    public polizas?: IPoliza[] | null,
    public unidadViajes?: IUnidadViaje[] | null,
    public vehiculo?: IVehiculo | null
  ) {
    this.status = this.status ?? false;
  }
}
