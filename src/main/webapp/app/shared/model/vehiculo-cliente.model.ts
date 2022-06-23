import { ICliente } from '@/shared/model/cliente.model';

export interface IVehiculoCliente {
  id?: number;
  fecha?: Date | null;
  numeroSerie?: string | null;
  color?: string | null;
  status?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  cliente?: ICliente | null;
}

export class VehiculoCliente implements IVehiculoCliente {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public numeroSerie?: string | null,
    public color?: string | null,
    public status?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public cliente?: ICliente | null
  ) {
    this.status = this.status ?? false;
  }
}
