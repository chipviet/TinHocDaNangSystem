import { Moment } from 'moment';
import { IBrand } from 'app/shared/model/brand.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IProduction {
  id?: number;
  name?: string;
  price?: number;
  description?: string;
  imageURL?: string;
  salePrice?: number;
  quantity?: number;
  condition?: number;
  origin?: string;
  configuration?: string;
  creationDate?: string;
  brand?: IBrand;
  category?: ICategory;
}

export const defaultValue: Readonly<IProduction> = {};
