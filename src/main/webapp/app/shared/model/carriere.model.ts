import { IFiliere } from 'app/shared/model/filiere.model';
import { ICours } from 'app/shared/model/cours.model';

export interface ICarriere {
  id?: number;
  nomCarriere?: string | null;
  description?: string | null;
  filieres?: IFiliere[] | null;
  cours?: ICours[] | null;
}

export const defaultValue: Readonly<ICarriere> = {};
