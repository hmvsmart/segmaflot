import { IPersona } from '@/shared/model/persona.model';

export interface IPersonaFisica {
  id?: number;
  nombre?: string | null;
  apaterno?: string | null;
  amaterno?: string | null;
  fechaNacimiento?: Date | null;
  curp?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  persona?: IPersona | null;
}

export class PersonaFisica implements IPersonaFisica {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public apaterno?: string | null,
    public amaterno?: string | null,
    public fechaNacimiento?: Date | null,
    public curp?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public persona?: IPersona | null
  ) {}
}
