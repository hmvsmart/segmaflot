import { IListaServicio } from '@/shared/model/lista-servicio.model';
import { ICliente } from '@/shared/model/cliente.model';

export interface IUnidadServicio {
  id?: number;
  fecha?: Date | null;
  totalEstimado?: number | null;
  totalReal?: number | null;
  observacionesGeneralesContentType?: string | null;
  observacionesGenerales?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  listaServicios?: IListaServicio[] | null;
  cliente?: ICliente | null;
}

export class UnidadServicio implements IUnidadServicio {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public totalEstimado?: number | null,
    public totalReal?: number | null,
    public observacionesGeneralesContentType?: string | null,
    public observacionesGenerales?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public listaServicios?: IListaServicio[] | null,
    public cliente?: ICliente | null
  ) {}
}
