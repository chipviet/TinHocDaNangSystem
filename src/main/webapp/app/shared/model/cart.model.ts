import { Moment } from 'moment';

export interface ICart {
  id?: number;
  idUser?: number;
  totalSpent?: number;
  creationDate?: string;
}

export const defaultValue: Readonly<ICart> = {};
