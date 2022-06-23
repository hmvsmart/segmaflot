import { IVehiculoCliente } from '@/shared/model/vehiculo-cliente.model';
import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';
import { IPersona } from '@/shared/model/persona.model';

export interface ICliente {
  id?: number;
  fecha?: Date | null;
  observacionesContentType?: string | null;
  observaciones?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  vehiculoClientes?: IVehiculoCliente[] | null;
  unidadServicios?: IUnidadServicio[] | null;
  persona?: IPersona | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public observacionesContentType?: string | null,
    public observaciones?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public vehiculoClientes?: IVehiculoCliente[] | null,
    public unidadServicios?: IUnidadServicio[] | null,
    public persona?: IPersona | null
  ) {}
}
