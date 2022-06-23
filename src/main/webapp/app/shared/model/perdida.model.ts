import { IInventario } from '@/shared/model/inventario.model';

export interface IPerdida {
  id?: number;
  fecha?: Date | null;
  cantidad?: number | null;
  observacionesContentType?: string | null;
  observaciones?: string | null;
  inventario?: IInventario | null;
}

export class Perdida implements IPerdida {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public cantidad?: number | null,
    public observacionesContentType?: string | null,
    public observaciones?: string | null,
    public inventario?: IInventario | null
  ) {}
}
