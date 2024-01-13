import { IFiliere } from 'app/shared/model/filiere.model';

export interface ICarriere {
  id?: number;
  nomCarriere?: string | null;
  description?: string | null;
  nomFilieres?: IFiliere[] | null;
}

export const defaultValue: Readonly<ICarriere> = {};
