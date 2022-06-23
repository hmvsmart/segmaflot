import { IEmpleadoTipoVehiculo } from '@/shared/model/empleado-tipo-vehiculo.model';
import { IExperienciaHabilidad } from '@/shared/model/experiencia-habilidad.model';
import { ILicenciaManejo } from '@/shared/model/licencia-manejo.model';
import { IHorario } from '@/shared/model/horario.model';
import { IOperadorUnidad } from '@/shared/model/operador-unidad.model';
import { IPersona } from '@/shared/model/persona.model';
import { ISucursal } from '@/shared/model/sucursal.model';

export interface IEmpleado {
  id?: number;
  rfc?: string | null;
  nss?: string | null;
  finicio?: Date | null;
  puesto?: string | null;
  salario?: number | null;
  diaPago?: string | null;
  tipoPago?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  empleadoTipoVehiculos?: IEmpleadoTipoVehiculo[] | null;
  experienciaHabilidads?: IExperienciaHabilidad[] | null;
  licenciaManejos?: ILicenciaManejo[] | null;
  horarios?: IHorario[] | null;
  operadorUnidads?: IOperadorUnidad[] | null;
  persona?: IPersona | null;
  sucursal?: ISucursal | null;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public rfc?: string | null,
    public nss?: string | null,
    public finicio?: Date | null,
    public puesto?: string | null,
    public salario?: number | null,
    public diaPago?: string | null,
    public tipoPago?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public empleadoTipoVehiculos?: IEmpleadoTipoVehiculo[] | null,
    public experienciaHabilidads?: IExperienciaHabilidad[] | null,
    public licenciaManejos?: ILicenciaManejo[] | null,
    public horarios?: IHorario[] | null,
    public operadorUnidads?: IOperadorUnidad[] | null,
    public persona?: IPersona | null,
    public sucursal?: ISucursal | null
  ) {}
}
