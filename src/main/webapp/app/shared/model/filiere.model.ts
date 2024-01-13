import { ICours } from 'app/shared/model/cours.model';
import { ICarriere } from 'app/shared/model/carriere.model';

export interface IFiliere {
  id?: number;
  nomFiliere?: string | null;
  description?: string | null;
  nomCours?: ICours[] | null;
  nomCarrieres?: ICarriere[] | null;
}

export const defaultValue: Readonly<IFiliere> = {};
