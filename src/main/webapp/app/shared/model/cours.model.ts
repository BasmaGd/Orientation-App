import { IFiliere } from 'app/shared/model/filiere.model';

export interface ICours {
  id?: number;
  nomCours?: string | null;
  description?: string | null;
  nomFilieres?: IFiliere[] | null;
}

export const defaultValue: Readonly<ICours> = {};
