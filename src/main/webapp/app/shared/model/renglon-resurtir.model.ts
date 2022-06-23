import { IResurtir } from '@/shared/model/resurtir.model';

export interface IRenglonResurtir {
  id?: number;
  fechaCaducidad?: Date | null;
  cantidad?: number | null;
  precioCompra?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  resurtir?: IResurtir | null;
}

export class RenglonResurtir implements IRenglonResurtir {
  constructor(
    public id?: number,
    public fechaCaducidad?: Date | null,
    public cantidad?: number | null,
    public precioCompra?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public resurtir?: IResurtir | null
  ) {}
}
