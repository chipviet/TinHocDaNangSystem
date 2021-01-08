import { Moment } from 'moment';
import { ICart } from 'app/shared/model/cart.model';
import { IProduction } from 'app/shared/model/production.model';

export interface ICartProduction {
  id?: number;
  creationDate?: string;
  quanlity?: number;
  cart?: ICart;
  prodution?: IProduction;
}

export const defaultValue: Readonly<ICartProduction> = {};
