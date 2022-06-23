import { IDireccion } from '@/shared/model/direccion.model';
import { ICP } from '@/shared/model/cp.model';

export interface IColonia {
  id?: number;
  nombre?: string | null;
  direccions?: IDireccion[] | null;
  cp?: ICP | null;
}

export class Colonia implements IColonia {
  constructor(public id?: number, public nombre?: string | null, public direccions?: IDireccion[] | null, public cp?: ICP | null) {}
}
