import { IMaterialServicio } from '@/shared/model/material-servicio.model';
import { IPrecioServicio } from '@/shared/model/precio-servicio.model';
import { IListaServicio } from '@/shared/model/lista-servicio.model';

export interface IServicio {
  id?: number;
  nombre?: string | null;
  descripcionContentType?: string | null;
  descripcion?: string | null;
  duracionEstimada?: Date | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  materialServicios?: IMaterialServicio[] | null;
  precioServicios?: IPrecioServicio[] | null;
  listaServicios?: IListaServicio[] | null;
}

export class Servicio implements IServicio {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public descripcionContentType?: string | null,
    public descripcion?: string | null,
    public duracionEstimada?: Date | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public materialServicios?: IMaterialServicio[] | null,
    public precioServicios?: IPrecioServicio[] | null,
    public listaServicios?: IListaServicio[] | null
  ) {}
}
