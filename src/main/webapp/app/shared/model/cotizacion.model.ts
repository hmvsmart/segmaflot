export interface ICotizacion {
  id?: number;
  fecha?: Date | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
}

export class Cotizacion implements ICotizacion {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null
  ) {}
}
