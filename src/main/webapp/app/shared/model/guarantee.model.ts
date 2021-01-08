import { Moment } from 'moment';

export interface IGuarantee {
  id?: number;
  description?: string;
  creationDate?: string;
}

export const defaultValue: Readonly<IGuarantee> = {};
