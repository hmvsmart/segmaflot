import { ICP } from '@/shared/model/cp.model';
import { IEstado } from '@/shared/model/estado.model';

export interface ICiudad {
  id?: number;
  nombre?: string | null;
  cPS?: ICP[] | null;
  estado?: IEstado | null;
}

export class Ciudad implements ICiudad {
  constructor(public id?: number, public nombre?: string | null, public cPS?: ICP[] | null, public estado?: IEstado | null) {}
}
