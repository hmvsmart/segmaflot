import { ISucursal } from '@/shared/model/sucursal.model';
import { IReporte } from '@/shared/model/reporte.model';
import { IPedido } from '@/shared/model/pedido.model';
import { IPersona } from '@/shared/model/persona.model';

export interface IPersonaMoral {
  id?: number;
  razonSocial?: string | null;
  rfc?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  sucursals?: ISucursal[] | null;
  reportes?: IReporte[] | null;
  pedidos?: IPedido[] | null;
  persona?: IPersona | null;
}

export class PersonaMoral implements IPersonaMoral {
  constructor(
    public id?: number,
    public razonSocial?: string | null,
    public rfc?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public sucursals?: ISucursal[] | null,
    public reportes?: IReporte[] | null,
    public pedidos?: IPedido[] | null,
    public persona?: IPersona | null
  ) {}
}
