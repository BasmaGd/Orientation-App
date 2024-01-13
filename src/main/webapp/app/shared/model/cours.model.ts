import { IFiliere } from 'app/shared/model/filiere.model';
import { ICarriere } from 'app/shared/model/carriere.model';

export interface ICours {
  id?: number;
  nomCours?: string | null;
  description?: string | null;
  filieres?: IFiliere[] | null;
  nomCarrieres?: ICarriere[] | null;
}

export const defaultValue: Readonly<ICours> = {};
