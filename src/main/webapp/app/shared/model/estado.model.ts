import { ICiudad } from '@/shared/model/ciudad.model';

export interface IEstado {
  id?: number;
  nombre?: string | null;
  ciudads?: ICiudad[] | null;
}

export class Estado implements IEstado {
  constructor(public id?: number, public nombre?: string | null, public ciudads?: ICiudad[] | null) {}
}
