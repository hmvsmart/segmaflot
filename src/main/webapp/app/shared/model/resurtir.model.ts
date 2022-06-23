import { IRenglonResurtir } from '@/shared/model/renglon-resurtir.model';
import { IInventario } from '@/shared/model/inventario.model';

export interface IResurtir {
  id?: number;
  fecha?: Date | null;
  total?: number | null;
  createByUser?: string | null;
  createdOn?: Date | null;
  modifyByUser?: string | null;
  modifiedOn?: Date | null;
  auditStatus?: string | null;
  renglonResurtirs?: IRenglonResurtir[] | null;
  inventario?: IInventario | null;
}

export class Resurtir implements IResurtir {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public total?: number | null,
    public createByUser?: string | null,
    public createdOn?: Date | null,
    public modifyByUser?: string | null,
    public modifiedOn?: Date | null,
    public auditStatus?: string | null,
    public renglonResurtirs?: IRenglonResurtir[] | null,
    public inventario?: IInventario | null
  ) {}
}
