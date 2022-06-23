import { IColonia } from '@/shared/model/colonia.model';
import { ICiudad } from '@/shared/model/ciudad.model';

export interface ICP {
  id?: number;
  cp?: number | null;
  colonias?: IColonia[] | null;
  ciudad?: ICiudad | null;
}

export class CP implements ICP {
  constructor(public id?: number, public cp?: number | null, public colonias?: IColonia[] | null, public ciudad?: ICiudad | null) {}
}
