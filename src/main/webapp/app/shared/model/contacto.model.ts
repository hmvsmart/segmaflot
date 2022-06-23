import { ITipoContacto } from '@/shared/model/tipo-contacto.model';
import { IPersona } from '@/shared/model/persona.model';

export interface IContacto {
  id?: number;
  valor?: string | null;
  etiqueta?: string | null;
  string?: boolean | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  tipoContacto?: ITipoContacto | null;
  persona?: IPersona | null;
}

export class Contacto implements IContacto {
  constructor(
    public id?: number,
    public valor?: string | null,
    public etiqueta?: string | null,
    public string?: boolean | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public tipoContacto?: ITipoContacto | null,
    public persona?: IPersona | null
  ) {
    this.string = this.string ?? false;
  }
}
