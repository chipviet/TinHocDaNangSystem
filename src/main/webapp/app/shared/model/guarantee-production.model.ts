import { IProduction } from 'app/shared/model/production.model';
import { IGuarantee } from 'app/shared/model/guarantee.model';

export interface IGuaranteeProduction {
  id?: number;
  production?: IProduction;
  guarantee?: IGuarantee;
}

export const defaultValue: Readonly<IGuaranteeProduction> = {};
