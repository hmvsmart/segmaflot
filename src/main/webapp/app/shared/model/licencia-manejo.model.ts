import { IEmpleado } from '@/shared/model/empleado.model';

export interface ILicenciaManejo {
  id?: number;
  fecha?: Date | null;
  tipo?: string | null;
  status?: boolean | null;
  fechaExpiracion?: Date | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  empleado?: IEmpleado | null;
}

export class LicenciaManejo implements ILicenciaManejo {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public tipo?: string | null,
    public status?: boolean | null,
    public fechaExpiracion?: Date | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public empleado?: IEmpleado | null
  ) {
    this.status = this.status ?? false;
  }
}
