import { IUnidad } from '@/shared/model/unidad.model';

export interface IVehiculo {
  id?: number;
  marca?: string | null;
  submarca?: string | null;
  modelo?: string | null;
  generacion?: string | null;
  tipoVehiculo?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  unidads?: IUnidad[] | null;
}

export class Vehiculo implements IVehiculo {
  constructor(
    public id?: number,
    public marca?: string | null,
    public submarca?: string | null,
    public modelo?: string | null,
    public generacion?: string | null,
    public tipoVehiculo?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public unidads?: IUnidad[] | null
  ) {}
}
