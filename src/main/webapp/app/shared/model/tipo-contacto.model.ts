import { IContacto } from '@/shared/model/contacto.model';

export interface ITipoContacto {
  id?: number;
  nombre?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  contactos?: IContacto[] | null;
}

export class TipoContacto implements ITipoContacto {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public contactos?: IContacto[] | null
  ) {}
}
