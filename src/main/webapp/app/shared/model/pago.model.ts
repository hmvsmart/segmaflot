export interface IPago {
  id?: number;
  fecha?: Date | null;
  tipoPago?: string | null;
  monto?: number | null;
  area?: string | null;
  identificador?: string | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
}

export class Pago implements IPago {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public tipoPago?: string | null,
    public monto?: number | null,
    public area?: string | null,
    public identificador?: string | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null
  ) {}
}
